package be.kdg.banditgames.gameplay.adapter.out.game;

import be.kdg.banditgames.gameplay.domain.GameProjection;
import org.springframework.stereotype.Component;

@Component
public class GameProjectionMongoMapper {

    public static GameProjectionMongoEntity fromDomain(GameProjection gameProjection){
        return new GameProjectionMongoEntity(gameProjection.gameId(), gameProjection.name());
    }

    public static GameProjection toDomain(GameProjectionMongoEntity entity){
        return new GameProjection(entity.getName(), entity.getGameId());
    }
}
