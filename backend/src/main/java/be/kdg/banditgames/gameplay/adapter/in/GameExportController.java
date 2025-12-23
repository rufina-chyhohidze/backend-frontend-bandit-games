package be.kdg.banditgames.gameplay.adapter.in;

import be.kdg.banditgames.gameplay.adapter.out.AiMetadataEmbedded;
import be.kdg.banditgames.gameplay.adapter.out.GameSessionMongoEntity;
import be.kdg.banditgames.gameplay.adapter.out.GameStateMongoEmbedded;
import be.kdg.banditgames.gameplay.adapter.out.MongoGameplayRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gameplay/export")
public class GameExportController {

    private final MongoGameplayRepository mongoGameplayRepository;

    public GameExportController(MongoGameplayRepository mongoGameplayRepository) {
        this.mongoGameplayRepository = mongoGameplayRepository;
    }


    @GetMapping("/sessions")
    public ResponseEntity<List<GameSessionSummaryDto>> getAllSessions() {
        List<GameSessionMongoEntity> entities = mongoGameplayRepository.findAll();

        List<GameSessionSummaryDto> summaries = entities.stream()
                .map(entity -> new GameSessionSummaryDto(
                        entity.getSessionId().toString(),
                        entity.getGameId().toString(),
                        entity.getPlayerType() != null ? entity.getPlayerType().name() : "UNKNOWN",
                        entity.getPlayer2Type() != null ? entity.getPlayer2Type().name() : "UNKNOWN",
                        entity.getGameResult() != null ? entity.getGameResult().name() : null,
                        entity.getStartTime() != null ? entity.getStartTime().toString() : null,
                        entity.getEndTime() != null ? entity.getEndTime().toString() : null,
                        entity.getGameStates() != null ? entity.getGameStates().size() : 0
                ))
                .toList();

        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{sessionId}")
    public void exportGameAsCsv(@PathVariable UUID sessionId, HttpServletResponse response) throws IOException {
        GameSessionMongoEntity entity = mongoGameplayRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found: " + sessionId));

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"game_" + sessionId + ".csv\"");

        PrintWriter writer = response.getWriter();

        writer.println("move_number,player_type,player_side,board_state,legal_moves," +
                "actual_move,ai_hard_recommended_move,ai_hard_confidence,ai_hard_win_probability," +
                "ai_ml_recommended_move,ai_ml_confidence,ai_ml_win_probability,timestamp");

        List<GameStateMongoEmbedded> gameStates = entity.getGameStates();
        if (gameStates != null) {
            for (GameStateMongoEmbedded state : gameStates) {
                AiMetadataEmbedded aiMeta = state.getAiMetadataEmbedded();

                writer.printf(java.util.Locale.US, "%d,%s,%s,\"%s\",\"%s\",%s,%s,%s,%s,%s,%s,%s,%s%n",
                        state.getMoveNumber(),
                        state.getPlayerType() != null ? state.getPlayerType().name() : "",
                        state.getPlayerSide() != null ? state.getPlayerSide().name() : "",
                        escapeCSV(state.getBoard()),
                        escapeCSV(state.getLegalMoves()),
                        aiMeta != null && aiMeta.getActualMove() != null ? aiMeta.getActualMove() : "",
                        aiMeta != null && aiMeta.getAiHardRecommendedMove() != null ? aiMeta.getAiHardRecommendedMove() : "",
                        aiMeta != null && aiMeta.getAiHardConfidence() != null ? String.format(java.util.Locale.US, "%.4f", aiMeta.getAiHardConfidence()) : "",
                        aiMeta != null && aiMeta.getAiHardWinProbability() != null ? String.format(java.util.Locale.US, "%.4f", aiMeta.getAiHardWinProbability()) : "",
                        aiMeta != null && aiMeta.getAiMlRecommendedMove() != null ? aiMeta.getAiMlRecommendedMove() : "",
                        aiMeta != null && aiMeta.getAiMlConfidence() != null ? String.format(java.util.Locale.US, "%.4f", aiMeta.getAiMlConfidence()) : "",
                        aiMeta != null && aiMeta.getAiMlWinProbability() != null ? String.format(java.util.Locale.US, "%.4f", aiMeta.getAiMlWinProbability()) : "",
                        state.getTimestamp() != null ? state.getTimestamp().toString() : ""
                );
            }
        }

        writer.flush();
    }

    @GetMapping("/{sessionId}/replay")
    public ResponseEntity<GameReplayDto> getGameReplay(@PathVariable UUID sessionId) {
        GameSessionMongoEntity entity = mongoGameplayRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found: " + sessionId));

        List<MoveReplayDto> moves = entity.getGameStates().stream()
                .map(state -> {
                    AiMetadataEmbedded aiMeta = state.getAiMetadataEmbedded();
                    return new MoveReplayDto(
                            state.getMoveNumber(),
                            state.getPlayerType() != null ? state.getPlayerType().name() : null,
                            state.getPlayerSide() != null ? state.getPlayerSide().name() : null,
                            state.getBoard(),
                            state.getLegalMoves(),
                            aiMeta != null ? aiMeta.getActualMove() : null,
                            aiMeta != null ? aiMeta.getAiHardRecommendedMove() : null,
                            aiMeta != null ? aiMeta.getAiHardConfidence() : null,
                            aiMeta != null ? aiMeta.getAiHardWinProbability() : null,
                            aiMeta != null ? aiMeta.getAiMlRecommendedMove() : null,
                            aiMeta != null ? aiMeta.getAiMlConfidence() : null,
                            aiMeta != null ? aiMeta.getAiMlWinProbability() : null,
                            state.getTimestamp() != null ? state.getTimestamp().toString() : null
                    );
                })
                .toList();

        GameReplayDto replay = new GameReplayDto(
                entity.getSessionId().toString(),
                entity.getGameId().toString(),
                entity.getPlayerType() != null ? entity.getPlayerType().name() : null,
                entity.getPlayer2Type() != null ? entity.getPlayer2Type().name() : null,
                entity.getGameResult() != null ? entity.getGameResult().name() : null,
                entity.getStartTime() != null ? entity.getStartTime().toString() : null,
                entity.getEndTime() != null ? entity.getEndTime().toString() : null,
                moves
        );

        return ResponseEntity.ok(replay);
    }

    private String escapeCSV(String value) {
        if (value == null) return "";
        return value.replace("\"", "\"\"");
    }

    public record GameReplayDto(
            String sessionId,
            String gameId,
            String player1Type,
            String player2Type,
            String gameResult,
            String startTime,
            String endTime,
            List<MoveReplayDto> moves
    ) {}

    public record MoveReplayDto(
            int moveNumber,
            String playerType,
            String playerSide,
            String boardState,
            String legalMoves,
            Integer actualMove,
            Integer aiHardRecommendedMove,
            Double aiHardConfidence,
            Double aiHardWinProbability,
            Integer aiMlRecommendedMove,
            Double aiMlConfidence,
            Double aiMlWinProbability,
            String timestamp
    ) {}

    public record GameSessionSummaryDto(
            String sessionId,
            String gameId,
            String player1Type,
            String player2Type,
            String gameResult,
            String startTime,
            String endTime,
            int moveCount
    ) {}
}

