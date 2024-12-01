package barriga.repositories;

import barriga.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> getUserByEmail(String email);
}
