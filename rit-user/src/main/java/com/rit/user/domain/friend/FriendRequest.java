package com.rit.user.domain.friend;

import com.rit.robusta.util.Preconditions;
import com.rit.user.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

import static com.rit.user.domain.friend.FriendRequestSideMode.LEFT_REQUESTER;
import static com.rit.user.domain.friend.FriendRequestSideMode.RIGHT_REQUESTER;

@Getter
public final class FriendRequest {

    private final UUID id;
    private final UUID left;
    private final UUID right;
    private final FriendRequestSideMode requestSideMode;
    private FriendRequestStatus status;

    @Builder
    private FriendRequest(UUID id, UUID requesterId, UUID targetId, FriendRequestStatus status) {
        this.id = id;
        this.status = status;
        var difference = requesterId.compareTo(targetId);
        Preconditions.checkArgument(difference, d -> d != 0, "UUID difference must be different than 0");
        left = difference < 0 ? requesterId : targetId;
        right = difference < 0 ? targetId : requesterId;
        requestSideMode = difference < 0 ? LEFT_REQUESTER : RIGHT_REQUESTER;
    }

    public FriendRequest(UUID id, UUID left, UUID right, FriendRequestSideMode requestSideMode, FriendRequestStatus status) {
        this.id = id;
        this.left = left;
        this.right = right;
        this.requestSideMode = requestSideMode;
        this.status = status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public UUID getRequesterId() {
        return requestSideMode == LEFT_REQUESTER ? left : right;
    }

    public UUID getTargetId() {
        return requestSideMode == LEFT_REQUESTER ? right : left;
    }

    public UUID getOtherSide(UUID id) {
        if (id.equals(left)) {
            return right;
        }
        return left;
    }

    public boolean isRequester(User user) {
        return getRequesterId().equals(user.getId());
    }
}
