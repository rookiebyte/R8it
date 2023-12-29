package com.rit.notification.context.email;

import com.rit.notification.domain.notification.email.EmailSenderService;
import com.rit.notification.domain.notification.email.EmailTemplate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Service
@Validated
public class MailNotificationService {

    private final EmailSenderService emailSenderService;

    public void sendEmailNotification(@Valid EmailTemplate emailTemplate) {
        emailSenderService.send(emailTemplate);
    }
}
