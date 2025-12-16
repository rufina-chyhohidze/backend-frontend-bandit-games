package be.kdg.banditgames.gameplay.port.in.gameSession;

import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.domain.GameResult;

import java.time.LocalDateTime;

public record GameResultsCommand (
        LocalDateTime occurredAt,
        SessionId sessionId,
        GameResult gameResult
){
}
