package com.narsm.web.module.event.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.module.account.domain.entity.Account;
import com.narsm.web.module.event.domain.entity.Event;
import com.narsm.web.module.event.form.EventForm;
import com.narsm.web.module.event.infra.repository.EventRepository;
import com.narsm.web.module.study.domain.entity.Study;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event createEvent(Study study, EventForm eventForm, Account account) {
        Event event = Event.from(eventForm, account, study);
        return eventRepository.save(event);
    }

    public void updateEvent(Event event, EventForm eventForm) {
        event.updateFrom(eventForm);
    }
}
