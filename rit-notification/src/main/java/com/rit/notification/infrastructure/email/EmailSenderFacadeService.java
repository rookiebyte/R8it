package com.rit.notification.infrastructure.email;

import com.rit.notification.domain.notification.email.EmailSenderService;
import com.rit.notification.domain.notification.email.EmailTemplate;
import com.rit.notification.exception.SendingNotificationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@AllArgsConstructor
public class EmailSenderFacadeService implements EmailSenderService {

    private static final String BINDINGS_KEY = "bindings";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderFacadeService.class);

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void send(EmailTemplate emailTemplate) {
        try {
            sendHtmlMimeMessage(emailTemplate);
        } catch (MessagingException ex) {
            throw new SendingNotificationException(ex);
        }
    }

    private void sendHtmlMimeMessage(EmailTemplate emailTemplate) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setTo(emailTemplate.getRecipient());
        helper.setSubject(emailTemplate.getSubject());
        helper.setText(getHtmlContent(emailTemplate), true);
        LOGGER.info("Send email notification with id {}", emailTemplate.getNotificationId());
        emailSender.send(message);
    }

    private String getHtmlContent(EmailTemplate emailTemplate) {
        Context context = new Context();
        if (emailTemplate.getBindings() != null) {
            context.setVariables(Map.of(BINDINGS_KEY, emailTemplate.getBindings()));
        }
        return templateEngine.process(emailTemplate.getTemplateName(), context);
    }
}
