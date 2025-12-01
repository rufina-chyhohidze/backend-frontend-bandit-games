package be.kdg.banditgames.platform.port.in.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;

import java.util.List;
import java.util.Optional;

public interface FindLobbyPort {

    List<PlayerId> getFriends(PlayerId playerId);

    List<PlayerId> getPendingRequests(PlayerId playerId);

    Optional<Friendship> getFriendshipBetween(PlayerId playerA, PlayerId playerB);
}
