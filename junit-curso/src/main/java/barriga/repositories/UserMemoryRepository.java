package barriga.repositories;

import barriga.domain.User;
import barriga.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserMemoryRepository implements UserRepository {

    private List<User> users;
    private static Long currentUserId;

    public UserMemoryRepository() {
        currentUserId = 0L;
        users = new ArrayList<User>();
        save(new User(null, "User #1", "user1@gmail.com", "123456"));
    }

    @Override
    public User save(User user) {
        User newUser = new User(nextUserId(), user.getName(), user.getEmail(), user.getPassword());
        users.add(newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    private Long nextUserId() {
        return ++currentUserId;
    }

    public void printUsers() {
        System.out.println(users);
    }

    public static void main(String[] args) {
        UserMemoryRepository repository = new UserMemoryRepository();
        repository.printUsers();
        repository.save(new User(null, "User #2", "user2@gmail.com", "aa"));
        repository.printUsers();
    }
}
