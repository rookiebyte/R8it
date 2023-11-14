package com.rit.user.infrastructure.user;

import com.rit.user.domain.user.OtpActionType;
import com.rit.user.domain.user.OtpService;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserOtp;

import java.time.LocalDateTime;

class OtpFacadeService implements OtpService {

    @Override
    public UserOtp generateOtp(OtpActionType actionType) {
        return UserOtp.builder()
                      .actionType(actionType)
                      .value("123456".getBytes())
                      .expiresAt(LocalDateTime.now().plusHours(1))
                      .build();
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
