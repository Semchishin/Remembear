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

    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "name";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "pass";
    private static final String USER_ROLE = "USER";
    private static final String UNKNOWN_LOGIN = "unknown";
    private static final String EXCEPTION_MESSAGE = "User not found";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserDetailsService userDetailsService;

    @Test
    void loadUserByUsernameShouldDelegateToRepository() {
        User expected = new User(USER_ID, USER_NAME, USER_LOGIN, USER_PASSWORD, USER_ROLE);
        when(userRepository.findByUsername(USER_LOGIN)).thenReturn(expected);

        var result = userDetailsService.loadUserByUsername(USER_LOGIN);

        assertSame(expected, result);
        verify(userRepository).findByUsername(USER_LOGIN);
    }

    @Test
    void loadUserByUsernameShouldPropagateException() {
        when(userRepository.findByUsername(UNKNOWN_LOGIN))
                .thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        assertThrows(RuntimeException.class,
                () -> userDetailsService.loadUserByUsername(UNKNOWN_LOGIN));
    }
}
