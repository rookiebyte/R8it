package com.rit.user.infrastructure.user;

import com.rit.user.domain.user.OtpService;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserOtp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class OtpFacadeService implements OtpService {

    @Override
    public UserOtp generateOtp(String actionName) {
        return UserOtp.builder()
                      .actionName(actionName)
                      .value("123456".getBytes())
                      .expiresAt(LocalDateTime.now().plusHours(1))
                      .build();
    }

    @Override
    public boolean isUserOtpMatches(String otp, String actionName, User user) {
        if (!user.getOneTimePasswords().containsKey(actionName)) {
            return false;
        }
        var result = user.getOneTimePasswords().get(actionName).matches(otp.getBytes());
        user.getOneTimePasswords().remove(actionName);
        return result;
    }
}
