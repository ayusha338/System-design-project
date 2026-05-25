package com.coursera.course_registration.kafka.consumer;

import com.coursera.course_registration.kafka.events.SeatAvailableEvent;
import com.coursera.course_registration.model.WaitList;
import com.coursera.course_registration.model.WaitlistStatus;
import com.coursera.course_registration.repository.WaitListRepository;
import com.coursera.course_registration.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WaitlistPromotionConsumer {

    private final WaitListRepository waitListRepository;
    private final EnrollmentService enrollmentService;

    @KafkaListener(
            topics = "seat-available",
            groupId = "waitlist-group"
    )
    public void consume(SeatAvailableEvent event){
        Optional<WaitList> waitList =  waitListRepository.findTopWaiting(event.getSectionId());
        if (waitList.isEmpty()) {
            log.info("No students on waitlist for section {}",
                    event.getSectionId());
            return;
        }
        waitList.ifPresent(list ->{
            enrollmentService.enrollStudent
                (list.getStudent().getId()
                        , list.getSection().getId());
            list.setStatus(WaitlistStatus.PROMOTED);
            waitListRepository.save(list);
            log.info("Student {} promoted from waitlist for section {}",
                    list.getStudent().getId(),
                    list.getSection().getId());
        });
    }

}
