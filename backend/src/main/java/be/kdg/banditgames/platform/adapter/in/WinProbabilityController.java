package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.platform.adapter.in.response.WinProbabilityDto;
import be.kdg.banditgames.platform.adapter.out.ml.WinProbabilityProjectionJpaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/gameplay/win-probability")
public class WinProbabilityController {

    private final WinProbabilityProjectionJpaRepository winProbabilityRepository;
    private final Logger logger = LoggerFactory.getLogger(WinProbabilityController.class);

    public WinProbabilityController(WinProbabilityProjectionJpaRepository winProbabilityRepository) {
        this.winProbabilityRepository = winProbabilityRepository;
    }

    @GetMapping("/{sessionId}")
    @Transactional
    public ResponseEntity<WinProbabilityDto> getWinProbability(
            @PathVariable String sessionId,
            @RequestParam(required = false) Integer moveNumber) {
        logger.info("Fetching win probability for session: {}, moveNumber: {}", sessionId, moveNumber);

        UUID uuid;
        try {
            uuid = UUID.fromString(sessionId);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid session ID format: {}", sessionId);
            return ResponseEntity.badRequest().build();
        }

        SessionId sessionIdObj = SessionId.of(uuid);

        if (moveNumber != null) {
            return winProbabilityRepository.findBySessionIdAndMoveNumber(sessionIdObj, moveNumber)
                    .map(entity -> {
                        logger.info("Found win probability for session {} at move {}: P1={}, P2={}",
                                sessionId, moveNumber, entity.getPlayer1WinProbability(), entity.getPlayer2WinProbability());
                        return ResponseEntity.ok(new WinProbabilityDto(
                                entity.getSessionId().sessionsId().toString(),
                                entity.getMoveNumber(),
                                entity.getPlayer1WinProbability(),
                                entity.getPlayer2WinProbability(),
                                entity.getActivePlayerWinProbability()
                        ));
                    })
                    .orElseGet(() -> {
                        logger.info("No win probability found for session {} at move {}", sessionId, moveNumber);
                        return ResponseEntity.notFound().build();
                    });
        }

        return winProbabilityRepository.findFirstBySessionIdOrderByMoveNumberDesc(sessionIdObj)
                .map(entity -> {
                    logger.info("Found win probability for session {}: P1={}, P2={}",
                            sessionId, entity.getPlayer1WinProbability(), entity.getPlayer2WinProbability());
                    return ResponseEntity.ok(new WinProbabilityDto(
                            entity.getSessionId().sessionsId().toString(),
                            entity.getMoveNumber(),
                            entity.getPlayer1WinProbability(),
                            entity.getPlayer2WinProbability(),
                            entity.getActivePlayerWinProbability()
                    ));
                })
                .orElseGet(() -> {
                    logger.info("No win probability found for session: {}", sessionId);
                    return ResponseEntity.notFound().build();
                });
    }
}
