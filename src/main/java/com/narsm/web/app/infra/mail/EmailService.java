package com.narsm.web.app.infra.mail;

public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
