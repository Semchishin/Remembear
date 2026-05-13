package semchishin.rememberprocessingservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Mock
    private RemindRepository remindRepository;

    @InjectMocks
    private DefaultRemindService remindService;

    private final LocalDateTime now = LocalDateTime.of(2026, 5, 13, 12, 0);

    @Test
    void createReminderShouldDelegateToRepository() {
        Remind remind = new Remind(null, 1L, "title", "desc", now, now.plusHours(1));
        Remind saved = new Remind(42L, 1L, "title", "desc", now, now.plusHours(1));
        when(remindRepository.add(remind)).thenReturn(saved);

        Remind result = remindService.createReminder(remind);

        assertSame(saved, result);
        verify(remindRepository).add(remind);
    }

    @Test
    void findRemindByIdShouldReturnRemindWhenFound() {
        Remind remind = new Remind(1L, 1L, "title", "desc", now, now.plusHours(1));
        when(remindRepository.findById(1L)).thenReturn(Optional.of(remind));

        Remind result = remindService.findRemindById(1L);

        assertSame(remind, result);
    }

    @Test
    void findRemindByIdShouldThrowWhenNotFound() {
        when(remindRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RemindNotFoundException.class, () -> remindService.findRemindById(99L));
    }

    @Test
    void getAllRemindsByUserIdShouldReturnList() {
        Remind r1 = new Remind(1L, 1L, "t1", "d1", now, now.plusHours(1));
        Remind r2 = new Remind(2L, 1L, "t2", "d2", now, now.plusHours(2));
        when(remindRepository.findAllByUserId(1L)).thenReturn(List.of(r1, r2));

        List<Remind> result = remindService.getAllRemindsByUserId(1L);

        assertEquals(2, result.size());
        verify(remindRepository).findAllByUserId(1L);
    }

    @Test
    void updateRemindShouldDelegateToRepository() {
        Remind remind = new Remind(1L, 1L, "title", "desc", now, now.plusHours(1));

        remindService.UpdateRemind(remind);

        verify(remindRepository).update(remind);
    }

    @Test
    void deleteByIdShouldDelegateToRepository() {
        remindService.deleteById(1L);

        verify(remindRepository).deleteById(1L);
    }
}
