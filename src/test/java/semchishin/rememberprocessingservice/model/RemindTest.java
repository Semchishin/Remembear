package semchishin.rememberprocessingservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RemindTest {

    private static final String TITLE = "title";
    private static final String DESC = "desc";
    private static final String SHORT_TITLE = "t";
    private static final String SHORT_DESC = "d";
    private static final LocalDateTime REMIND_AT = LocalDateTime.of(2026, 6, 1, 10, 0);

    @Test
    void createWithRemindTimeShouldSetAllFields() {
        Remind remind = Remind.createWithRemindTime(TITLE, DESC, REMIND_AT);

        assertNull(remind.getRemindId());
        assertNull(remind.getUserId());
        assertEquals(TITLE, remind.getTitle());
        assertEquals(DESC, remind.getDescription());
        assertNotNull(remind.getCreatedAt());
        assertEquals(REMIND_AT, remind.getRemindAt());
    }

    @Test
    void createWithoutRemindTimeShouldNotSetRemindAt() {
        Remind remind = Remind.createWithoutRemindTime(TITLE, DESC);

        assertNull(remind.getRemindId());
        assertNull(remind.getUserId());
        assertEquals(TITLE, remind.getTitle());
        assertEquals(DESC, remind.getDescription());
        assertNotNull(remind.getCreatedAt());
        assertNull(remind.getRemindAt());
    }

    @Test
    void createWithRemindTimeShouldSetCreatedAtToNow() {
        Remind remind = Remind.createWithRemindTime(SHORT_TITLE, SHORT_DESC, LocalDateTime.now().plusDays(1));
        assertNotNull(remind.getCreatedAt());
    }

    @Test
    void createWithoutRemindTimeShouldSetCreatedAtToNow() {
        Remind remind = Remind.createWithoutRemindTime(SHORT_TITLE, SHORT_DESC);
        assertNotNull(remind.getCreatedAt());
    }
}
