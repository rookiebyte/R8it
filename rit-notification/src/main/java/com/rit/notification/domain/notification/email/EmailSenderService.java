package com.rit.notification.domain.notification.email;

public interface EmailSenderService {

    void send(EmailTemplate emailTemplate);
}
