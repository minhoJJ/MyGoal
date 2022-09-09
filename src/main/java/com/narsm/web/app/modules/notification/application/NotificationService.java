package com.narsm.web.app.modules.notification.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.app.modules.notification.domain.entity.Notification;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    public void markAsRead(List<Notification> notifications) {
        notifications.forEach(Notification::read);
    }
}
