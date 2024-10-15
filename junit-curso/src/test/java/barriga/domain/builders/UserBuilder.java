package barriga.domain.builders;

import barriga.domain.User;

public class UserBuilder {
    private Long id;
    private String name;
    private String email;
    private String password;

    private UserBuilder() {}

    public static UserBuilder aUser() {
        UserBuilder builder = new UserBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(UserBuilder builder) {
        builder.id = 1L;
        builder.name = "John John";
        builder.email = "john@john.com";
        builder.password = "password";
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User now() {
        return new User(this.id, this.name, this.email, this.password);
    }
}
