package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.gameplay.domain.vo.AchievementId;
import be.kdg.banditgames.gameplay.domain.vo.GameId;
import be.kdg.banditgames.gameplay.domain.vo.PlayerId;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Player {
    private final PlayerId playerId;
    private String username;

    private final Set<PlayerId> friends = new HashSet<>();
    private final Set<GameId> favouriteGames = new HashSet<>();
    private final Set<AchievementId> unlockedAchievements = new HashSet<>();

    private final Set<PlayerId> outgoingFriendRequests = new HashSet<>();
    private final Set<PlayerId> incomingFriendRequests = new HashSet<>();
    public Player(PlayerId playerId, String username) {
        this.playerId = Objects.requireNonNull(playerId);
        setUsername(username);
    }

    public static Player createNew(String username) {
        return new Player(PlayerId.create(), username);
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        this.username = username;
    }

    public List<PlayerId> getFriends() {
        return List.copyOf(friends);
    }

    public List<GameId> getFavouriteGames() {
        return List.copyOf(favouriteGames);
    }

    public List<AchievementId> getUnlockedAchievements() {
        return List.copyOf(unlockedAchievements);
    }

    // --- Achievements ---

    public void addAchievement(AchievementId achievementId) {
        Objects.requireNonNull(achievementId);
        unlockedAchievements.add(achievementId);
    }

    public boolean hasUnlocked(AchievementId achievementId) {
        return unlockedAchievements.contains(achievementId);
    }

    // --- Favourite games ---

    public void addFavouriteGame(GameId gameId) {
        favouriteGames.add(Objects.requireNonNull(gameId));
    }

    public void removeFavouriteGame(GameId gameId) {
        favouriteGames.remove(gameId);
    }

    // --- Friendships ---

    /**
     * Player sends a friend request to another player.
     * Use case should also call receiveFriendRequest on the other Player.
     */
    public void requestFriendship(PlayerId otherPlayerId) {
        validateOtherPlayer(otherPlayerId);
        if (friends.contains(otherPlayerId)) return; // already friends
        outgoingFriendRequests.add(otherPlayerId);
    }

    /**
     * Internal method to register that someone else requested friendship with this player.
     * Should be called from use case together with requestFriendship on the requester.
     */
    public void receiveFriendRequest(PlayerId fromPlayerId) {
        validateOtherPlayer(fromPlayerId);
        if (friends.contains(fromPlayerId)) return;
        incomingFriendRequests.add(fromPlayerId);
    }

    /**
     * Accept a friendship request from another player.
     * Use case should also call acceptFriendship on the other side.
     */
    public void acceptFriendship(PlayerId otherPlayerId) {
        validateOtherPlayer(otherPlayerId);
        if (!incomingFriendRequests.remove(otherPlayerId)) {
            // no pending request; in a simple model we just ignore
            return;
        }
        friends.add(otherPlayerId);
        // if we had an outgoing request for the same player, clear it
        outgoingFriendRequests.remove(otherPlayerId);
    }

    /**
     * Deny a friendship request.
     */
    public void denyFriendship(PlayerId otherPlayerId) {
        validateOtherPlayer(otherPlayerId);
        incomingFriendRequests.remove(otherPlayerId);
    }

    /**
     * Remove an existing friend.
     */
    public void removeFriendship(PlayerId otherPlayerId) {
        validateOtherPlayer(otherPlayerId);
        friends.remove(otherPlayerId);
    }

    private void validateOtherPlayer(PlayerId otherPlayerId) {
        Objects.requireNonNull(otherPlayerId, "Other player id cannot be null");
        if (this.playerId.equals(otherPlayerId)) {
            throw new IllegalArgumentException("Player cannot perform friendship operations with themselves");
        }
    }

    // getters if we ever need to inspect pending requests in a use case

    public List<PlayerId> getOutgoingFriendRequests() {
        return List.copyOf(outgoingFriendRequests);
    }

    public List<PlayerId> getIncomingFriendRequests() {
        return List.copyOf(incomingFriendRequests);
    }
}