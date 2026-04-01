package semchishin.rememberprocessingservice.repository;

import semchishin.rememberprocessingservice.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
