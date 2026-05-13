package semchishin.rememberprocessingservice.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import semchishin.rememberprocessingservice.TestConstants;
import semchishin.rememberprocessingservice.model.Remind;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRemindRepositoryTest extends AbstractRepositoryTest {

    private static final LocalDateTime NOW = TestConstants.BASE_TIMESTAMP;
    private static final LocalDateTime PLUS_1H = NOW.plusHours(1);
    private static final LocalDateTime PLUS_2H = NOW.plusHours(2);
    private static final LocalDateTime PLUS_1D = NOW.plusDays(1);

    private static final String DEFAULT_TITLE = "title";
    private static final String DEFAULT_DESC = "desc";
    private static final String TITLE_1 = "t1";
    private static final String DESC_1 = "d1";
    private static final String TITLE_2 = "t2";
    private static final String DESC_2 = "d2";
    private static final String ORIGINAL_TITLE = "original";
    private static final String UPDATED_TITLE = "updated";
    private static final String UPDATED_DESC = "updated desc";

    private static final String USER_NAME = "test";
    private static final String USER_PASSWORD = "pass";
    private static final String USER_ROLE = "USER";

    private DefaultRemindRepository repository;
    private Long userId;

    @BeforeEach
    void setUp() {
        repository = new DefaultRemindRepository(template);
        template.update(TestConstants.DELETE_FROM_REMINDS);
        template.update(TestConstants.DELETE_FROM_USERS);
        userId = template.queryForObject(
                TestConstants.INSERT_USER_SQL,
                Long.class, USER_NAME, USER_NAME, USER_PASSWORD, USER_ROLE);
    }

    @Test
    void addShouldInsertAndSetId() {
        Remind remind = new Remind();
        remind.setUserId(userId);
        remind.setTitle(DEFAULT_TITLE);
        remind.setDescription(DEFAULT_DESC);
        remind.setCreatedAt(NOW);
        remind.setRemindAt(PLUS_1H);

        Remind result = repository.add(remind);

        assertNotNull(result.getRemindId());
        assertEquals(userId, result.getUserId());
        assertEquals(DEFAULT_TITLE, result.getTitle());
        assertEquals(DEFAULT_DESC, result.getDescription());
        assertEquals(NOW, result.getCreatedAt());
        assertEquals(PLUS_1H, result.getRemindAt());
    }

    @Test
    void findByIdShouldReturnRemindWhenFound() {
        Remind remind = new Remind();
        remind.setUserId(userId);
        remind.setTitle(DEFAULT_TITLE);
        remind.setDescription(DEFAULT_DESC);
        remind.setCreatedAt(NOW);
        remind.setRemindAt(PLUS_1H);
        repository.add(remind);

        Optional<Remind> result = repository.findById(remind.getRemindId());

        assertTrue(result.isPresent());
        assertEquals(remind.getRemindId(), result.get().getRemindId());
        assertEquals(DEFAULT_TITLE, result.get().getTitle());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        Optional<Remind> result = repository.findById(TestConstants.NON_EXISTENT_ID);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllByUserIdShouldReturnList() {
        Remind r1 = new Remind(null, userId, TITLE_1, DESC_1, NOW, PLUS_1H);
        Remind r2 = new Remind(null, userId, TITLE_2, DESC_2, NOW, PLUS_2H);
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
        Remind remind = new Remind(null, userId, ORIGINAL_TITLE, ORIGINAL_TITLE, NOW, PLUS_1H);
        repository.add(remind);

        remind.setTitle(UPDATED_TITLE);
        remind.setDescription(UPDATED_DESC);
        remind.setRemindAt(PLUS_1D);
        repository.update(remind);

        Remind fetched = repository.findById(remind.getRemindId()).orElseThrow();
        assertEquals(UPDATED_TITLE, fetched.getTitle());
        assertEquals(UPDATED_DESC, fetched.getDescription());
        assertEquals(PLUS_1D, fetched.getRemindAt());
        assertEquals(NOW, fetched.getCreatedAt());
    }

    @Test
    void deleteByIdShouldRemoveRemind() {
        Remind remind = new Remind(null, userId, DEFAULT_TITLE, DEFAULT_DESC, NOW, PLUS_1H);
        repository.add(remind);

        repository.deleteById(remind.getRemindId());

        assertTrue(repository.findById(remind.getRemindId()).isEmpty());
    }
}
