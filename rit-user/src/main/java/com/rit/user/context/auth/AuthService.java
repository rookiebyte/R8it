package com.rit.user.context.auth;

import com.rit.starterboot.domain.notification.NotificationService;
import com.rit.starterboot.domain.notification.RegistrationOtpMailNotification;
import com.rit.starterboot.domain.user.UserStatus;
import com.rit.user.configuration.jwt.JwtFacade;
import com.rit.user.context.auth.dto.LoginRequest;
import com.rit.user.context.auth.dto.LoginResponse;
import com.rit.user.context.auth.dto.RegisterOtpRequest;
import com.rit.user.context.auth.dto.RegisterRequest;
import com.rit.user.context.auth.exception.IncorrectOtpException;
import com.rit.user.context.auth.exception.InvalidCredentialsException;
import com.rit.user.context.auth.exception.UserAlreadyExistsException;
import com.rit.user.domain.user.OtpActionType;
import com.rit.user.domain.user.OtpService;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.domain.user.UsersCredentials;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

@AllArgsConstructor
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final JwtFacade jwtFacade;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findUserByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);
        var credentials = userRepository.fetchUsersCredentialsByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);
        if (passwordEncoder.matches(request.password(), new String(credentials.password()))) {
            return new LoginResponse(jwtFacade.createJwt(user));
        }
        throw new InvalidCredentialsException();
    }

    public void userRegisterInitWithOtp(RegisterOtpRequest request) {
        if (userRepository.findUserByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException(request.email());
        }
        var user = User.builder()
                       .email(request.email())
                       .userStatus(UserStatus.PENDING)
                       .oneTimePasswords(new HashMap<>())
                       .build();
        var otp = otpService.generateOtp(OtpActionType.REGISTRATION);
        var template = new RegistrationOtpMailNotification(user.getEmail(), new String(otp.getValue()));
        notificationService.sendNotification(template);
        user.addOtp(otp);
        userRepository.saveUser(user);
    }

    public LoginResponse register(RegisterRequest request) {
        var user = userRepository.findUserByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);
        if (user.getUserStatus() != UserStatus.PENDING) {
            LOGGER.warn("Login attempt for user[{}] with not pending status", user.getId());
            throw new InvalidCredentialsException();
        }
        if (!otpService.isUserOtpMatches(request.otp(), OtpActionType.REGISTRATION, user)) {
            throw new IncorrectOtpException();
        }

        var credentials = new UsersCredentials(request.email(), passwordEncoder.encode(request.password()).getBytes());
        userRepository.updateUsersCredentials(user, credentials);
        return new LoginResponse(jwtFacade.createJwt(user));
    }
}
