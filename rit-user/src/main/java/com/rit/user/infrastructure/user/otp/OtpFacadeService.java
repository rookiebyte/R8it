package com.rit.user.infrastructure.user.otp;

import com.rit.user.configuration.otp.properties.OtpProperties;
import com.rit.user.domain.user.OtpActionType;
import com.rit.user.domain.user.OtpService;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserOtp;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
class OtpFacadeService implements OtpService {

    private final OtpProperties otpProperties;

    @Override
    public UserOtp generateOtp(OtpActionType actionType) {
        return UserOtp.builder()
                      .actionType(actionType)
                      .value(generateValue(otpProperties.defaultLength()).getBytes())
                      .expiresAt(LocalDateTime.now().plusHours(otpProperties.defaultExpDelay()))
                      .build();
    }

    private String generateValue(int length) {
        return TwoFactor.calculateVerificationCode(otpProperties.secret(), length);
    }

    @Override
    public boolean isUserOtpMatches(String otp, OtpActionType actionType, User user) {
        if (!user.getOneTimePasswords().containsKey(actionType)) {
            return false;
        }
        var result = user.getOneTimePasswords().get(actionType).matches(otp.getBytes());
        user.getOneTimePasswords().remove(actionType);
        return result;
    }
}
