package semchishin.rememberprocessingservice;

import java.time.LocalDateTime;

public final class TestConstants {

    // PostgreSQL container
    public static final String DB_IMAGE = "postgres:15";
    public static final String DB_NAME = "remembear";
    public static final String DB_USERNAME = "test";
    public static final String DB_PASSWORD = "test";
    public static final int DB_POOL_SIZE = 2;
    public static final String DB_SEARCH_PATH_SQL = "SET search_path TO remembear";
    public static final String FLYWAY_MIGRATION_LOCATION = "filesystem:flyway/migration";

    // Cleanup SQL
    public static final String DELETE_FROM_REMINDS = "DELETE FROM reminds";
    public static final String DELETE_FROM_USERS = "DELETE FROM users";

    // Insert SQL
    public static final String INSERT_USER_SQL =
            "INSERT INTO users (name, login, password, role) VALUES (?, ?, ?, ?) RETURNING id";

    // Common IDs
    public static final Long NON_EXISTENT_ID = 999L;

    // Base timestamp for remind tests
    public static final LocalDateTime BASE_TIMESTAMP = LocalDateTime.of(2026, 5, 13, 12, 0);

    private TestConstants() {}
}
