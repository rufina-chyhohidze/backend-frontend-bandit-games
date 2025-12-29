package be.kdg.banditgames.platform.adapter.in.friendship;
import be.kdg.banditgames.BackendApplication;
import be.kdg.banditgames.TestContainerConfig;
import be.kdg.banditgames.platform.adapter.out.friendship.FriendshipJpaEntity;
import be.kdg.banditgames.platform.adapter.out.friendship.FriendshipJpaRepository;
import be.kdg.banditgames.platform.domain.FriendshipStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = BackendApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
class FriendshipControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FriendshipJpaRepository friendshipJpaRepository;

    @Test
    @WithMockUser(authorities = "player")
    void sendFriendRequest_createsPendingFriendshipInDatabase() throws Exception {
        // given
        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();

        String body = """
                {
                  "fromPlayerId": "%s",
                  "toPlayerId": "%s"
                }
                """.formatted(fromId, toId);

        mockMvc.perform(
                        post("/api/friendships/request")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk());

        List<FriendshipJpaEntity> all = friendshipJpaRepository.findAll();
        assertThat(all).hasSize(1);

        FriendshipJpaEntity entity = all.get(0);
        assertThat(entity.getStatus()).isEqualTo(FriendshipStatus.PENDING);

        assertThat(List.of(entity.getPlayerAId(), entity.getPlayerBId()))
                .containsExactlyInAnyOrder(fromId, toId);

        assertThat(entity.getInitiatorId()).isEqualTo(fromId);
    }
}
