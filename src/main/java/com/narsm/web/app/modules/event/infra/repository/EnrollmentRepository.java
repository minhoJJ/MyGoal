package com.narsm.web.app.modules.event.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.narsm.web.app.modules.account.domain.entity.Account;
import com.narsm.web.app.modules.event.domain.entity.Enrollment;
import com.narsm.web.app.modules.event.domain.entity.Event;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByEventAndAccount(Event event, Account account);

    Enrollment findByEventAndAccount(Event event, Account account);
}