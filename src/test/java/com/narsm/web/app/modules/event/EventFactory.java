package com.narsm.web.app.modules.event;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.narsm.web.app.modules.account.domain.entity.Account;
import com.narsm.web.app.modules.event.application.EventService;
import com.narsm.web.app.modules.event.domain.entity.Event;
import com.narsm.web.app.modules.event.domain.entity.EventType;
import com.narsm.web.app.modules.event.endpoint.form.EventForm;
import com.narsm.web.app.modules.event.infra.repository.EventRepository;
import com.narsm.web.app.modules.study.domain.entity.Study;
import com.narsm.web.app.modules.study.infra.repository.StudyRepository;

@Component
public class EventFactory {
    @Autowired EventRepository eventRepository;
    @Autowired StudyRepository studyRepository;
    @Autowired EventService eventService;

    public Event createEvent(EventType eventType, Account account, String studyPath) {
        Study study = studyRepository.findByPath(studyPath);
        LocalDateTime now = LocalDateTime.now();
        EventForm eventForm = EventForm.builder()
                .description("description")
                .eventType(eventType)
                .endDateTime(now.plusWeeks(3))
                .endEnrollmentDateTime(now.plusWeeks(1))
                .limitOfEnrollments(2)
                .startDateTime(now.plusWeeks(2))
                .title("title")
                .build();
        return eventService.createEvent(study, eventForm, account);
    }
}
