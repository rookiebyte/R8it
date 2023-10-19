package com.rit.user.context.auth;

import com.rit.starterboot.domain.user.User;
import com.rit.user.configuration.jwt.JwtFacade;
import com.rit.user.context.auth.dto.LoginRequest;
import com.rit.user.context.auth.dto.LoginResponse;
import com.rit.user.context.auth.dto.RegisterRequest;
import com.rit.user.context.auth.exception.InvalidCredentialsException;
import com.rit.user.context.auth.exception.UserAlreadyExistsException;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.domain.user.UsersCredentials;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtFacade jwtFacade;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtFacade jwtFacade, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtFacade = jwtFacade;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findUserByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);
        var credentials = userRepository.fetchUsersCredentialsByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);
        if (credentials.authenticate(passwordEncoder, request)) {
            return new LoginResponse(jwtFacade.createJwt(user));
        }
        throw new InvalidCredentialsException();
    }

    public LoginResponse register(RegisterRequest request) {
        if (userRepository.findUserByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException(request.email());
        }

        var user = User.builder().email(request.email()).build();
        var credentials = new UsersCredentials(request.email(), passwordEncoder.encode(request.password()).getBytes());
        user = userRepository.saveUser(user, credentials);
        return new LoginResponse(jwtFacade.createJwt(user));
    }
}
