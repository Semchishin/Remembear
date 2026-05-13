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
import semchishin.rememberprocessingservice.TestConstants;

import javax.sql.DataSource;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(TestConstants.DB_IMAGE)
            .withDatabaseName(TestConstants.DB_NAME)
            .withUsername(TestConstants.DB_USERNAME)
            .withPassword(TestConstants.DB_PASSWORD);

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
        config.setMaximumPoolSize(TestConstants.DB_POOL_SIZE);
        config.setConnectionInitSql(TestConstants.DB_SEARCH_PATH_SQL);
        return new HikariDataSource(config);
    }

    private void runFlywayMigrations(DataSource dataSource) {
        Flyway.configure()
                .dataSource(dataSource)
                .schemas(TestConstants.DB_NAME)
                .defaultSchema(TestConstants.DB_NAME)
                .locations(TestConstants.FLYWAY_MIGRATION_LOCATION)
                .load()
                .migrate();
    }
}
