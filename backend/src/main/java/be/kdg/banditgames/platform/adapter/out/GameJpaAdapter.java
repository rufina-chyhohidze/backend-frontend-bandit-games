//package be.kdg.banditgames.platform.adapter.out;
//
//import be.kdg.banditgames.platform.domain.Game;
//import be.kdg.banditgames.platform.domain.GameStatus;
//import be.kdg.banditgames.platform.port.out.LoadPlayableGamesPort;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
////@Repository
//public class GameJpaAdapter implements LoadPlayableGamesPort {
//    private final GameJpaRepository jpa;
//
//    public GameJpaAdapter(GameJpaRepository jpa) {
//        this.jpa = jpa;
//    }
//
//    @Override
//    public List<Game> loadPlayableGames() {
//        return jpa.findByStatus(GameStatus.PUBLISHED).stream()
//                .map(this::toDomain)
//                .toList();
//    }
//
//    private Game toDomain(GameJpaEntity e) {
//        return new Game(
//                e.getId(),
//                e.getName(),
//                e.getDescription(),
//                e.getRules(),
//                e.getPictureUrl(),
//                e.getStatus(),
//                e.getUrlGameSession()
//        );
//    }
//
//}
