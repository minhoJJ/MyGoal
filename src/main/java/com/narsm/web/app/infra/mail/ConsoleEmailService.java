package com.narsm.web.app.infra.mail;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Profile("local")
@Service
@Slf4j
public class ConsoleEmailService implements EmailService {
    @Override
    public void sendEmail(EmailMessage emailMessage) {
        log.info("sent email: {}", emailMessage.getMessage());
    }
}
