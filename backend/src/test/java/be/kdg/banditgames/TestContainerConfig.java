package be.kdg.banditgames;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

@TestConfiguration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "testcontainers.enabled", havingValue = "true", matchIfMissing = true)
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

    @Bean
    @ServiceConnection
    static RabbitMQContainer rabbitMQContainer() {
        return new RabbitMQContainer("rabbitmq:3.13-alpine");
    }
}
