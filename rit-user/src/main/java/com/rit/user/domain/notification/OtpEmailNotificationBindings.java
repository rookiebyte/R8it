package com.rit.user.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OtpEmailNotificationBindings {

    private final String otpCode;
}
