package be.kdg.banditgames.gameplay.adapter.out.game;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document("games")
public class GameProjectionMongoEntity {
    @Id
    private UUID gameId;

    @Field("name")
    private String name;

    public GameProjectionMongoEntity(UUID gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }
}
