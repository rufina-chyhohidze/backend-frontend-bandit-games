// src/test/java/be/kdg/banditgames/gameplay/adapter/out/GameSessionMongoAdapterIT.java
package be.kdg.banditgame.gameplay.adapter.out;

import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;
import be.kdg.banditgames.gameplay.adapter.out.GameSessionMongoAdapter;
import be.kdg.banditgames.gameplay.adapter.out.GameSessionMongoEntity;
import be.kdg.banditgames.gameplay.adapter.out.GameStateMongoEmbedded;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
// Uncomment for Testcontainers:
// @ContextConfiguration(initializers = {be.kdg.banditgames.MongoTestContainerInitializer.class})
class GameSessionMongoAdapterIT {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GameSessionMongoAdapter adapter;

    @Test
    void canInsertFakeSessionAndAppendMoveWithAi() {
        UUID sessionId = UUID.randomUUID();
        GameSessionMongoEntity fake = new GameSessionMongoEntity(
            sessionId,
            UUID.randomUUID(),
            PlayerType.HUMAN,
            PlayerType.AI_EASY,
            be.kdg.banditgames.gameplay.domain.GameSessionState.IN_PROGRESS,
            LocalDateTime.now(),
            null,
            null,
            List.of()
        );
        mongoTemplate.save(fake);

        // Append a move + AI evaluation
        GameState domainState = GameState.createNew(
            PlayerType.HUMAN, PlayerSide.B, 1,
            "serializedBoard", "serializedLegalMoves"
        );
        AiMoveMetadata aiMove = new AiMoveMetadata("drop(3)", 0.85, "drop(4)", 0.65, 1200, 7);

        adapter.addGameState(SessionId.of(sessionId), domainState, aiMove);

        GameSessionMongoEntity reloaded = mongoTemplate.findById(sessionId, GameSessionMongoEntity.class);
        assertNotNull(reloaded);
        assertEquals(1, reloaded.getGameStates().size());

        GameStateMongoEmbedded emb = reloaded.getGameStates().get(0);
        assertEquals("drop(4)", emb.getBestMove());
        assertEquals(0.85, emb.getConfidenceScore());
        assertEquals(1200, emb.getVisitCount());
        assertEquals(7, emb.getSearchDepth());
    }
}
