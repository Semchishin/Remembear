package semchishin.rememberprocessingservice.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import semchishin.rememberprocessingservice.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultUserRepositoryTest extends AbstractRepositoryTest {

    private DefaultUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DefaultUserRepository(template);
        template.update("DELETE FROM reminds");
        template.update("DELETE FROM users");
    }

    @Test
    void saveShouldInsertAndSetId() {
        User user = new User(null, "name", "login", "pass", "USER");

        User result = repository.save(user);

        assertNotNull(result.getId());
        assertEquals("name", result.getName());
        assertEquals("login", result.getLogin());
        assertEquals("pass", result.getPassword());
        assertEquals("USER", result.getRole());
    }

    @Test
    void findByIdShouldReturnUserWhenFound() {
        Long id = insertUser("name", "login", "pass", "USER");

        Optional<User> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals("name", result.get().getName());
        assertEquals("login", result.get().getLogin());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        Optional<User> result = repository.findById(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByUsernameShouldReturnUser() {
        insertUser("name", "login", "pass", "USER");

        User result = repository.findByUsername("login");

        assertEquals("name", result.getName());
        assertEquals("login", result.getLogin());
        assertEquals("USER", result.getRole());
    }

    @Test
    void findAllShouldReturnList() {
        insertUser("n1", "l1", "p1", "USER");
        insertUser("n2", "l2", "p2", "ADMIN");

        List<User> result = repository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findAllShouldReturnEmptyList() {
        List<User> result = repository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void updateShouldModifyUser() {
        Long id = insertUser("original", "login", "pass", "USER");
        User user = repository.findById(id).orElseThrow();
        user.setName("updated");
        user.setPassword("newpass");
        user.setRole("ADMIN");

        repository.update(user);

        User fetched = repository.findById(id).orElseThrow();
        assertEquals("updated", fetched.getName());
        assertEquals("login", fetched.getLogin());
        assertEquals("newpass", fetched.getPassword());
        assertEquals("ADMIN", fetched.getRole());
    }

    @Test
    void deleteByIdShouldRemoveUser() {
        Long id = insertUser("name", "login", "pass", "USER");

        repository.deleteById(id);

        assertTrue(repository.findById(id).isEmpty());
    }

    private Long insertUser(String name, String login, String password, String role) {
        return template.queryForObject(
                "INSERT INTO users (name, login, password, role) VALUES (?, ?, ?, ?) RETURNING id",
                Long.class, name, login, password, role);
    }
}
