//package be.kdg.banditgames.platform.adapter.out.player;
//
//import be.kdg.banditgames.common.shared.PlayerId;
//import be.kdg.banditgames.platform.domain.Player;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Import({PlayerJpaAdapter.class})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class PlayerJpaAdapterTest {
//
//    @Autowired
//    private PlayerJpaRepository repo;
//
//    @Autowired
//    private PlayerJpaAdapter adapter;
//
//    @Test
//    void save_and_loadById() {
//        // given
//        UUID id = UUID.randomUUID();
//        PlayerJpaEntity entity = new PlayerJpaEntity(
//                id,
//                "rufina",
//                List.of(UUID.randomUUID()),
//                List.of()
//        );
//        repo.save(entity);
//
//        // when
//        var resultOpt = adapter.loadById(PlayerId.of(id));
//
//        // then
//        assertThat(resultOpt).isPresent();
//        Player player = resultOpt.get();
//        assertThat(player.getPlayerId().playerId()).isEqualTo(id);
//        assertThat(player.getUsername()).isEqualTo("rufina");
//        assertThat(player.getFavoriteGames()).hasSize(1);
//    }
//
//    @Test
//    void findByUsernameContainingIgnoreCase_mapsBackToDomain() {
//        // given
//        PlayerJpaEntity e1 = new PlayerJpaEntity(UUID.randomUUID(), "Alice", List.of(), List.of());
//        PlayerJpaEntity e2 = new PlayerJpaEntity(UUID.randomUUID(), "alicja", List.of(), List.of());
//        PlayerJpaEntity e3 = new PlayerJpaEntity(UUID.randomUUID(), "Bob", List.of(), List.of());
//        repo.saveAll(List.of(e1, e2, e3));
//
//        // when
//        List<Player> result = adapter.findByUsernameContainingIgnoreCase("ali");
//
//        // then
//        assertThat(result).extracting(Player::getUsername)
//                .containsExactlyInAnyOrder("Alice", "alicja");
//    }
//}
