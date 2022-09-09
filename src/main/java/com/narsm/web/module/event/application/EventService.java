package com.narsm.web.module.event.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.module.account.domain.entity.Account;
import com.narsm.web.module.event.domain.entity.Enrollment;
import com.narsm.web.module.event.domain.entity.Event;
import com.narsm.web.module.event.form.EventForm;
import com.narsm.web.module.event.infra.repository.EnrollmentRepository;
import com.narsm.web.module.event.infra.repository.EventRepository;
import com.narsm.web.module.study.domain.entity.Study;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EnrollmentRepository enrollmentRepository;

    public Event createEvent(Study study, EventForm eventForm, Account account) {
        Event event = Event.from(eventForm, account, study);
        return eventRepository.save(event);
    }

    public void updateEvent(Event event, EventForm eventForm) {
        event.updateFrom(eventForm);
        event.acceptWaitingList();
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    public void enroll(Event event, Account account) {
        if (!enrollmentRepository.existsByEventAndAccount(event, account)) {
            Enrollment enrollment = Enrollment.of(LocalDateTime.now(), event.isAbleToAcceptWaitingEnrollment(), account);
            event.addEnrollment(enrollment);
            enrollmentRepository.save(enrollment);
        }
    }

    public void leave(Event event, Account account) {
        Enrollment enrollment = enrollmentRepository.findByEventAndAccount(event, account);
        event.removeEnrollment(enrollment);
        enrollmentRepository.delete(enrollment);
        event.acceptNextIfAvailable();
    }
}
