package be.kdg.banditgames.platform.core;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;
import be.kdg.banditgames.platform.domain.FriendshipStatus;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.in.friendship.FindFriendshipPort;
import be.kdg.banditgames.platform.port.in.friendship.ManagingFriendshipUseCase;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.friendship.LoadFriendshipPort;
import be.kdg.banditgames.platform.port.out.friendship.PersistFriendshipPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendshipUseCaseImpl implements ManagingFriendshipUseCase, FindFriendshipPort {

    private final PersistFriendshipPort persistFriendshipPort;
    private final LoadFriendshipPort loadFriendshipPort;
    private final LoadPlayerPort loadPlayerPort;

    public FriendshipUseCaseImpl(PersistFriendshipPort persistFriendshipPort,
                                 LoadFriendshipPort loadFriendshipPort,
                                 LoadPlayerPort loadPlayerPort) {
        this.persistFriendshipPort = persistFriendshipPort;
        this.loadFriendshipPort = loadFriendshipPort;
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    public Friendship sendFriendRequest(PlayerId fromPlayer, PlayerId toPlayer) {
        Optional<Friendship> existing = getFriendshipBetween(fromPlayer, toPlayer);
        if (existing.isPresent()) {
            throw new IllegalStateException("Friendship or request already exists.");
        }

        Friendship friendship = Friendship.createNew(fromPlayer, toPlayer, FriendshipStatus.PENDING);
        persistFriendshipPort.saveFriendship(friendship);
        return friendship;
    }

    @Override
    public void acceptFriendRequest(PlayerId fromPlayer, PlayerId toPlayer) {
        Friendship friendship = getFriendshipBetween(fromPlayer, toPlayer)
                .orElseThrow(() -> new IllegalStateException("Friend request does not exist."));
        friendship.accept();
        persistFriendshipPort.saveFriendship(friendship);
    }

    @Override
    public void rejectFriendRequest(PlayerId fromPlayer, PlayerId toPlayer) {
        Friendship friendship = getFriendshipBetween(fromPlayer, toPlayer)
                .orElseThrow(() -> new IllegalStateException("Friend request does not exist."));
        persistFriendshipPort.removeFriendship(friendship);
    }

    @Override
    public void removeFriend(PlayerId playerA, PlayerId playerB) {
        Friendship friendship = getFriendshipBetween(playerA, playerB)
                .orElseThrow(() -> new IllegalStateException("Friendship does not exist."));
        persistFriendshipPort.removeFriendship(friendship);
    }

    @Override
    public List<Player> getFriends(PlayerId playerId) {
        return loadFriendshipPort.loadFriendshipsForPlayer(playerId).stream()
                .filter(f -> f.getStatus() == FriendshipStatus.ACCEPTED)
                .map(f -> {
                    // Get the OTHER player in the friendship
                    PlayerId friendId = f.getPlayerA().equals(playerId) ? f.getPlayerB() : f.getPlayerA();
                    return loadPlayerPort.loadById(friendId)
                            .orElseThrow(() -> new IllegalStateException("Player not found: " + friendId));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Player> getPendingRequests(PlayerId playerId) {
        // ✅ FIX: Get requests where YOU are the RECIPIENT (someone sent TO you)
        // Check if the initiator is NOT you
        return loadFriendshipPort.loadFriendshipsForPlayer(playerId).stream()
                .filter(f -> f.getStatus() == FriendshipStatus.PENDING &&
                        !f.getInitiator().equals(playerId)) // Initiator is NOT you
                .map(f -> {
                    // The requester is the initiator
                    PlayerId requesterId = f.getInitiator();
                    return loadPlayerPort.loadById(requesterId)
                            .orElseThrow(() -> new IllegalStateException("Player not found: " + requesterId));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Player> getSentRequests(PlayerId playerId) {
        return loadFriendshipPort.loadFriendshipsForPlayer(playerId).stream()
                .filter(f -> f.getStatus() == FriendshipStatus.PENDING &&
                        f.getInitiator().equals(playerId)) // Initiator IS you
                .map(f -> {
                    PlayerId recipientId = f.getPlayerA().equals(playerId) ?
                            f.getPlayerB() : f.getPlayerA();
                    return loadPlayerPort.loadById(recipientId)
                            .orElseThrow(() -> new IllegalStateException("Player not found: " + recipientId));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Friendship> getFriendshipBetween(PlayerId playerA, PlayerId playerB) {
        return loadFriendshipPort.loadFriendshipsForPlayer(playerA).stream()
                .filter(f -> (f.getPlayerA().equals(playerA) && f.getPlayerB().equals(playerB)) ||
                        (f.getPlayerA().equals(playerB) && f.getPlayerB().equals(playerA)))
                .findFirst();
    }
}