package com.narsm.web.app.modules.event.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.app.modules.account.domain.entity.Account;
import com.narsm.web.app.modules.event.domain.entity.Enrollment;
import com.narsm.web.app.modules.event.domain.entity.Event;
import com.narsm.web.app.modules.event.endpoint.form.EventForm;
import com.narsm.web.app.modules.event.infra.repository.EnrollmentRepository;
import com.narsm.web.app.modules.event.infra.repository.EventRepository;
import com.narsm.web.app.modules.study.domain.entity.Study;

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
        if (!enrollment.isAttended()) {
            event.removeEnrollment(enrollment);
            enrollmentRepository.delete(enrollment);
            event.acceptNextIfAvailable();
        }
    }

    public void acceptEnrollment(Event event, Enrollment enrollment) {
        event.accept(enrollment);
    }

    public void rejectEnrollment(Event event, Enrollment enrollment) {
        event.reject(enrollment);
    }

    public void checkInEnrollment(Event event, Enrollment enrollment) {
        enrollment.attend();
    }

    public void cancelCheckinEnrollment(Event event, Enrollment enrollment) {
        enrollment.absent();
    }
}