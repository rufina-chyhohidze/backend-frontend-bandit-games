package be.kdg.banditgames.platform.adapter.out.achievement;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.port.out.achievement.LoadAchievementsByIdsPort;
import be.kdg.banditgames.platform.port.out.achievement.LoadAvailableAchievementsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class AchievementJpaAdapter implements LoadAvailableAchievementsPort, LoadAchievementsByIdsPort {
    private final AchievementJpaRepository repo;

    public AchievementJpaAdapter(AchievementJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Achievement> loadAvailableAchievements(GameId gameId) {
        return repo.findByGameId(gameId.gameId())
                .stream()
                .map(AchievementJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Achievement> loadByGameIdAndIds(GameId gameId, List<UUID> achievementIds) {
        return repo.findByGameIdAndAchievementIdIn(gameId.gameId(), achievementIds)
                .stream()
                .map(AchievementJpaMapper::toDomain)
                .toList();
    }
}
