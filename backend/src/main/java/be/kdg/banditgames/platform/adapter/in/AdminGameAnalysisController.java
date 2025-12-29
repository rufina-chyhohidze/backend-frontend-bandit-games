package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.platform.adapter.in.response.GameReplayExportDto;
import be.kdg.banditgames.platform.adapter.in.response.GameSessionExportDto;
import be.kdg.banditgames.platform.adapter.in.response.MoveReplayExportDto;
import be.kdg.banditgames.platform.adapter.out.ml.MoveRecommendedProjectionJpaEntity;
import be.kdg.banditgames.platform.adapter.out.ml.MoveRecommendedProjectionJpaRepository;
import be.kdg.banditgames.platform.adapter.out.ml.WinProbabilityProjectionJpaEntity;
import be.kdg.banditgames.platform.adapter.out.ml.WinProbabilityProjectionJpaRepository;
import be.kdg.banditgames.platform.port.out.GameSessionExportPort;
import be.kdg.banditgames.platform.port.out.dto.AiMetadataExportData;
import be.kdg.banditgames.platform.port.out.dto.GameSessionExportData;
import be.kdg.banditgames.platform.port.out.dto.GameStateExportData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gameplay/export")
public class AdminGameAnalysisController {

    private final GameSessionExportPort gameSessionExportPort;
    private final MoveRecommendedProjectionJpaRepository moveRecommendedRepository;
    private final WinProbabilityProjectionJpaRepository winProbabilityRepository;
    private final Logger logger = LoggerFactory.getLogger(AdminGameAnalysisController.class);

