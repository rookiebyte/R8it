package com.rit.user.context.info;

import com.rit.starterboot.configuration.security.context.UserContextProvider;
import com.rit.user.context.info.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserInfoEndpoint {

    private final InfoService infoService;
    private final UserContextProvider userContextProvider;

    @GetMapping
    public UserDto userInfo() {
        return infoService.userInfo(userContextProvider.get());
    }
}
