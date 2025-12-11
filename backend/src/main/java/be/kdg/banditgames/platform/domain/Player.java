package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;

import java.util.ArrayList;
import java.util.List;

public class Player {
    
    private final PlayerId playerId;
    private String username;
    private List<GameId> favoriteGames;
    private List<AchievementId> achievements;
    
    
    private Player(PlayerId playerId, String username, List<GameId> favoriteGames, List<AchievementId> achievements) {
        this.playerId = playerId;
        this.username = username;
        this.favoriteGames = new ArrayList<>(favoriteGames);
        this.achievements = new ArrayList<>(achievements);
    }
    
    
    public static Player createNew(String username) {
        return new Player(PlayerId.create(), username, List.of(), List.of());
    }
    
    public static Player rehydrate(PlayerId playerId, String username, List<GameId> favoriteGames, List<AchievementId> achievements) {
        return new Player(playerId, username, favoriteGames, achievements);
    }

    // In Player.java
    public static Player createNewWithId(PlayerId playerId, String username) {
        return new Player(playerId, username, List.of(), List.of());
    }

    public void addFavoriteGame(GameId gameId) {
        if (!favoriteGames.contains(gameId)) {
            favoriteGames.add(gameId);
        }
    }

    public void removeFavoriteGame(GameId gameId) {
        favoriteGames.remove(gameId);
    }

    public boolean isFavorite(GameId gameId) {
        return favoriteGames.contains(gameId);
    }
    public PlayerId getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public List<GameId> getFavoriteGames() {
        return favoriteGames;
    }

    public List<AchievementId> getAchievements() {
        return achievements;
    }

    public void addAchievement(AchievementId achievementId) {
        if (!achievements.contains(achievementId)) {
            achievements.add(achievementId);
        }
    }
}
