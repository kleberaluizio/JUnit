package barriga.domain;

import barriga.exceptions.ValidationException;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    public User(Long id, String name, String email, String password) {
        assertValidData(id, name, email, password);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void assertValidData(Long id, String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("E-mail cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be null or empty");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
