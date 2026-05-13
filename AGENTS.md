# RememberProcessingService — Agent Guide

## Stack
- **Spring Boot 4.0.4** (Java 21), Gradle 9.4, single module
- **PostgreSQL 15** via HikariCP + Flyway (filesystem migrations)
- **Kafka**, **RabbitMQ (AMQP)**, **Redis** as messaging/state infra
- **Security**: JWT (jjwt 0.11.5/0.12.6), BCrypt, stateless sessions, role-based (`ADMIN`, `USER`)
- **Lombok** on all model/service classes

## Local Dev
Infra dependencies (Postgres 15, Kafka, SonarQube) via Docker Compose:
```
docker compose -f localDeployment/docker-compose.yaml up -d
```

## Key Commands
```sh
./gradlew test                  # run all tests (excludes semchishin.flyway.*)
./gradlew flywayClean           # wipe DB schema via Flyway
./gradlew flywayMigrate         # apply pending migrations
./gradlew build                 # full build + tests
```

## Test Quirks
- `semchishin.flyway.*` is **excluded** from `./gradlew test` — must run explicitly
- Flyway migrations location: `filesystem:flyway/migration` (not classpath)
- DB schema: `remembear`

## Architecture
- **No controllers** — message-driven processing service (Kafka/RabbitMQ consumers expected)
- Package: `semchishin.rememberprocessingservice`
- `RemindPusherService` is an empty interface stub
- Repositories use `JdbcTemplate` directly (no Spring Data JPA)

## Known Issues
- `V3__create_table_remember.sql` uses `bigint primary key` without auto-increment, but `DefaultRemindRepository` expects `RETURNING remind_id`
