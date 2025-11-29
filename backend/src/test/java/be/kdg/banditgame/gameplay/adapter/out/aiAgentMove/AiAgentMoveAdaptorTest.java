// src/test/java/be/kdg/banditgames/gameplay/adapter/out/aiAgentMove/AiAgentMoveAdaptorTest.java
package be.kdg.banditgame.gameplay.adapter.out.aiAgentMove;

import be.kdg.banditgames.gameplay.adapter.out.aiAgentMove.AiAgentMoveAdaptor;
import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AiAgentMoveAdaptorTest {

    private final RestTemplate restTemplate =  new RestTemplate();
    private MockRestServiceServer mockServer;
    private AiAgentMoveAdaptor adaptor;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        adaptor = new AiAgentMoveAdaptor();
    }

    @Test
    void returnsFakeAiResponse() {
        mockServer.expect(requestTo("http://localhost:8081/ai/aiMove"))
                  .andRespond(withSuccess(
                      """
                      {"move":"drop(3)","confidenceScore":0.85,"bestMove":"drop(4)","heuristicScore":0.65,"visitCount":1200,"searchDepth":7}
                      """,
                      MediaType.APPLICATION_JSON));

        AiMoveMetadata aiMove = adaptor.getAiAgentMove(new AiRequestCommand("board", "legal"));

        assertEquals("drop(3)", aiMove.recommendedMove());
        assertEquals("drop(4)", aiMove.bestMove());
        assertEquals(0.85, aiMove.confidenceScore(), 1e-6);
    }
}
