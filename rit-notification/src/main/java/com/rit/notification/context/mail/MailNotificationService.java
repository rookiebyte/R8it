package com.rit.notification.context.mail;

import com.rit.notification.domain.notification.mail.MailSenderService;
import com.rit.notification.domain.notification.mail.MailTemplate;
import com.rit.starterboot.domain.notification.mail.MailNotificationTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MailNotificationService {

    private final MailSenderService mailSenderService;

    public void sendEmailNotification(MailNotificationTemplate template) {
        var mailTemplate = MailTemplate.builder()
                                       .recipient(template.getRecipient())
                                       .subject(template.getSubject())
                                       .templateName(template.getTemplateName())
                                       .bindings(template.getBindings())
                                       .build();
        mailSenderService.send(mailTemplate);
    }
}
