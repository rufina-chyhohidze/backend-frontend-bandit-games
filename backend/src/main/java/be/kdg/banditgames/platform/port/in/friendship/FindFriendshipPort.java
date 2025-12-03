package be.kdg.banditgames.platform.port.in.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;
import be.kdg.banditgames.platform.domain.Player;

import java.util.List;
import java.util.Optional;

public interface FindFriendshipPort {

    List<Player> getFriends(PlayerId playerId);

    List<Player> getPendingRequests(PlayerId playerId);

    Optional<Friendship> getFriendshipBetween(PlayerId playerA, PlayerId playerB);

    List<Player> getSentRequests(PlayerId playerId);
}
