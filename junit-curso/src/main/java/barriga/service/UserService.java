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
        // Connect with database
        // Prepare SQL query
        // Executar query
        // Obter o usuário persistido

        return repository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }
}
