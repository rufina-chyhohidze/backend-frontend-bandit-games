package be.kdg.banditgames.platform.port.out.friendship;

import be.kdg.banditgames.platform.domain.Friendship;

public interface PersistFriendshipPort {
    
    void saveFriendship(Friendship friendship);
    void removeFriendship(Friendship friendship);
    
}
