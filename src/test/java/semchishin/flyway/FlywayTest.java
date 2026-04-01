package semchishin.flyway;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import semchishin.rememberprocessingservice.RememberProcessingServiceApplication;

@SpringBootTest(classes = RememberProcessingServiceApplication.class)
@TestPropertySource(properties = "spring.flyway.clean-disabled=false")
@Slf4j
public class FlywayTest {

    @Autowired
    private Flyway flyway;

    private static final String  FLYWAY_CLEAN = "Database was cleaned";

    private static final String FLYWAY_MIGRATE = "Database migrations was applied";

    @Test
    void flywayClean() {
        flyway.clean();
        log.info(FLYWAY_CLEAN);
    }

    @Test
    void flywayMigrate() {
        flyway.migrate();
        log.info(FLYWAY_MIGRATE);
    }
}
