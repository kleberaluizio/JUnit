package barriga.service;

import barriga.domain.User;
import barriga.exceptions.ValidationException;
import barriga.repositories.UserRepository;

import java.util.Optional;

public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        findUserByEmail(user.getEmail()).ifPresent(existingUser ->{
            throw new ValidationException(String.format("User %s already exists!", existingUser.getEmail()));
        });
        return repository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }
}
