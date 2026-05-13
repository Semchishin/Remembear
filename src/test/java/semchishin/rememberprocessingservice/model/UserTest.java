package semchishin.rememberprocessingservice.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "name";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "pass";
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_PREFIX = "ROLE_";

    @Test
    void getAuthoritiesShouldReturnRoleWithPrefix() {
        User user = new User(USER_ID, USER_NAME, USER_LOGIN, USER_PASSWORD, ROLE_ADMIN);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority(ROLE_PREFIX + ROLE_ADMIN),
                authorities.iterator().next());
    }

    @Test
    void getAuthoritiesShouldWorkForUserRole() {
        User user = new User(USER_ID, USER_NAME, USER_LOGIN, USER_PASSWORD, ROLE_USER);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority(ROLE_PREFIX + ROLE_USER),
                authorities.iterator().next());
    }

    @Test
    void getUsernameShouldReturnLogin() {
        User user = new User(USER_ID, USER_NAME, USER_LOGIN, USER_PASSWORD, ROLE_USER);

        assertEquals(USER_LOGIN, user.getUsername());
    }

    @Test
    void getPasswordShouldReturnPassword() {
        User user = new User(USER_ID, USER_NAME, USER_LOGIN, USER_PASSWORD, ROLE_USER);

        assertEquals(USER_PASSWORD, user.getPassword());
    }
}
