package com.rit.user.context.info;

import com.rit.starterboot.domain.user.UserContext;
import com.rit.user.context.info.dto.UserDto;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
/*todo: test*/
public class InfoService {

    private final UserRepository userRepository;

    public UserDto userInfo(UserContext userContext) {
        return userRepository.findUserById(userContext.id())
                             .map(UserDto::new)
                             .orElseThrow(() -> new UserNotFoundException(userContext.id()));
    }
}
