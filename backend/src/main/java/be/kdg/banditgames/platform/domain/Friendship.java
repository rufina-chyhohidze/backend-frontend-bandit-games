package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.PlayerId;

import java.time.LocalDateTime;

public class Friendship {

    private final PlayerId playerA;
    private final PlayerId playerB;
    private FriendshipStatus status;
    private final LocalDateTime createdAt;

    private Friendship(PlayerId playerA, PlayerId playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.status = FriendshipStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
    
    public static Friendship createNew(PlayerId playerA, PlayerId playerB, FriendshipStatus status) {
        Friendship friendship = new Friendship(playerA, playerB);
        friendship.status = status;
        return friendship;
    }
    
    public static Friendship rehydrate(PlayerId playerA, PlayerId playerB, FriendshipStatus status, LocalDateTime createdAt) {
        Friendship friendship = new Friendship(playerA, playerB);
        friendship.status = status;
        return friendship;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PlayerId getPlayerA() { return playerA; }
    public PlayerId getPlayerB() { return playerB; }
    public FriendshipStatus getStatus() { return status; }
    public void accept() { this.status = FriendshipStatus.ACCEPTED; }
    public void reject() { this.status = FriendshipStatus.REJECTED; }
}

