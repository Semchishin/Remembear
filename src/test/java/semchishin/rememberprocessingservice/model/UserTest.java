package semchishin.rememberprocessingservice.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getAuthoritiesShouldReturnRoleWithPrefix() {
        User user = new User(1L, "name", "login", "pass", "ADMIN");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority("ROLE_ADMIN"),
                authorities.iterator().next());
    }

    @Test
    void getAuthoritiesShouldWorkForUserRole() {
        User user = new User(1L, "name", "login", "pass", "USER");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority("ROLE_USER"),
                authorities.iterator().next());
    }

    @Test
    void getUsernameShouldReturnLogin() {
        User user = new User(1L, "name", "login", "pass", "USER");

        assertEquals("login", user.getUsername());
    }

    @Test
    void getPasswordShouldReturnPassword() {
        User user = new User(1L, "name", "login", "pass", "USER");

        assertEquals("pass", user.getPassword());
    }
}
