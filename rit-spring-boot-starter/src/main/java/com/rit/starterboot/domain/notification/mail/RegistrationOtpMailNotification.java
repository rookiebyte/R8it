package com.rit.starterboot.domain.notification.mail;

import java.util.Map;

public final class RegistrationOtpMailNotification implements MailNotificationTemplate {

    private static final String TEMPLATE_NAME = "registration/otp";
    private final String recipient;
    private final String otp;

    public RegistrationOtpMailNotification(String recipient, String otp) {
        this.recipient = recipient;
        this.otp = otp;
    }

    @Override
    public String getTemplateName() {
        return TEMPLATE_NAME;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public String getSubject() {
        return "RIT OTP CODE - " + otp;
    }

    @Override
    public Map<String, Object> getBindings() {
        return Map.of("otpCode", otp);
    }
}