    public AdminGameAnalysisController(GameSessionExportPort gameSessionExportPort,
                                        MoveRecommendedProjectionJpaRepository moveRecommendedRepository,
                                        WinProbabilityProjectionJpaRepository winProbabilityRepository) {
        this.gameSessionExportPort = gameSessionExportPort;
        this.moveRecommendedRepository = moveRecommendedRepository;
        this.winProbabilityRepository = winProbabilityRepository;
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<GameSessionExportDto>> getAllSessions() {
        logger.info("Fetching all game sessions for export");

        List<GameSessionExportData> sessions = gameSessionExportPort.findAllSessionsOrderedByStartTime();

        List<GameSessionExportDto> dtos = sessions.stream()
                .map(this::toGameSessionDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<byte[]> exportSessionCsv(@PathVariable String sessionId) {
        logger.info("Exporting CSV for session: {}", sessionId);

        UUID uuid;
        try {
            uuid = UUID.fromString(sessionId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return gameSessionExportPort.findSessionById(uuid)
                .map(data -> {
                    // Fetch ML projections for this session
                    SessionId sessId = SessionId.of(uuid);
                    Map<Integer, MoveRecommendedProjectionJpaEntity> moveRecommendations =
                            moveRecommendedRepository.findBySessionId(sessId).stream()
                                    .collect(Collectors.toMap(
                                            MoveRecommendedProjectionJpaEntity::getMoveNumber,
                                            e -> e,
                                            (a, b) -> b
                                    ));
                    Map<Integer, WinProbabilityProjectionJpaEntity> winProbabilities =
                            winProbabilityRepository.findBySessionId(sessId).stream()
                                    .collect(Collectors.toMap(
                                            WinProbabilityProjectionJpaEntity::getMoveNumber,
                                            e -> e,
                                            (a, b) -> b
                                    ));
                    String csv = generateCsv(data, moveRecommendations, winProbabilities);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType("text/csv"));
                    headers.setContentDispositionFormData("attachment", "game_" + sessionId + ".csv");
                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(csv.getBytes());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{sessionId}/replay")
    public ResponseEntity<GameReplayExportDto> getSessionReplay(@PathVariable String sessionId) {
        logger.info("Fetching replay for session: {}", sessionId);

        UUID uuid;
        try {
            uuid = UUID.fromString(sessionId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return gameSessionExportPort.findSessionById(uuid)
                .map(data -> {
                    // Fetch ML projections for this session
                    SessionId sessId = SessionId.of(uuid);
                    Map<Integer, MoveRecommendedProjectionJpaEntity> moveRecommendations =
                            moveRecommendedRepository.findBySessionId(sessId).stream()
                                    .collect(Collectors.toMap(
                                            MoveRecommendedProjectionJpaEntity::getMoveNumber,
                                            e -> e,
                                            (a, b) -> b // keep latest if duplicate
                                    ));
                    Map<Integer, WinProbabilityProjectionJpaEntity> winProbabilities =
                            winProbabilityRepository.findBySessionId(sessId).stream()
                                    .collect(Collectors.toMap(
                                            WinProbabilityProjectionJpaEntity::getMoveNumber,
                                            e -> e,
                                            (a, b) -> b
                                    ));
                    return ResponseEntity.ok(toGameReplayDto(data, moveRecommendations, winProbabilities));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private GameSessionExportDto toGameSessionDto(GameSessionExportData data) {
        int moveCount = data.gameStates() != null ? data.gameStates().size() : 0;

        return new GameSessionExportDto(
                data.sessionId().toString(),
                data.gameId().toString(),
                data.player1Type(),
                data.player2Type(),
                data.gameResult(),
                data.startTime(),
                data.endTime(),
                moveCount
        );
    }

    private GameReplayExportDto toGameReplayDto(GameSessionExportData data,
                                                  Map<Integer, MoveRecommendedProjectionJpaEntity> moveRecommendations,
                                                  Map<Integer, WinProbabilityProjectionJpaEntity> winProbabilities) {
        List<MoveReplayExportDto> moves = new ArrayList<>();

        if (data.gameStates() != null) {
            String previousBoard = null;
            for (GameStateExportData state : data.gameStates()) {
                Integer actualMove = extractActualMove(previousBoard, state.board());
                MoveRecommendedProjectionJpaEntity mlRec = moveRecommendations.get(state.moveNumber());
                WinProbabilityProjectionJpaEntity mlWinProb = winProbabilities.get(state.moveNumber());
                moves.add(toMoveReplayDto(state, actualMove, mlRec, mlWinProb));
                previousBoard = state.board();
            }
        }

        return new GameReplayExportDto(
                data.sessionId().toString(),
                data.gameId().toString(),
                data.player1Type(),
                data.player2Type(),
                data.gameResult(),
                data.startTime(),
                data.endTime(),
                moves
        );
    }

    private MoveReplayExportDto toMoveReplayDto(GameStateExportData state, Integer actualMove,
                                                  MoveRecommendedProjectionJpaEntity mlRec,
                                                  WinProbabilityProjectionJpaEntity mlWinProb) {
        AiMetadataExportData aiMeta = state.aiMetadata();

        Integer aiHardRecommendedMove = null;
        Double aiHardConfidence = null;
        Double aiHardWinProbability = null;
        Integer aiMlRecommendedMove = null;
        Double aiMlConfidence = null;
        Double aiMlWinProbability = null;

        if (aiMeta != null) {
            // MCTS/Hard AI data from gameplay module
            if (aiMeta.bestMove() != null) {
                aiHardRecommendedMove = parseMove(aiMeta.bestMove());
            }
            aiHardWinProbability = aiMeta.heuristicScore();
            if (aiMeta.visitCount() != null && aiMeta.visitCount() > 0) {
                aiHardConfidence = 1.0;
            }

            // ML AI data from gameplay module (for AI_ML games)
            if (aiMeta.recommendedMove() != null) {
                aiMlRecommendedMove = parseMove(aiMeta.recommendedMove());
            }
            aiMlConfidence = aiMeta.confidenceScore();
        }

        // Override with ML projections from platform module (for MCTS games analysis)
        if (mlRec != null) {
            aiMlRecommendedMove = parseMove(mlRec.getRecommendedMove());
            aiMlConfidence = mlRec.getConfidence();
        }

        if (mlWinProb != null) {
            aiMlWinProbability = mlWinProb.getPlayer1WinProbability();
        }

        return new MoveReplayExportDto(
                state.moveNumber(),
                state.playerType(),
                state.playerSide(),
                state.board(),
                state.legalMoves(),
                actualMove,
                aiHardRecommendedMove,
                aiHardConfidence,
                aiHardWinProbability,
                aiMlRecommendedMove,
                aiMlConfidence,
                aiMlWinProbability,
                state.timestamp()
        );
    }

    private Integer parseMove(String moveStr) {
        if (moveStr == null || moveStr.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(moveStr.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer extractActualMove(String previousBoard, String currentBoard) {
        if (currentBoard == null) {
            return null;
        }

        String[] current = currentBoard.split(" ");
        String[] previous = previousBoard != null ? previousBoard.split(" ") : null;

        // Connect4: 7 columns, 6 rows
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                int idx = row * 7 + col;
                if (idx >= current.length) continue;

                String currentCell = current[idx];
                String previousCell = previous != null && idx < previous.length ? previous[idx] : "e";

                if (!"e".equals(currentCell) && "e".equals(previousCell)) {
                    return col;
                }
            }
        }

        return null;
    }

    private String generateCsv(GameSessionExportData data,
                                Map<Integer, MoveRecommendedProjectionJpaEntity> moveRecommendations,
                                Map<Integer, WinProbabilityProjectionJpaEntity> winProbabilities) {
        StringBuilder csv = new StringBuilder();

        csv.append("MoveNumber,PlayerType,PlayerSide,BoardState,LegalMoves,ActualMove,");
        csv.append("AiHardRecommendedMove,AiHardConfidence,AiHardWinProbability,");
        csv.append("AiMlRecommendedMove,AiMlConfidence,AiMlWinProbability,Timestamp\n");

        if (data.gameStates() != null) {
            String previousBoard = null;
            for (GameStateExportData state : data.gameStates()) {
                Integer actualMove = extractActualMove(previousBoard, state.board());
                MoveRecommendedProjectionJpaEntity mlRec = moveRecommendations.get(state.moveNumber());
                WinProbabilityProjectionJpaEntity mlWinProb = winProbabilities.get(state.moveNumber());
                MoveReplayExportDto move = toMoveReplayDto(state, actualMove, mlRec, mlWinProb);

                csv.append(move.moveNumber()).append(",");
                csv.append(escapeCsv(move.playerType())).append(",");
                csv.append(escapeCsv(move.playerSide())).append(",");
                csv.append(escapeCsv(move.boardState())).append(",");
                csv.append(escapeCsv(move.legalMoves())).append(",");
                csv.append(move.actualMove() != null ? move.actualMove() : "").append(",");
                csv.append(move.aiHardRecommendedMove() != null ? move.aiHardRecommendedMove() : "").append(",");
                csv.append(move.aiHardConfidence() != null ? move.aiHardConfidence() : "").append(",");
                csv.append(move.aiHardWinProbability() != null ? move.aiHardWinProbability() : "").append(",");
                csv.append(move.aiMlRecommendedMove() != null ? move.aiMlRecommendedMove() : "").append(",");
                csv.append(move.aiMlConfidence() != null ? move.aiMlConfidence() : "").append(",");
                csv.append(move.aiMlWinProbability() != null ? move.aiMlWinProbability() : "").append(",");
                csv.append(move.timestamp() != null ? move.timestamp().toString() : "").append("\n");

                previousBoard = state.board();
            }
        }

        return csv.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
