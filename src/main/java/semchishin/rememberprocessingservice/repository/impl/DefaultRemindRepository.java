package semchishin.rememberprocessingservice.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import semchishin.rememberprocessingservice.model.Remind;
import semchishin.rememberprocessingservice.model.User;
import semchishin.rememberprocessingservice.repository.RemindRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultRemindRepository implements RemindRepository {

    public static final String DELETE_BY_ID = "DELETE FROM reminds WHERE remind_id = ?";

    public static final String INSERT = "INSERT INTO reminds" +
            " (user_id, title, description, created_at, remind_at) VALUES (?, ?, ?, ?, ?)" +
            " RETURNING remind_id";

    public static final String SELECT_ALL = "SELECT * FROM reminds WHERE user_id = ?";

    public static final String UPDATE_BY_ID = "UPDATE reminds SET user_id = ?, title = ?, description = ?, remind_at = ?" +
            " WHERE remind_id = ?";

    private static final String FIND_BY_ID = "SELECT * FROM reminds where remind_id = ?";

    private static final RowMapper<Remind> ROW_MAPPER = (rs, rowNum) -> new Remind(
            rs.getLong("remind_id"),
            rs.getLong("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("remind_at").toLocalDateTime()
    );

    private final JdbcTemplate template;

    @Override
    public Remind add(Remind remind) {
        Long id = template.queryForObject(INSERT, Long.class, remind.getUserId(), remind.getTitle(),
                remind.getDescription(), remind.getCreatedAt(), remind.getRemindAt());
        remind.setRemindId(id);
        return remind;
    }

    @Override
    public Optional<Remind> findById(Long id) {
        Remind remind = template.queryForObject(FIND_BY_ID, Remind.class, id);
        return Optional.ofNullable(remind);
    }

    @Override
    public List<Remind> findAllByUserId(Long userId) {
        return template.query(SELECT_ALL, ROW_MAPPER, userId);
    }

    @Override
    public void update(Remind remind) {
        template.update(UPDATE_BY_ID, remind.getUserId(), remind.getTitle(),
                remind.getDescription(), remind.getCreatedAt(), remind.getRemindAt(),
                remind.getRemindId());
    }

    @Override
    public void deleteById(Long id) {
        template.update(DELETE_BY_ID, id);
    }
}
