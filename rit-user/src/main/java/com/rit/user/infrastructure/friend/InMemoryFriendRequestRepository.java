package com.rit.user.infrastructure.friend;

import com.rit.robusta.inmemory.InMemoryRepository;
import com.rit.user.domain.friend.FriendRequest;
import com.rit.user.domain.friend.FriendRequestRepository;
import com.rit.user.domain.friend.FriendRequestStatus;
import com.rit.user.infrastructure.friend.entity.FriendRequestEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryFriendRequestRepository extends InMemoryRepository<UUID, FriendRequestEntity> implements FriendRequestRepository {

    public InMemoryFriendRequestRepository() {
        super(UUID.class, FriendRequestEntity.class);
    }

    @Override
    protected UUID provideKey(FriendRequestEntity entity) {
        return entity.getId();
    }

    @Override
    protected void overrideKey(FriendRequestEntity entity, UUID key) {
        entity.setId(key);
    }

    @Override
    public FriendRequest saveFriendRequest(FriendRequest request) {
        var entity = new FriendRequestEntity(request);
        return save(entity).toDomain();
    }

    @Override
    public Optional<FriendRequest> findExistingRequest(FriendRequest request) {
        return findBy(e -> e.getLeft().equals(request.getLeft()) || e.getRight().equals(request.getRight()))
                .map(FriendRequestEntity::toDomain);
    }

    @Override
    public List<FriendRequest> findByUserIdAndStatus(UUID userId, FriendRequestStatus status) {
        return findAllBy(e -> (e.getLeft().equals(userId) || e.getRight().equals(userId)) && e.getStatus().equals(status))
                .stream()
                .map(FriendRequestEntity::toDomain)
                .toList();
    }
}
