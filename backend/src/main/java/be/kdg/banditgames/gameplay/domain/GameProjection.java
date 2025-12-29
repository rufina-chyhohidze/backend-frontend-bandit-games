package be.kdg.banditgames.gameplay.domain;

import java.util.UUID;

public record GameProjection (
        String name,
        UUID gameId
){
}
