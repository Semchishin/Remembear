package semchishin.rememberprocessingservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semchishin.rememberprocessingservice.model.User;
import semchishin.rememberprocessingservice.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserDetailsService userDetailsService;

    @Test
    void loadUserByUsernameShouldDelegateToRepository() {
        User expected = new User(1L, "name", "login", "pass", "USER");
        when(userRepository.findByUsername("login")).thenReturn(expected);

        var result = userDetailsService.loadUserByUsername("login");

        assertSame(expected, result);
        verify(userRepository).findByUsername("login");
    }

    @Test
    void loadUserByUsernameShouldPropagateException() {
        when(userRepository.findByUsername("unknown"))
                .thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class,
                () -> userDetailsService.loadUserByUsername("unknown"));
    }
}
