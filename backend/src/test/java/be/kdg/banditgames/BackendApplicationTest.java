package be.kdg.banditgames;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainerConfig.class)
class BackendApplicationTest {
    
    @Test
    void contextLoads() {
        // This test ensures that the Spring application context loads successfully.
    }

}