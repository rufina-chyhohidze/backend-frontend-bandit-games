package be.kdg.banditgames.platform.adapter.out;

import be.kdg.banditgames.gameplay.domain.vo.AchievementId;
import be.kdg.banditgames.gameplay.domain.vo.GameId;
import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.port.out.LoadAvailableAchievementsPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class InMemoryAchievementRepositoryAdapter implements LoadAvailableAchievementsPort {

    private final List<Achievement> achievements;

    public InMemoryAchievementRepositoryAdapter() {
        GameId connectFourGameId = GameId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        GameId chessGameId       = GameId.of(UUID.fromString("00000000-0000-0000-0000-000000000002"));

        this.achievements = List.of(
                // Connect Four achievements
                new Achievement(
                        AchievementId.of(UUID.fromString("10000000-0000-0000-0000-000000000001")),
                        connectFourGameId,
                        "First Connect4 Win",
                        "Win your first Connect Four game.",
                        "Play and win any Connect Four match."
                ),
                new Achievement(
                        AchievementId.of(UUID.fromString("10000000-0000-0000-0000-000000000002")),
                        connectFourGameId,
                        "Combo Master",
                        "Create two winning opportunities at the same time.",
                        "Set up the board so that you have two possible winning moves."
                ),

                // Chess achievements (mirroring teacher example)
                new Achievement(
                        AchievementId.of(UUID.fromString("20000000-0000-0000-0000-000000000001")),
                        chessGameId,
                        "First Blood",
                        "Capture your opponent's first piece.",
                        "Capture any enemy piece during a chess game."
                ),
                new Achievement(
                        AchievementId.of(UUID.fromString("20000000-0000-0000-0000-000000000002")),
                        chessGameId,
                        "Speedy Victory",
                        "Win in under 20 moves.",
                        "Checkmate your opponent in fewer than 20 total moves."
                ),
                new Achievement(
                        AchievementId.of(UUID.fromString("20000000-0000-0000-0000-000000000003")),
                        chessGameId,
                        "Castle Time",
                        "Castle kingside or queenside.",
                        "Perform a valid castling move during the game."
                )
        );
    }

    @Override
    public List<Achievement> loadAvailableAchievements(GameId gameId) {
        return achievements.stream()
                .filter(a -> a.getGameId().equals(gameId))
                .toList();
    }

}
