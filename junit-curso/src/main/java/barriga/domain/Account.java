package barriga.domain;

import barriga.exceptions.ValidationException;

public class Account {
    private Long id;
    private String name;
    private User user;

    public Account() {}

    public Account(Long id, String name, User user) {
        assertValidData(name, user);
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void assertValidData(String name, User user) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        if (user == null ) {
            throw new ValidationException("User cannot be null");
        }
    }
}
