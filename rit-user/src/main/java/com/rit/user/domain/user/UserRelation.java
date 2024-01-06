package com.rit.user.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRelation {

    private final UserRelationType type;
    private final String userId;
    private final String targetId;

    public UserRelation(UserRelationType type, String userId, String targetId) {
        this.type = type;
        this.userId = userId;
        this.targetId = targetId;
    }
}
