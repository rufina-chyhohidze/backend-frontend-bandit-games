package be.kdg.banditgames.platform.core.achievement;

import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.domain.FriendshipStatus;
import be.kdg.banditgames.platform.port.in.achievement.ListAvailableAchievementsUseCase;
import be.kdg.banditgames.platform.port.in.achievement.ListUnlockedAchievementsCommand;
import be.kdg.banditgames.platform.port.in.achievement.ListUnlockedAchievementsUseCase;
import be.kdg.banditgames.platform.port.in.achievement.UnlockedAchievementResult;
import be.kdg.banditgames.platform.port.in.friendship.FindFriendshipPort;
import be.kdg.banditgames.platform.port.out.achievement.LoadAchievementsByIdsPort;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerAchievementIdsPort;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ListUnlockedAchievementsUseCaseImpl implements ListUnlockedAchievementsUseCase {
    private final LoadPlayerAchievementIdsPort loadPlayerAchievementIdsPort;
    private final LoadAchievementsByIdsPort loadAchievementsByIdsPort;
    private final FindFriendshipPort findFriendshipPort;

    public ListUnlockedAchievementsUseCaseImpl(
            LoadPlayerAchievementIdsPort loadPlayerAchievementIdsPort,
            LoadAchievementsByIdsPort loadAchievementsByIdsPort,
            FindFriendshipPort findFriendshipPort
    ) {
        this.loadPlayerAchievementIdsPort = loadPlayerAchievementIdsPort;
        this.loadAchievementsByIdsPort = loadAchievementsByIdsPort;
        this.findFriendshipPort = findFriendshipPort;
    }

    @Override
    public List<UnlockedAchievementResult> listUnlockedAchievements(ListUnlockedAchievementsCommand command) {

        if (!command.requesterId().equals(command.playerId())) {
            var friendshipOpt = findFriendshipPort.getFriendshipBetween(
                    command.requesterId(),
                    command.playerId()
            );

            boolean allowed = friendshipOpt.isPresent()
                    && friendshipOpt.get().getStatus() == FriendshipStatus.ACCEPTED;

            if (!allowed) {
                throw new AccessDeniedException("You can only view achievements of your friends");
            }
        }

        List<UUID> ids = loadPlayerAchievementIdsPort.loadAchievementIds(command.playerId());
        if (ids.isEmpty()) return List.of();

        var achievements = loadAchievementsByIdsPort.loadByGameIdAndIds(command.gameId(), ids);

        return achievements.stream()
                .map(UnlockedAchievementResult::fromDomain)
                .toList();
    }

}
