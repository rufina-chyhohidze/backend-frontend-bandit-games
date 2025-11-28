package be.kdg.banditgames.platform.adapter.out.achievement;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.port.out.LoadAvailableAchievementsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AchievementJpaAdapter implements LoadAvailableAchievementsPort {
    private final AchievementJpaRepository repo;

    public AchievementJpaAdapter(AchievementJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Achievement> loadAvailableAchievements(GameId gameId) {
        return repo.findByGameId(gameId.gameId())
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Achievement toDomain(AchievementJpaEntity e) {
        return new Achievement(
                e.toAchievementId(),
                e.toGameId(),
                e.getName(),
                e.getDescription(),
                e.getUnlockHint()
        );
    }
}
