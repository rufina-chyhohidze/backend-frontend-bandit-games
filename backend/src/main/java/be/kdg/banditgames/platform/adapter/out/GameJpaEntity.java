//package be.kdg.banditgames.platform.adapter.out;
//
//import be.kdg.banditgames.platform.domain.GameStatus;
//import jakarta.persistence.*;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "games")
//public class GameJpaEntity {
//    @Id
//    @GeneratedValue
//    private UUID id;
//
//    private String name;
//    private String description;
//    private String rules;
//    private String pictureUrl;
//
//    @Enumerated(EnumType.STRING)
//    private GameStatus status;
//
//    private String urlGameSession;
//
//
//    public UUID getId() { return id; }
//    public void setId(UUID id) { this.id = id; }
//
//    public String getName() { return name; }
//
//    public String getDescription() { return description; }
//
//    public String getRules() { return rules; }
//
//    public String getPictureUrl() { return pictureUrl; }
//
//    public GameStatus getStatus() { return status; }
//
//    public String getUrlGameSession() { return urlGameSession; }
//}
//
