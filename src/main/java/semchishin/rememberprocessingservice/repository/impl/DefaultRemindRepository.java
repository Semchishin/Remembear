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

    private final JdbcTemplate template;

    private static final String FIND_BY_ID = "SELECT * FROM reminds where remindid = ?";

    private static final RowMapper<Remind> ROW_MAPPER = (rs, rowNum) -> new Remind(
            rs.getLong("remind_id"),
            rs.getLong("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("remind_at").toLocalDateTime()
    );

    @Override
    public Remind add(Remind remind) {
        return null;
    }

    @Override
    public Optional<Remind> findById(Long id) {
        Remind remind = template.queryForObject(FIND_BY_ID, Remind.class, id);
        return Optional.ofNullable(remind);
    }

    @Override
    public List<Remind> findAllByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Remind updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
