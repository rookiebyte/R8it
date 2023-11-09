package com.rit.user.domain.user;

public interface OtpService {

    UserOtp generateOtp(String actionName);

    boolean isUserOtpMatches(String otp, String actionName, User user);
}
