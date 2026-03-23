package semchishin.rememberprocessingservice.repository;

import semchishin.rememberprocessingservice.model.User;

public interface UserRepository {

    User add(User user);

    User findById(Long id);

    User updateById(User user);

    void deleteById(Long id);
}
