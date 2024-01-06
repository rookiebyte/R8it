package com.rit.user.infrastructure.friend.entity;

import com.google.common.base.Objects;
import com.rit.user.domain.friend.FriendRequest;
import com.rit.user.domain.friend.FriendRequestSideMode;
import com.rit.user.domain.friend.FriendRequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FriendRequestEntity {

    private UUID id;
    private UUID left;
    private UUID right;
    private FriendRequestSideMode requestSideMode;
    private FriendRequestStatus status;

    public FriendRequestEntity(FriendRequest friendRequest) {
        this.id = friendRequest.getId();
        this.left = friendRequest.getLeft();
        this.right = friendRequest.getRight();
        this.requestSideMode = friendRequest.getRequestSideMode();
        this.status = friendRequest.getStatus();
    }

    public FriendRequest toDomain() {
        return new FriendRequest(id, left, right, requestSideMode, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequestEntity that = (FriendRequestEntity) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
