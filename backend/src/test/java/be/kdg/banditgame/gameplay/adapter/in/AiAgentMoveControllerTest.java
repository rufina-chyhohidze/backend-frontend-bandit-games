// src/test/java/be/kdg/banditgames/gameplay/adapter/in/AiAgentMoveControllerTest.java
package be.kdg.banditgame.gameplay.adapter.in;

import be.kdg.banditgames.gameplay.adapter.in.AiAgentMoveController;
import be.kdg.banditgames.gameplay.adapter.in.request.AiRequest;
import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AiAgentMoveController.class)
@ActiveProfiles("test")
class AiAgentMoveControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;

    @MockBean private AiAgentMoveUseCase useCase;

    @Test
    void returnsAiMoveDto() throws Exception {
        when(useCase.handleMove(new be.kdg.banditgames.gameplay.port.in.AiRequestCommand("b","l")))
            .thenReturn(new AiMove("drop(3)", 0.85, "drop(4)", 0.65, 1200, 7));

        AiRequest req = new AiRequest("b","l");

        mvc.perform(get("/api/gameplay/ai-move")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(req)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.move").value("drop(3)"))
           .andExpect(jsonPath("$.bestMove").value("drop(4)"))
           .andExpect(jsonPath("$.confidenceScore").value(0.85));
    }
}
