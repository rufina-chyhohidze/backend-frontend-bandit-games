package be.kdg.banditgames;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfig {

    @Bean
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("test")
                .withUsername("bandit")
                .withPassword("bandit");
    }

    @Bean
    @ServiceConnection
    static MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer("mongo:7.0");
    }
}
