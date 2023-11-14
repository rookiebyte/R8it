package com.rit.notification.context.mail;

import com.rit.notification.context.mail.dto.EmailNotificationRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailNotificationEndpoint {

    private final MailNotificationService mailNotificationService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    void sendEmailNotification(@Valid @RequestBody EmailNotificationRequest request) {
        mailNotificationService.sendEmailNotification(request);
    }
}
