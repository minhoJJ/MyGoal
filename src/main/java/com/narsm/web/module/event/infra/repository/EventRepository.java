package com.narsm.web.module.event.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.module.event.domain.entity.Event;

@Transactional(readOnly = true)
public interface EventRepository extends JpaRepository<Event, Long> {
}
