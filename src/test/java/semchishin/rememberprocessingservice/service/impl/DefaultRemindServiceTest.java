package semchishin.rememberprocessingservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semchishin.rememberprocessingservice.TestConstants;
import semchishin.rememberprocessingservice.exception.RemindNotFoundException;
import semchishin.rememberprocessingservice.model.Remind;
import semchishin.rememberprocessingservice.repository.RemindRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRemindServiceTest {

    private static final LocalDateTime NOW = TestConstants.BASE_TIMESTAMP;
    private static final LocalDateTime PLUS_1H = NOW.plusHours(1);
    private static final LocalDateTime PLUS_2H = NOW.plusHours(2);

    private static final Long USER_ID = 1L;
    private static final Long REMIND_ID = 1L;
    private static final Long SAVED_REMIND_ID = 42L;
    private static final Long MISSING_REMIND_ID = 99L;

    private static final String TITLE = "title";
    private static final String DESC = "desc";
    private static final String TITLE_1 = "t1";
    private static final String DESC_1 = "d1";
    private static final String TITLE_2 = "t2";
    private static final String DESC_2 = "d2";

    @Mock
    private RemindRepository remindRepository;

    @InjectMocks
    private DefaultRemindService remindService;

    @Test
    void createReminderShouldDelegateToRepository() {
        Remind remind = new Remind(null, USER_ID, TITLE, DESC, NOW, PLUS_1H);
        Remind saved = new Remind(SAVED_REMIND_ID, USER_ID, TITLE, DESC, NOW, PLUS_1H);
        when(remindRepository.add(remind)).thenReturn(saved);

        Remind result = remindService.createReminder(remind);

        assertSame(saved, result);
        verify(remindRepository).add(remind);
    }

    @Test
    void findRemindByIdShouldReturnRemindWhenFound() {
        Remind remind = new Remind(REMIND_ID, USER_ID, TITLE, DESC, NOW, PLUS_1H);
        when(remindRepository.findById(REMIND_ID)).thenReturn(Optional.of(remind));

        Remind result = remindService.findRemindById(REMIND_ID);

        assertSame(remind, result);
    }

    @Test
    void findRemindByIdShouldThrowWhenNotFound() {
        when(remindRepository.findById(MISSING_REMIND_ID)).thenReturn(Optional.empty());

        assertThrows(RemindNotFoundException.class, () -> remindService.findRemindById(MISSING_REMIND_ID));
    }

    @Test
    void getAllRemindsByUserIdShouldReturnList() {
        Remind r1 = new Remind(REMIND_ID, USER_ID, TITLE_1, DESC_1, NOW, PLUS_1H);
        Remind r2 = new Remind(2L, USER_ID, TITLE_2, DESC_2, NOW, PLUS_2H);
        when(remindRepository.findAllByUserId(USER_ID)).thenReturn(List.of(r1, r2));

        List<Remind> result = remindService.getAllRemindsByUserId(USER_ID);

        assertEquals(2, result.size());
        verify(remindRepository).findAllByUserId(USER_ID);
    }

    @Test
    void updateRemindShouldDelegateToRepository() {
        Remind remind = new Remind(REMIND_ID, USER_ID, TITLE, DESC, NOW, PLUS_1H);

        remindService.UpdateRemind(remind);

        verify(remindRepository).update(remind);
    }

    @Test
    void deleteByIdShouldDelegateToRepository() {
        remindService.deleteById(REMIND_ID);

        verify(remindRepository).deleteById(REMIND_ID);
    }
}
