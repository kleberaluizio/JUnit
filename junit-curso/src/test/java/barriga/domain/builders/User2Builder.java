package barriga.domain.builders;

import barriga.domain.User2;

public class User2Builder {
	private Long id;
	private String name;
	private String email;
	private String password;

	private User2Builder(){}

	public static User2Builder umUser2() {
		User2Builder builder = new User2Builder();
		initializeDefaultData(builder);
		return builder;
	}

	private static void initializeDefaultData(User2Builder builder) {
		builder.id = 0L;
		builder.name = "";
		builder.email = "";
		builder.password = "";
	}

	public User2Builder withId(Long id) {
		this.id = id;
		return this;
	}

	public User2Builder withName(String name) {
		this.name = name;
		return this;
	}

	public User2Builder withEmail(String email) {
		this.email = email;
		return this;
	}

	public User2Builder withPassword(String password) {
		this.password = password;
		return this;
	}

	public User2 now() {
		return new User2(this.id, this.name, this.email, this.password);
	}
}