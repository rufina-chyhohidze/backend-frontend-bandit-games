package be.kdg.banditgames.platform.adapter.out.ml;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "win_probability_projections")
public class WinProbabilityProjectionJpaEntity {

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
    private double player1WinProbability;

    @Column(nullable = false)
    private double player2WinProbability;

    @Column(nullable = false)
    private double activePlayerWinProbability;

    @ElementCollection
    @CollectionTable(
            name = "win_probability_distributions",
            joinColumns = @JoinColumn(name = "projection_id")
    )
    @Column(name = "value", nullable = false)
    private List<Double> distribution;

    protected WinProbabilityProjectionJpaEntity() {}

    public WinProbabilityProjectionJpaEntity(
            SessionId sessionId,
            int moveNumber,
            PlayerType aiType,
            String gameState,
            String legalMoves,
            double player1WinProbability,
            double player2WinProbability,
            double activePlayerWinProbability,
            List<Double> distribution
    ) {
        this.sessionId = sessionId;
        this.moveNumber = moveNumber;
        this.aiType = aiType;
        this.gameState = gameState;
        this.legalMoves = legalMoves;
        this.player1WinProbability = player1WinProbability;
        this.player2WinProbability = player2WinProbability;
        this.activePlayerWinProbability = activePlayerWinProbability;
        this.distribution = distribution;
    }

    public List<Double> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<Double> distribution) {
        this.distribution = distribution;
    }

    public double getActivePlayerWinProbability() {
        return activePlayerWinProbability;
    }

    public void setActivePlayerWinProbability(double activePlayerWinProbability) {
        this.activePlayerWinProbability = activePlayerWinProbability;
    }

    public double getPlayer2WinProbability() {
        return player2WinProbability;
    }

    public void setPlayer2WinProbability(double player2WinProbability) {
        this.player2WinProbability = player2WinProbability;
    }

    public double getPlayer1WinProbability() {
        return player1WinProbability;
    }

    public void setPlayer1WinProbability(double player1WinProbability) {
        this.player1WinProbability = player1WinProbability;
    }

    public String getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(String legalMoves) {
        this.legalMoves = legalMoves;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public PlayerType getAiType() {
        return aiType;
    }

    public void setAiType(PlayerType aiType) {
        this.aiType = aiType;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
