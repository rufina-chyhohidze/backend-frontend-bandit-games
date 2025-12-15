package be.kdg.banditgames.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DatabaseMigrationsRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationsRunner.class);

    private final JdbcTemplate jdbcTemplate;

    public DatabaseMigrationsRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            logger.info("Checking event_publication.serialized_event column type...");

            String sql = "SELECT data_type, character_maximum_length FROM information_schema.columns WHERE table_name = 'event_publication' AND column_name = 'serialized_event'";
            Map<String, Object> row = null;
            try {
                row = jdbcTemplate.queryForMap(sql);
            } catch (Exception e) {
                logger.info("Table or column not present or cannot be read: {}", e.getMessage());
            }

            if (row == null || row.isEmpty()) {
                logger.info("No event_publication.serialized_event column found; skipping migration.");
                return;
            }

            String dataType = (String) row.get("data_type");
            Object charMaxObj = row.get("character_maximum_length");
            Integer charMax = null;
            if (charMaxObj instanceof Number) {
                charMax = ((Number) charMaxObj).intValue();
            }

            logger.info("Found column data_type={} character_maximum_length={}", dataType, charMax);

            if ("character varying".equalsIgnoreCase(dataType) && charMax != null && charMax <= 255) {
                logger.info("Altering event_publication.serialized_event to type text...");
                jdbcTemplate.execute("ALTER TABLE event_publication ALTER COLUMN serialized_event TYPE text");
                logger.info("Column altered to text successfully.");
            } else {
                logger.info("No migration needed for event_publication.serialized_event.");
            }
        } catch (Exception ex) {
            logger.error("Error while running database migration runner: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}

