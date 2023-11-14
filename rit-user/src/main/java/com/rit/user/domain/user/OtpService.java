package com.rit.user.domain.user;

public interface OtpService {

    UserOtp generateOtp(OtpActionType actionType);

    boolean isUserOtpMatches(String otp, OtpActionType actionType, User user);
}
