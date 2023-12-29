package com.rit.user.domain.notification;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailNotification {

    private final String recipient;
    private final String templateName;
    public final Object bindings;
}
