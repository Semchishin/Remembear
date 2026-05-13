package semchishin.rememberprocessingservice.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import semchishin.rememberprocessingservice.model.Remind;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRemindRepositoryTest extends AbstractRepositoryTest {

    private DefaultRemindRepository repository;

    private Long userId;

    @BeforeEach
    void setUp() {
        repository = new DefaultRemindRepository(template);
        template.update("DELETE FROM reminds");
        template.update("DELETE FROM users");
        userId = template.queryForObject(
                "INSERT INTO users (name, login, password, role) VALUES (?, ?, ?, ?) RETURNING id",
                Long.class, "test", "test", "pass", "USER");
    }

    @Test
    void addShouldInsertAndSetId() {
        LocalDateTime now = LocalDateTime.of(2026, 5, 13, 12, 0);
        Remind remind = new Remind();
        remind.setUserId(userId);
        remind.setTitle("title");
        remind.setDescription("desc");
        remind.setCreatedAt(now);
        remind.setRemindAt(now.plusHours(1));

        Remind result = repository.add(remind);

        assertNotNull(result.getRemindId());
        assertEquals(userId, result.getUserId());
        assertEquals("title", result.getTitle());
        assertEquals("desc", result.getDescription());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now.plusHours(1), result.getRemindAt());
    }

    @Test
    void findByIdShouldReturnRemindWhenFound() {
        LocalDateTime now = LocalDateTime.of(2026, 5, 13, 12, 0);
        Remind remind = new Remind();
        remind.setUserId(userId);
        remind.setTitle("title");
        remind.setDescription("desc");
        remind.setCreatedAt(now);
        remind.setRemindAt(now.plusHours(1));
        repository.add(remind);

        Optional<Remind> result = repository.findById(remind.getRemindId());

        assertTrue(result.isPresent());
        assertEquals(remind.getRemindId(), result.get().getRemindId());
        assertEquals("title", result.get().getTitle());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        Optional<Remind> result = repository.findById(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllByUserIdShouldReturnList() {
        LocalDateTime now = LocalDateTime.of(2026, 5, 13, 12, 0);
        Remind r1 = new Remind(null, userId, "t1", "d1", now, now.plusHours(1));
        Remind r2 = new Remind(null, userId, "t2", "d2", now, now.plusHours(2));
        repository.add(r1);
        repository.add(r2);

        List<Remind> result = repository.findAllByUserId(userId);

        assertEquals(2, result.size());
    }

    @Test
    void findAllByUserIdShouldReturnEmptyList() {
        List<Remind> result = repository.findAllByUserId(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void updateShouldModifyRemind() {
        LocalDateTime now = LocalDateTime.of(2026, 5, 13, 12, 0);
        Remind remind = new Remind(null, userId, "original", "original", now, now.plusHours(1));
        repository.add(remind);

        remind.setTitle("updated");
        remind.setDescription("updated desc");
        remind.setRemindAt(now.plusDays(1));
        repository.update(remind);

        Remind fetched = repository.findById(remind.getRemindId()).orElseThrow();
        assertEquals("updated", fetched.getTitle());
        assertEquals("updated desc", fetched.getDescription());
        assertEquals(now.plusDays(1), fetched.getRemindAt());
        assertEquals(now, fetched.getCreatedAt());
    }

    @Test
    void deleteByIdShouldRemoveRemind() {
        LocalDateTime now = LocalDateTime.of(2026, 5, 13, 12, 0);
        Remind remind = new Remind(null, userId, "title", "desc", now, now.plusHours(1));
        repository.add(remind);

        repository.deleteById(remind.getRemindId());

        assertTrue(repository.findById(remind.getRemindId()).isEmpty());
    }
}
