package semchishin.rememberprocessingservice.repository.impl;

import semchishin.rememberprocessingservice.model.Remind;
import semchishin.rememberprocessingservice.repository.RemindRepository;

import java.util.List;

public class DefaultRemindRepository implements RemindRepository {

    @Override
    public Remind add() {
        return null;
    }

    @Override
    public Remind findById(Long id) {
        return null;
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
