package semchishin.flyway;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import semchishin.rememberprocessingservice.RememberProcessingServiceApplication;

@SpringBootTest(classes = RememberProcessingServiceApplication.class)
@TestPropertySource(properties = "spring.flyway.clean-disabled=false")
@Testcontainers
@Slf4j
public class FlywayTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("remembear")
            .withUsername("remembear")
            .withPassword("remembear");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private Flyway flyway;

    private static final String FLYWAY_CLEAN = "Database was cleaned";
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
