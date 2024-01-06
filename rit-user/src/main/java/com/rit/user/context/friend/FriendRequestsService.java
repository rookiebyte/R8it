package com.rit.user.context.friend;

import com.rit.starterboot.servlet.domain.user.UserContext;
import com.rit.user.context.friend.dto.FriendRequestDto;
import com.rit.user.domain.friend.FriendRequest;
import com.rit.user.domain.friend.FriendRequestRepository;
import com.rit.user.domain.friend.FriendRequestStatus;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendRequestsService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public FriendRequest createFriendRequest(UserContext userContext, UUID targetId) {
        final var requester = findUser(userContext.id());
        final var target = findUser(targetId);
        final var request = FriendRequest.builder()
                                         .requesterId(requester.getId())
                                         .targetId(target.getId())
                                         .status(FriendRequestStatus.PENDING)
                                         .build();

        final var optionalOldRequest = friendRequestRepository.findExistingRequest(request);
        if (optionalOldRequest.isEmpty()) {
            return friendRequestRepository.saveFriendRequest(request);
        }
        final var oldRequest = optionalOldRequest.get();
        if (requester.getId().equals(oldRequest.getTargetId())) {
            oldRequest.setStatus(FriendRequestStatus.ACCEPTED);
            return friendRequestRepository.saveFriendRequest(oldRequest);
        }
        return oldRequest;
    }

    public FriendRequest cancelFriendRequest(UserContext userContext, UUID targetId) {
        final var requester = findUser(userContext.id());
        final var target = findUser(targetId);
        final var request = FriendRequest.builder()
                                         .requesterId(requester.getId())
                                         .targetId(target.getId())
                                         .status(FriendRequestStatus.CANCELLED)
                                         .build();

        final var optionalOldRequest = friendRequestRepository.findExistingRequest(request);
        if (optionalOldRequest.isEmpty()) {
            return friendRequestRepository.saveFriendRequest(request);
        }
        final var oldRequest = optionalOldRequest.get();
        final var status = oldRequest.isRequester(requester) ? FriendRequestStatus.CANCELLED : FriendRequestStatus.DECLINED;
        oldRequest.setStatus(status);
        return friendRequestRepository.saveFriendRequest(oldRequest);
    }

    public List<FriendRequestDto> friendRequestList(UserContext userContext) {
        var user = findUser(userContext.id());
        return friendRequestRepository.findByUserIdAndStatus(user.getId(), FriendRequestStatus.PENDING)
                                      .stream().map(it -> new FriendRequestDto(user, it))
                                      .toList();
    }

    public List<User> friendsList(UserContext userContext) {
        var friendRequests = friendRequestRepository.findByUserIdAndStatus(userContext.id(), FriendRequestStatus.ACCEPTED);
        var ids = friendRequests.stream().map(it -> it.getOtherSide(userContext.id())).collect(Collectors.toSet());
        return userRepository.findUserByIds(ids);
    }

    private User findUser(UUID id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
