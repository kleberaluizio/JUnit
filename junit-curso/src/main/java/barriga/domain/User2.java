package barriga.domain;

import barriga.exceptions.ValidationException;

public class User2 {
    private Long id;
    private String name;
    private String email;
    private String password;

    public User2(Long id, String name, String email, String password) {
        assertValidData(id, name, email, password);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void assertValidData(Long id, String name, String email, String password) {
        if (isNullOrEmpty(name)) {
            throw new ValidationException("Name cannot be null or empty");
        }
        if (isNullOrEmpty(email)) {
            throw new ValidationException("Email cannot be null or empty");
        }
        if (isNullOrEmpty(password)) {
            throw new ValidationException("Password cannot be null or empty");
        }
    }

    public boolean isNullOrEmpty(String parameter) {
        return parameter == null || parameter.trim().isEmpty();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
