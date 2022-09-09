package com.narsm.web.app.modules.study.event;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.narsm.web.app.infra.config.AppProperties;
import com.narsm.web.app.infra.mail.EmailMessage;
import com.narsm.web.app.infra.mail.EmailService;
import com.narsm.web.app.modules.account.domain.entity.Account;
import com.narsm.web.app.modules.account.infra.predicates.AccountPredicates;
import com.narsm.web.app.modules.account.infra.repository.AccountRepository;
import com.narsm.web.app.modules.notification.domain.entity.Notification;
import com.narsm.web.app.modules.notification.domain.entity.NotificationType;
import com.narsm.web.app.modules.notification.infra.repository.NotificationRepository;
import com.narsm.web.app.modules.study.domain.entity.Study;
import com.narsm.web.app.modules.study.infra.repository.StudyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Transactional
@Component
@RequiredArgsConstructor
public class StudyEventListener {

    private final StudyRepository studyRepository;
    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;

    @EventListener
    public void handleStudyCreatedEvent(StudyCreatedEvent studyCreatedEvent) {
        Study study = studyRepository.findStudyWithTagsAndZonesById(studyCreatedEvent.getStudy().getId());
        Iterable<Account> accounts = accountRepository.findAll(AccountPredicates.findByTagsAndZones(study.getTags(), study.getZones()));
        for (Account account : accounts) {
            Account.NotificationSetting notificationSetting = account.getNotificationSetting();
            if (notificationSetting.isStudyCreatedByEmail()) {
                sendEmail(study, account);
            }
            if (notificationSetting.isStudyCreatedByWeb()) {
                saveNotification(study, account);
            }
        }
    }

    private void sendEmail(Study study, Account account) {
        Context context = new Context();
        context.setVariable("link", "/study/" + study.getEncodedPath());
        context.setVariable("nickname", account.getNickname());
        context.setVariable("linkName", study.getTitle());
        context.setVariable("message", "새로운 스터디가 오픈하였습니다.");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context);
        emailService.sendEmail(EmailMessage.builder()
                .to(account.getEmail())
                .subject("[Webluxible] " + study.getTitle() + " 스터디가 오픈하였습니다.")
                .message(message)
                .build());
    }

    private void saveNotification(Study study, Account account) {
        notificationRepository.save(Notification.from(study.getTitle(), "/study/" + study.getEncodedPath(),
                false, LocalDateTime.now(), study.getShortDescription(), account, NotificationType.STUDY_CREATED));
    }
}