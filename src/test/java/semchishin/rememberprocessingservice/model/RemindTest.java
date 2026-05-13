package semchishin.rememberprocessingservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RemindTest {

    @Test
    void createWithRemindTimeShouldSetAllFields() {
        LocalDateTime remindAt = LocalDateTime.of(2026, 6, 1, 10, 0);

        Remind remind = Remind.createWithRemindTime("title", "desc", remindAt);

        assertNull(remind.getRemindId());
        assertNull(remind.getUserId());
        assertEquals("title", remind.getTitle());
        assertEquals("desc", remind.getDescription());
        assertNotNull(remind.getCreatedAt());
        assertEquals(remindAt, remind.getRemindAt());
    }

    @Test
    void createWithoutRemindTimeShouldNotSetRemindAt() {
        Remind remind = Remind.createWithoutRemindTime("title", "desc");

        assertNull(remind.getRemindId());
        assertNull(remind.getUserId());
        assertEquals("title", remind.getTitle());
        assertEquals("desc", remind.getDescription());
        assertNotNull(remind.getCreatedAt());
        assertNull(remind.getRemindAt());
    }

    @Test
    void createWithRemindTimeShouldSetCreatedAtToNow() {
        Remind remind = Remind.createWithRemindTime("t", "d", LocalDateTime.now().plusDays(1));
        assertNotNull(remind.getCreatedAt());
    }

    @Test
    void createWithoutRemindTimeShouldSetCreatedAtToNow() {
        Remind remind = Remind.createWithoutRemindTime("t", "d");
        assertNotNull(remind.getCreatedAt());
    }
}
