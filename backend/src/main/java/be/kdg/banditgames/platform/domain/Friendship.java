package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.vo.FriendshipId;

import java.time.LocalDateTime;
import java.util.UUID;

public class Friendship {

    private final FriendshipId id;
    private final PlayerId playerA;
    private final PlayerId playerB;
    private final PlayerId initiator; // Tracks who sent the request
    private FriendshipStatus status;
    private LocalDateTime createdAt;

    private Friendship(FriendshipId friendshipId, PlayerId playerA, PlayerId playerB, PlayerId initiator, FriendshipStatus status, LocalDateTime createdAt) {
        this.id = friendshipId;
        this.playerA = playerA;
        this.playerB = playerB;
        this.initiator = initiator;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Friendship createNew(PlayerId fromPlayer, PlayerId toPlayer, FriendshipStatus status) {
        PlayerId playerA;
        PlayerId playerB;

        // Canonical ordering remains for uniqueness (Player A has smaller UUID)
        if (fromPlayer.playerId().compareTo(toPlayer.playerId()) < 0) {
            playerA = fromPlayer;
            playerB = toPlayer;
        } else {
            playerA = toPlayer;
            playerB = fromPlayer;
        }

        return new Friendship(
                FriendshipId.create(),
                playerA,
                playerB,
                fromPlayer,
                status,
                LocalDateTime.now()
        );
    }
    public static Friendship rehydrate(UUID id, PlayerId playerA, PlayerId playerB,
                                       FriendshipStatus status, LocalDateTime createdAt, PlayerId initiator) {

        return new Friendship(
                FriendshipId.of(id),
                playerA,
                playerB,
                initiator,
                status,
                createdAt
        );
    }

    public FriendshipId getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public PlayerId getPlayerA() { return playerA; }
    public PlayerId getPlayerB() { return playerB; }
    public PlayerId getInitiator() { return initiator; }
    public FriendshipStatus getStatus() { return status; }

    
    

    public void accept() {
        if (this.status != FriendshipStatus.PENDING) {
            throw new IllegalStateException("Can only accept pending friend requests");
        }
        this.status = FriendshipStatus.ACCEPTED;
    }

    public void reject() {
        if (this.status != FriendshipStatus.PENDING) {
            throw new IllegalStateException("Can only reject pending friend requests");
        }
        this.status = FriendshipStatus.REJECTED;
    }
}