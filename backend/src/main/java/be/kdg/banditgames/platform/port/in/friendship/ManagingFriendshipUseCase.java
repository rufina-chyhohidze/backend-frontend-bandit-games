package be.kdg.banditgames.platform.port.in.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;


public interface ManagingFriendshipUseCase {

    Friendship sendFriendRequest(PlayerId fromPlayer, PlayerId toPlayer);

    void acceptFriendRequest(PlayerId fromPlayer, PlayerId toPlayer);

    void rejectFriendRequest(PlayerId fromPlayer, PlayerId toPlayer);

    void removeFriend(PlayerId playerA, PlayerId playerB);
}
