package com.rit.notification.infrastructure.mail;

import com.rit.notification.domain.notification.mail.MailSenderService;
import com.rit.notification.domain.notification.mail.MailTemplate;
import com.rit.notification.exception.SendingNotificationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;


public class MailSenderFacadeService implements MailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void send(MailTemplate mailTemplate) {
        try {
            sendHtmlMimeMessage(mailTemplate);
        } catch (MessagingException ex) {
            throw new SendingNotificationException(ex);
        }
    }

    private void sendHtmlMimeMessage(MailTemplate mailTemplate) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setTo(mailTemplate.getRecipient());
        helper.setSubject(mailTemplate.getSubject());
        helper.setText(getHtmlContent(mailTemplate), true);
        emailSender.send(message);
    }

    private String getHtmlContent(MailTemplate mailTemplate) {
        Context context = new Context();
        context.setVariables(mailTemplate.getBindings());
        if (mailTemplate.getBindings() != null) {
            context.setVariables(mailTemplate.getBindings());
        }
        return templateEngine.process(mailTemplate.getTemplateName(), context);
    }
}
