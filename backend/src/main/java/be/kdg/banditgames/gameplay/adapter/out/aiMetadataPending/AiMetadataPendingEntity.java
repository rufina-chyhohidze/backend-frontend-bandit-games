package be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending;

import be.kdg.banditgames.gameplay.adapter.out.AiMetadataEmbedded;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.UUID;

@Document("ai_metadata_pending")
@CompoundIndex(name = "session_move_idx", def = "{'session_id': 1, 'move_number': 1}", unique = true)
public class AiMetadataPendingEntity {
    @Id
    private String id;

    @Field("session_id")
    private UUID sessionId;
    
    @Field("move_number")
    private int moveNumber;
    
    @Field("metadata")
    private AiMetadataEmbedded metadata;

    @Indexed(expireAfter = "PT5M") // ISO-8601 duration → 5 minutes
    @Field("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public AiMetadataPendingEntity() {}

    public AiMetadataPendingEntity(UUID sessionId, int moveNumber, AiMetadataEmbedded metadata, LocalDateTime createdAt) {
        this.sessionId = sessionId;
        this.moveNumber = moveNumber;
        this.metadata = metadata;
        this.createdAt = createdAt;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public AiMetadataEmbedded getMetadata() {
        return metadata;
    }

    public void setMetadata(AiMetadataEmbedded metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}