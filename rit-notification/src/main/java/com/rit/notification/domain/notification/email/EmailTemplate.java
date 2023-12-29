package com.rit.notification.domain.notification.email;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Validated
public abstract class EmailTemplate {

    @NotNull
    private final UUID notificationId;

    @NotEmpty
    private final String recipient;

    @NotEmpty
    private final String templateName;

    public abstract String getSubject();

    public abstract Object getBindings();
}
