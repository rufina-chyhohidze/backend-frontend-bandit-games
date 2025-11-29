package be.kdg.banditgames;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb"
})
class BackendApplicationTest {
    
    @Test
    void contextLoads() {
        // This test ensures that the Spring application context loads successfully.
    }

}