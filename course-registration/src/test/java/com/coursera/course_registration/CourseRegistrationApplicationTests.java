package com.coursera.course_registration;

import com.coursera.course_registration.exception.CourseNotFoundException;
import com.coursera.course_registration.model.*;
import com.coursera.course_registration.repository.*;
import com.coursera.course_registration.service.EnrollmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class CourseRegistrationApplicationTests {

	@Autowired
	private EnrollmentService enrollmentService;


	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WaitListRepository waitListRepository;

	@Test
	void testConcurrentEnrollment() throws InterruptedException {

		Course course = courseRepository
				.findById(UUID.fromString("870f3b7f-2933-4050-93d0-350de4bef98b"))
				.orElseThrow(()-> new CourseNotFoundException("Course Not Found!"));
		Section s = Section.builder()
				.course(course)
				.teacher(course.getTeacher())
				.capacity(1)
				.enrolledCount(0)
				.schedule("Test Schedule")
				.room("Test Room")
				.build();
		sectionRepository.save(s);

		List<UUID> studentIds = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			User student = User.builder()
					.name("TestUser" + i)
					.email("testuser" + i + "@test.com")
					.role(Role.STUDENT)
					.build();
			userRepository.save(student);
			studentIds.add(student.getId());
		}

		CountDownLatch latch = new CountDownLatch(1);
		ExecutorService executor = Executors.newFixedThreadPool(50);
		List<Callable<Void>> tasks = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			final int index = i ;
			tasks.add(()-> {
				latch.await();
				try {
					enrollmentService.enrollStudent(studentIds.get(index), s.getId());
				} catch (Exception e) {
					// ignore — expected
				}
				return null;
			});
		}
		latch.countDown();
		executor.invokeAll(tasks);
		executor.shutdown();

		// Sirf 1 enrolled hona chahiye
		List<Enrollment> enrollments = enrollmentRepository
				.findActiveBySectionId(s.getId());
		Assertions.assertEquals(1,enrollments.size(),"Only 1 student should be enrolled!");

		// 49 waitlisted hone chahiye
		List<WaitList> waitlist = waitListRepository
				.findBySection_Id(s.getId());
		Assertions.assertEquals(49, waitlist.size(),
				"49 students should be on waitlist!");
	}
}
