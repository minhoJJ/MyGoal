package com.narsm.web.app.modules.notification.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.narsm.web.app.modules.account.domain.entity.Account;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String link;

    private String message;

    private boolean checked;

    @ManyToOne
    private Account account;

    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public static Notification from(String title, String link, boolean checked, LocalDateTime created, String message, Account account, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.title = title;
        notification.link = link;
        notification.checked = checked;
        notification.created = created;
        notification.message = message;
        notification.account = account;
        notification.notificationType = notificationType;
        return notification;
    }

    public void read() {
        this.checked = true;
    }
}
