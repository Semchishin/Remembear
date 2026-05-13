package semchishin.rememberprocessingservice.repository.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("remembear")
            .withUsername("test")
            .withPassword("test");

    protected static JdbcTemplate template;

    @BeforeAll
    void initDatabase() {
        DataSource dataSource = createDataSource();
        template = new JdbcTemplate(dataSource);
        runFlywayMigrations(dataSource);
    }

    private DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgres.getJdbcUrl());
        config.setUsername(postgres.getUsername());
        config.setPassword(postgres.getPassword());
        config.setMaximumPoolSize(2);
        config.setConnectionInitSql("SET search_path TO remembear");
        return new HikariDataSource(config);
    }

    private void runFlywayMigrations(DataSource dataSource) {
        Flyway.configure()
                .dataSource(dataSource)
                .schemas("remembear")
                .defaultSchema("remembear")
                .locations("filesystem:flyway/migration")
                .load()
                .migrate();
    }
}
