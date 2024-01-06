package com.rit.user.context.friend

import com.rit.user.domain.friend.FriendRequestRepository
import com.rit.user.domain.friend.FriendRequestStatus
import com.rit.user.domain.user.UserRepository
import com.rit.user.factory.UserFactory
import com.rit.user.infrastructure.friend.InMemoryFriendRequestRepository
import com.rit.user.infrastructure.user.InMemoryUserRepository
import spock.lang.Specification

class FriendRequestsServiceSpec extends Specification implements UserFactory {

    private FriendRequestRepository friendRequestRepository
    private UserRepository userRepository
    private FriendRequestsService friendRequestsService

    def setup() {
        friendRequestRepository = new InMemoryFriendRequestRepository()
        userRepository = new InMemoryUserRepository()
        friendRequestsService = new FriendRequestsService(friendRequestRepository, userRepository)
    }

    def 'create twice same friend request, expect expect request pending'() {
        given:
        def userA = userRepository.saveUser(user(), credentials())
        def userB = userRepository.saveUser(userBuilder().email('friend@example.com').build(), credentials())
        def context = userContext(userA)
        when:
        friendRequestsService.createFriendRequest(context, userB.id)
        def response = friendRequestsService.createFriendRequest(context, userB.id)
        then:
        response != null
        response.status == FriendRequestStatus.PENDING
        friendRequestRepository.findByUserIdAndStatus(userA.id, FriendRequestStatus.PENDING).size() == 1
        friendRequestsService.friendsList(context).size() == 0
    }

    def 'create twice friend request, with different side, expect request accepted'() {
        given:
        def userA = userRepository.saveUser(user(), credentials())
        def userB = userRepository.saveUser(userBuilder().email('friend@example.com').build(), credentials())
        when:
        friendRequestsService.createFriendRequest(userContext(userB), userA.id)
        then:
        friendRequestsService.friendRequestList(userContext(userA)).size() == 1
        friendRequestsService.friendRequestList(userContext(userB)).size() == 1
        when:
        def response = friendRequestsService.createFriendRequest(userContext(userA), userB.id)
        then:
        response != null
        response.status == FriendRequestStatus.ACCEPTED
        friendRequestRepository.findByUserIdAndStatus(userA.id, FriendRequestStatus.ACCEPTED).size() == 1
        friendRequestRepository.findByUserIdAndStatus(userB.id, FriendRequestStatus.ACCEPTED).size() == 1
        friendRequestsService.friendsList(userContext(userA)).size() == 1
        friendRequestsService.friendsList(userContext(userB)).size() == 1
        friendRequestsService.friendRequestList(userContext(userA)).size() == 0
        friendRequestsService.friendRequestList(userContext(userB)).size() == 0
    }

    def 'cancelled existing friend request, expect request cancelled'() {
        given:
        def userA = userRepository.saveUser(user(), credentials())
        def userB = userRepository.saveUser(userBuilder().email('friend@example.com').build(), credentials())
        when:
        friendRequestsService.createFriendRequest(userContext(userA), userB.id)
        def response = friendRequestsService.cancelFriendRequest(userContext(userA), userB.id)
        then:
        response != null
        response.status == FriendRequestStatus.CANCELLED
        friendRequestRepository.findByUserIdAndStatus(userA.id, FriendRequestStatus.CANCELLED).size() == 1
    }

    def 'cancelled existing friend request, with different side, expect request declined'() {
        given:
        def userA = userRepository.saveUser(user(), credentials())
        def userB = userRepository.saveUser(userBuilder().email('friend@example.com').build(), credentials())
        when:
        friendRequestsService.createFriendRequest(userContext(userA), userB.id)
        def response = friendRequestsService.cancelFriendRequest(userContext(userB), userA.id)
        then:
        response != null
        response.status == FriendRequestStatus.DECLINED
        friendRequestRepository.findByUserIdAndStatus(userA.id, FriendRequestStatus.DECLINED).size() == 1
    }
}
