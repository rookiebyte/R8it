package com.rit.user.context.friend.dto;

import com.rit.user.domain.friend.FriendRequest;
import com.rit.user.domain.friend.FriendRequestStatus;
import com.rit.user.domain.user.User;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FriendRequestDto(

        @NotNull
        FriendRequestStatus status,

        @NotNull
        UUID targetId
) {

    public FriendRequestDto(User user, FriendRequest friendRequest) {
        this(friendRequest.getStatus(), friendRequest.getOtherSide(user.getId()));
    }
}
