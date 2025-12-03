    package be.kdg.banditgames.platform.adapter.in.response;
    
    import java.util.UUID;
    
    public record PlayerDtoWithName(
            UUID playerId,
            String username
    ) {
    }
