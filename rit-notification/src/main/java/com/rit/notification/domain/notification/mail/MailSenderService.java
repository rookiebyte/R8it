package com.rit.notification.domain.notification.mail;

public interface MailSenderService {

    void send(MailTemplate mailTemplate);
}
