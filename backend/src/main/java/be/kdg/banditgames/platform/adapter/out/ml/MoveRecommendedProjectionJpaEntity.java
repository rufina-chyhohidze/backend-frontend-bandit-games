package be.kdg.banditgames.platform.adapter.out.ml;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;
import jakarta.persistence.*;

@Entity
@Table(name = "move_recommended_projections")
public class MoveRecommendedProjectionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SessionId sessionId;

    @Column(nullable = false)
    private int moveNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerType aiType;

    @Lob
    @Column(nullable = false)
    private String gameState;

    @Lob
    @Column(nullable = false)
    private String legalMoves;

    @Column(nullable = false)
    private String recommendedMove;

    @Column(nullable = false)
    private double confidence;

    protected MoveRecommendedProjectionJpaEntity() {
    }

    public MoveRecommendedProjectionJpaEntity(
            SessionId sessionId,
            int moveNumber,
            PlayerType aiType,
            String gameState,
            String legalMoves,
            String recommendedMove,
            double confidence
    ) {
        this.sessionId = sessionId;
        this.moveNumber = moveNumber;
        this.aiType = aiType;
        this.gameState = gameState;
        this.legalMoves = legalMoves;
        this.recommendedMove = recommendedMove;
        this.confidence = confidence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public PlayerType getAiType() {
        return aiType;
    }

    public void setAiType(PlayerType aiType) {
        this.aiType = aiType;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public String getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(String legalMoves) {
        this.legalMoves = legalMoves;
    }

    public String getRecommendedMove() {
        return recommendedMove;
    }

    public void setRecommendedMove(String recommendedMove) {
        this.recommendedMove = recommendedMove;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
