package be.kdg.banditgames.platform.adapter.out.game;

import be.kdg.banditgames.platform.domain.GameStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "games")
public class GameJpaEntity {
    @Id
    private UUID id;

    private String name;
    private String description;
    private String rules;
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private String urlGameSession;

    public GameJpaEntity(UUID id, String name, String description, String rules, String pictureUrl, GameStatus status, String urlGameSession) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.pictureUrl = pictureUrl;
        this.status = status;
        this.urlGameSession = urlGameSession;
    }

    public GameJpaEntity() {

    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getRules() { return rules; }

    public String getPictureUrl() { return pictureUrl; }

    public GameStatus getStatus() { return status; }

    public String getUrlGameSession() { return urlGameSession; }
}

