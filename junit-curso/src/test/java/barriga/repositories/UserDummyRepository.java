package barriga.repositories;

import barriga.domain.User;

import java.util.Optional;

import static barriga.domain.builders.UserBuilder.aUser;

public class UserDummyRepository implements UserRepository {
    @Override
    public User save(User user) {
        return aUser()
                .withName(user.getName())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .now();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.of(aUser().withEmail(email).now());
    }
}
