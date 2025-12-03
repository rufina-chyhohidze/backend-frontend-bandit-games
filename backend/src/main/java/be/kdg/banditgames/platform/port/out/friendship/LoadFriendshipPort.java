package be.kdg.banditgames.platform.port.out.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;

import java.util.List;

public interface LoadFriendshipPort {

    List<Friendship> loadFriendshipsForPlayer(PlayerId playerId);
}
