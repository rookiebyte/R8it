package com.rit.user.domain.friend;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRequestRepository {

    FriendRequest saveFriendRequest(FriendRequest request);

    Optional<FriendRequest> findExistingRequest(FriendRequest request);

    List<FriendRequest> findByUserIdAndStatus(UUID userId, FriendRequestStatus status);
}
