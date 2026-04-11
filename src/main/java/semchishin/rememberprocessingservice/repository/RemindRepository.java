package semchishin.rememberprocessingservice.repository;

import semchishin.rememberprocessingservice.model.Remind;

import java.util.List;
import java.util.Optional;

public interface RemindRepository {

    Remind add(Remind remind);

    Optional<Remind> findById(Long id);

    List<Remind> findAllByUserId(Long userId);

    void update(Remind remind);

    void deleteById(Long id);


}
