package barriga.service;

import barriga.domain.Account;
import barriga.domain.Transaction;
import barriga.domain.builders.AccountBuilder;
import barriga.domain.builders.TransactionBuilder;
import barriga.exceptions.ValidationException;
import barriga.repositories.TransactionDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock private TransactionDAO transactionDAO;
    @InjectMocks private TransactionService transactionService;

    @Test
    void save_shouldSaveValidTransaction() {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction().now();
        Transaction savedTransaction = TransactionBuilder.aTransaction().withId(1L).now();

        //WHEN
        when(transactionDAO.save(transaction)).thenReturn(savedTransaction);

        //THEN
        Assertions.assertDoesNotThrow(() -> transactionService.save(transaction));
    }

    @Test
    void save_shouldValidationExceptionWhenInvalidTransactionTriesToBeSaved() {
        Assertions.assertAll("Transaction Invalid",
                () -> Assertions.assertThrows(ValidationException.class, () -> transactionService.save(null)),
                () -> verify(transactionDAO, never()).save(null)
        );
    }


    @ParameterizedTest(name ="{5}")
    @DisplayName("Should Throw ValidationException when ...")
    @CsvSource(
            value = {
                "null, 100.0, false, false, false, Transaction description is null",
                "Valid Transaction, null, false, false, false, Transaction value is null",
                "Valid Transaction, 100.0, true, false, false, Transaction account is null",
                "Valid Transaction, 100.0, false, true, false, Transaction date is null"}
            , nullValues = "null"
    )
    void save_shouldValidationExceptionWhenInvalidTransactionTriesToBeSaved(
            String description, Double value, boolean isAccountNull, boolean isDateNull, Boolean status, String displayMessage) {
        //GIVEN
        Account account = isAccountNull? null: AccountBuilder.aAccount().now();
        LocalDate date = isDateNull? null: LocalDate.now();

        TransactionBuilder transactionBuilder = TransactionBuilder.aTransaction()
                .withDescription(description)
                .withValue(value)
                .withAccount(account)
                .withDate(date)
                .withStatus(status);

        Transaction transaction = transactionBuilder.now();

        //THEN
        Assertions.assertAll("Transaction Invalid",
                ()-> Assertions.assertThrows(ValidationException.class, () -> transactionService.save(transaction)),
                ()-> verify(transactionDAO, never()).save(transaction)
        );
    }
}