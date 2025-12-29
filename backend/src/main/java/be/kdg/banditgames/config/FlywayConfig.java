package be.kdg.banditgames.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("!test")
@ConditionalOnProperty(name = "app.flyway.manual", havingValue = "true")
public class FlywayConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DataSource dataSource;
    private boolean hasRun = false;

    public FlywayConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!hasRun) {
            hasRun = true;
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .baselineVersion("0")
                    .load();
            flyway.migrate();
        }
    }
}