package com.narsm.web.app.modules.notification.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.narsm.web.app.modules.notification.domain.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
