package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;

import java.util.ArrayList;
import java.util.List;

public class Player {
    
    private final PlayerId playerId;
    private String username;
    private List<PlayerId> friends;
    private List<GameId> favoriteGames;
    private List<AchievementId> achievements;
    
    
    private Player(PlayerId playerId, String username, List<PlayerId> friends, List<GameId> favoriteGames, List<AchievementId> achievements) {
        this.playerId = playerId;
        this.username = username;
        this.friends = new ArrayList<>(friends);
        this.favoriteGames = new ArrayList<>(favoriteGames);
        this.achievements = new ArrayList<>(achievements);
    }
    
    
    public static Player createNew(String username) {
        return new Player(PlayerId.create(), username, List.of(), List.of(), List.of());
    }
    
    public static Player rehydrate(PlayerId playerId, String username, List<PlayerId> friends, List<GameId> favoriteGames, List<AchievementId> achievements) {
        return new Player(playerId, username, friends, favoriteGames, achievements);
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public List<PlayerId> getFriends() {
        return friends;
    }

    public List<GameId> getFavoriteGames() {
        return favoriteGames;
    }

    public List<AchievementId> getAchievements() {
        return achievements;
    }
}
