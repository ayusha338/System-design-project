package com.coursera.course_registration.Controller;

import com.coursera.course_registration.model.WaitList;
import com.coursera.course_registration.repository.WaitListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/ap1/v1/waitlist")
@RestController
@RequiredArgsConstructor
public class WaitListController {

    private final WaitListRepository waitListRepository;

    // Section ki poori waitlist
    @GetMapping("/sections/{sectionId}")
    public ResponseEntity<List<WaitList>> getWaitlistBySection(
            @PathVariable UUID sectionId) {
        return ResponseEntity.ok(
                waitListRepository.findBySection_Id(sectionId));
    }

    // Student ka waitlist entry
    @GetMapping("/students/{studentId}/sections/{sectionId}")
    public ResponseEntity<WaitList> getWaitlistEntry(
            @PathVariable UUID studentId,
            @PathVariable UUID sectionId) {
        return ResponseEntity.ok(
                waitListRepository
                        .findByStudent_IdAndSection_Id(studentId, sectionId)
                        .orElseThrow(() -> new RuntimeException("Not on waitlist!")));
    }

    // Waitlist leave karo
    @DeleteMapping("/{waitlistId}")
    public ResponseEntity<Void> leaveWaitlist(
            @PathVariable UUID waitlistId) {
        waitListRepository.deleteById(waitlistId);
        return ResponseEntity.noContent().build();
    }
}
