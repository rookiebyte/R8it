package com.rit.notification.context.mail;

import com.rit.notification.context.mail.dto.EmailNotificationRequest;
import com.rit.notification.domain.notification.mail.MailSenderService;
import com.rit.notification.domain.notification.mail.MailTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MailNotificationService {

    private final MailSenderService mailSenderService;

    public void sendEmailNotification(EmailNotificationRequest request) {
        var mailTemplate = MailTemplate.builder()
                                       .recipient(request.recipient())
                                       .subject(request.subject())
                                       .templateName(request.templateName())
                                       .bindings(request.bindings())
                                       .build();
        mailSenderService.send(mailTemplate);
    }
}
