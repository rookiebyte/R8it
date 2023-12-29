package com.rit.notification.domain.notification.email;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
class OtpEmailTemplate extends EmailTemplate {

    @Valid
    @NotNull
    private final OtpEmailTemplateBindings bindings;

    OtpEmailTemplate(UUID notificationId, String recipient, String templateName,
                     OtpEmailTemplateBindings bindings) {
        super(notificationId, recipient, templateName);
        this.bindings = bindings;
    }

    @Override
    public String getSubject() {
        return "OTP - " + bindings.otpCode;
    }

    @Override
    public Object getBindings() {
        return bindings;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class OtpEmailTemplateBindings {

        @NotEmpty
        private String otpCode;
    }
}
