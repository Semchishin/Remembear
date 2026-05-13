package semchishin.rememberprocessingservice.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import semchishin.rememberprocessingservice.TestConstants;
import semchishin.rememberprocessingservice.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultUserRepositoryTest extends AbstractRepositoryTest {

    private static final String DEFAULT_NAME = "name";
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "pass";
    private static final String DEFAULT_ROLE = "USER";

    private static final String NAME_1 = "n1";
    private static final String LOGIN_1 = "l1";
    private static final String PASSWORD_1 = "p1";

    private static final String NAME_2 = "n2";
    private static final String LOGIN_2 = "l2";
    private static final String PASSWORD_2 = "p2";
    private static final String ROLE_2 = "ADMIN";

    private static final String ORIGINAL_NAME = "original";
    private static final String UPDATED_NAME = "updated";
    private static final String UPDATED_PASSWORD = "newpass";

    private DefaultUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DefaultUserRepository(template);
        template.update(TestConstants.DELETE_FROM_REMINDS);
        template.update(TestConstants.DELETE_FROM_USERS);
    }

    @Test
    void saveShouldInsertAndSetId() {
        User user = new User(null, DEFAULT_NAME, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ROLE);

        User result = repository.save(user);

        assertNotNull(result.getId());
        assertEquals(DEFAULT_NAME, result.getName());
        assertEquals(DEFAULT_LOGIN, result.getLogin());
        assertEquals(DEFAULT_PASSWORD, result.getPassword());
        assertEquals(DEFAULT_ROLE, result.getRole());
    }

    @Test
    void findByIdShouldReturnUserWhenFound() {
        Long id = insertUser(DEFAULT_NAME, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ROLE);

        Optional<User> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(DEFAULT_NAME, result.get().getName());
        assertEquals(DEFAULT_LOGIN, result.get().getLogin());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        Optional<User> result = repository.findById(TestConstants.NON_EXISTENT_ID);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByUsernameShouldReturnUser() {
        insertUser(DEFAULT_NAME, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ROLE);

        User result = repository.findByUsername(DEFAULT_LOGIN);

        assertEquals(DEFAULT_NAME, result.getName());
        assertEquals(DEFAULT_LOGIN, result.getLogin());
        assertEquals(DEFAULT_ROLE, result.getRole());
    }

    @Test
    void findAllShouldReturnList() {
        insertUser(NAME_1, LOGIN_1, PASSWORD_1, DEFAULT_ROLE);
        insertUser(NAME_2, LOGIN_2, PASSWORD_2, ROLE_2);

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
        Long id = insertUser(ORIGINAL_NAME, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ROLE);
        User user = repository.findById(id).orElseThrow();
        user.setName(UPDATED_NAME);
        user.setPassword(UPDATED_PASSWORD);
        user.setRole(ROLE_2);

        repository.update(user);

        User fetched = repository.findById(id).orElseThrow();
        assertEquals(UPDATED_NAME, fetched.getName());
        assertEquals(DEFAULT_LOGIN, fetched.getLogin());
        assertEquals(UPDATED_PASSWORD, fetched.getPassword());
        assertEquals(ROLE_2, fetched.getRole());
    }

    @Test
    void deleteByIdShouldRemoveUser() {
        Long id = insertUser(DEFAULT_NAME, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ROLE);

        repository.deleteById(id);

        assertTrue(repository.findById(id).isEmpty());
    }

    private Long insertUser(String name, String login, String password, String role) {
        return template.queryForObject(
                TestConstants.INSERT_USER_SQL,
                Long.class, name, login, password, role);
    }
}
