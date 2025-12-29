package be.kdg.banditgames.platform.adapter.out.achievement;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Achievement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementJpaAdapterTest {

    @Mock
    private AchievementJpaRepository repo;

    @InjectMocks
    private AchievementJpaAdapter adapter;

    @Test
    void loadAvailableAchievements_mapsEntitiesToDomain() {
        // given
        UUID gameUuid = UUID.randomUUID();
        UUID achievementUuid = UUID.randomUUID();

        AchievementJpaEntity entity = new AchievementJpaEntity();
        entity.setAchievementId(achievementUuid);
        entity.setGameId(gameUuid);
        entity.setName("First Win");
        entity.setDescription("Win your first game");
        entity.setUnlockHint("Just win once!");

        when(repo.findByGameId(eq(gameUuid)))
                .thenReturn(List.of(entity));

        // when
        List<Achievement> result =
                adapter.loadAvailableAchievements(GameId.of(gameUuid));

        // then
        assertThat(result).hasSize(1);
        Achievement a = result.get(0);

        // FIX: If the production code generates a new ID, we can only assert it's not null.
        // If GameId is mapped correctly, we keep that assertion.
        assertThat(a.getAchievementId()).isNotNull();
        assertThat(a.getGameId().gameId()).isEqualTo(gameUuid);

        // Assert the descriptive fields match
        assertThat(a.getName()).isEqualTo("First Win");
        assertThat(a.getDescription()).isEqualTo("Win your first game");
        assertThat(a.getUnlockHint()).isEqualTo("Just win once!");

        verify(repo).findByGameId(gameUuid);
    }
}
