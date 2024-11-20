package barriga.domain.builders;

import barriga.domain.User;
import barriga.domain.Account;

public class AccountBuilder {
	private Long id;
	private String name;
	private User user;

	private AccountBuilder(){}

	public static AccountBuilder aAccount() {
		AccountBuilder builder = new AccountBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	private static void initializeDefaultData(AccountBuilder builder) {
		builder.id = 1L;
		builder.name = "Valid Account";
		builder.user = UserBuilder.aUser().now();
	}

	public AccountBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public AccountBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public AccountBuilder withUser(User user) {
		this.user = user;
		return this;
	}

	public Account now() {
		return new Account(this.id, this.name, this.user);
	}
}