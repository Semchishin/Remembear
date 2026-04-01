package semchishin.rememberprocessingservice.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import semchishin.rememberprocessingservice.model.User;
import semchishin.rememberprocessingservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultUserRepository implements UserRepository {

    public static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
    public static final String INSERT = "INSERT INTO users (name, login, password, role) VALUES (?, ?, ?, ?) RETURNING id";
    public static final String SELECT_ALL = "SELECT * FROM users";
    public static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String SELECT_BY_USERNAME = "SELECT * FROM users WHERE login = ?";
    public static final String UPDATE_BY_ID = "UPDATE users SET name = ?, login = ?, password = ?, role = ?" +
            " WHERE id = ?";
    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("login"),
            rs.getString("password"),
            rs.getString("role")
    );
    private final JdbcTemplate template;

    @Override
    public User findByUsername(String username) {
        return template.queryForObject(SELECT_BY_USERNAME, ROW_MAPPER, username);
    }

    @Override
    public User save(User user) {
        Long id = template.queryForObject(INSERT, Long.class, user.getName(), user.getLogin(), user.getPassword(),
                user.getRole());
        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = template.queryForObject(SELECT_BY_ID, ROW_MAPPER, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        return template.query(SELECT_ALL, ROW_MAPPER);
    }

    @Override
    public void update(User user) {
        template.update(UPDATE_BY_ID,
                user.getName(),
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                user.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        template.update(DELETE_BY_ID, id);
    }
}
