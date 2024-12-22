package barriga.domain.builders;

import barriga.domain.Account;
import java.time.LocalDate;
import java.time.LocalDateTime;

import barriga.domain.Transaction;

public class TransactionBuilder {
	private Long id;
	private String description;
	private Double value;
	private Account account;
	private LocalDateTime date;
	private Boolean status;

	private TransactionBuilder(){}

	public static TransactionBuilder aTransaction() {
		TransactionBuilder builder = new TransactionBuilder();
		initializeDefaultData(builder);
		return builder;
	}

	private static void initializeDefaultData(TransactionBuilder builder) {
		builder.id = null;
		builder.description = "Valid Transaction";
		builder.value = 100.0;
		builder.account = AccountBuilder.aAccount().now();
		builder.date = LocalDateTime.now();
		builder.status = false;
	}

	public TransactionBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public TransactionBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public TransactionBuilder withValue(Double value) {
		this.value = value;
		return this;
	}

	public TransactionBuilder withAccount(Account account) {
		this.account = account;
		return this;
	}

	public TransactionBuilder withDate(LocalDateTime date) {
		this.date = date;
		return this;
	}

	public TransactionBuilder withStatus(Boolean status) {
		this.status = status;
		return this;
	}

	public Transaction now() {
		Transaction transaction = new Transaction();
		transaction.setId(id);
		transaction.setDescription(description);
		transaction.setValue(value);
		transaction.setAccount(account);
		transaction.setDate(date);
		transaction.setStatus(status);
		return transaction;
	}
}