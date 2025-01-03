package barriga.service;

import barriga.domain.Account;
import barriga.domain.Transaction;
import barriga.domain.User;
import barriga.domain.builders.AccountBuilder;
import barriga.domain.builders.TransactionBuilder;
import barriga.exceptions.ValidationException;
import barriga.external.ClockService;
import barriga.repositories.TransactionDAO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Stream;

import static barriga.domain.builders.AccountBuilder.aAccount;
import static barriga.domain.builders.UserBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@Tag("service")
@Tag("user")
//@EnabledIf(value = "isValidHour")
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock private TransactionDAO transactionDAO;
    @InjectMocks private TransactionService transactionService;
    @InjectMocks private TransactionService2 transactionService2;
    @Mock private ClockService clockService;
    @InjectMocks private TransactionServiceInjectTime transactionService3;
    @Captor private ArgumentCaptor<Transaction> transactionCaptor;

    @Test
    void save_shouldSaveValidTransaction() {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction().now();
        Transaction savedTransaction = TransactionBuilder.aTransaction().withId(1L).now();
        when(transactionDAO.save(transaction)).thenReturn(savedTransaction);

        LocalDateTime desiredData = LocalDateTime.of(2023, 1, 1, 4, 30, 28);
        System.out.println(desiredData);

        //MOCKING STATIC METHOD
        try(MockedStatic<LocalDateTime> ldt = mockStatic(LocalDateTime.class)){
            //WHEN
            ldt.when(LocalDateTime::now).thenReturn(desiredData);
            when(transactionService.save(transaction)).thenReturn(savedTransaction);

            //THEN
            assertAll("Valid Transaction",
                    ()-> assertEquals(transaction, transactionService.save(transaction)),
                    ()-> verify(transactionDAO, times(1)).save(transaction)
            );
            // OR CHAINED assertAll
            assertAll("Valid Transaction",
                    ()-> assertEquals(transaction, transactionService.save(transaction)),
                    ()-> assertAll("Valid Account",
                            ()-> assertEquals(transaction.getAccount(), savedTransaction.getAccount()),
                            ()-> assertAll("Valid User",
                                    ()-> assertEquals(transaction.getAccount().getUser(), savedTransaction.getAccount().getUser())
                            )
                    )
            );
        }
    }

    // MOCKING CONSTRUCTOR
    @Test
    void save_shouldSaveValidTransaction_MOCKING_CONSTRUCTOR() {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction().now();
        Transaction savedTransaction = TransactionBuilder.aTransaction().withId(1L).now();
        when(transactionDAO.save(transaction)).thenReturn(savedTransaction);

        System.out.println(new Date().getHours());
        //MOCKING CONSTRUCTOR
        try(MockedConstruction<Date> date = mockConstruction(Date.class,
                (mock, context)-> when(mock.getHours()).thenReturn(4)
        )){
            //WHEN
            System.out.println(new Date().getHours());

            when(transactionService2.save(transaction)).thenReturn(savedTransaction);

            //THEN
            assertAll("Valid Transaction",
                    ()-> assertEquals(transaction, transactionService2.save(transaction)),
                    ()-> verify(transactionDAO, times(1)).save(transaction),
                    ()-> assertEquals(3, date.constructed().size())
            );
        }
        System.out.println(new Date().getHours());
    }

    // Refactoring for dependency inversion
    @Test
    void save_shouldSaveValidTransaction_REFACTORING() {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction().now();
        Transaction savedTransaction = TransactionBuilder.aTransaction().withId(1L).now();

        //WHEN
        when(transactionDAO.save(transaction)).thenReturn(savedTransaction);
        when(clockService.getCurrentTime()).thenReturn(LocalDateTime.of(2024, 1, 1, 2, 0, 0));
        when(transactionService3.save(transaction)).thenReturn(savedTransaction);

        //THEN
        assertAll("Valid Transaction",
                () -> assertEquals(transaction, transactionService3.save(transaction)),
                () -> verify(transactionDAO, times(1)).save(transaction)
        );
    }


    @Test
    void save_shouldValidationExceptionWhenInvalidTransactionTriesToBeSaved() {
        assertAll("Invalid Transaction",
                () -> assertThrows(ValidationException.class, () -> transactionService.save(null)),
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
    void save_shouldThrowValidationExceptionWhenInvalidTransactionTriesToBeSaved(
            String description, Double value, boolean isAccountNull, boolean isDateNull, Boolean status, String message) {
        //GIVEN
        Account account = isAccountNull? null: aAccount().now();
        LocalDateTime date = isDateNull? null: LocalDateTime.now();

        Transaction transaction = TransactionBuilder.aTransaction()
                .withDescription(description)
                .withValue(value)
                .withAccount(account)
                .withDate(date)
                .withStatus(status)
                .now();

        //THEN
        ValidationException exception = assertThrows(ValidationException.class, () -> transactionService.save(transaction));
        assertAll("Invalid Transaction",
                ()-> assertEquals(message, exception.getMessage()),
                ()-> verify(transactionDAO, never()).save(transaction)
        );
    }

    // This way is better!
    @ParameterizedTest(name ="{5}")
    @DisplayName("UsingDataProvider : Should Throw ValidationException when ...")
    @MethodSource(value = "transactionProvider")
    void save_shouldThrowValidationExceptionWhenInvalidTransactionTriesToBeSaved_UsingDataProvider(
            String description, Double value, Account account, LocalDateTime date, Boolean status, String message) {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction()
                .withDescription(description)
                .withValue(value)
                .withAccount(account)
                .withDate(date)
                .withStatus(status)
                .now();

        //THEN
        ValidationException exception = assertThrows(ValidationException.class, () -> transactionService.save(transaction));
        assertAll("Invalid Transaction",
                ()-> assertEquals(message, exception.getMessage()),
                ()-> verify(transactionDAO, never()).save(transaction)
        );
    }

    private static Stream<Arguments> transactionProvider() {
        return Stream.of(
                Arguments.of(null, 100.0, aAccount().now(), LocalDateTime.now(), false, "Transaction description is null"),
                Arguments.of("Valid Transaction", null, aAccount().now(), LocalDateTime.now(), false, "Transaction value is null"),
                Arguments.of("Valid Transaction", 100.0, null, LocalDateTime.now(), false, "Transaction account is null"),
                Arguments.of("Valid Transaction", 100.0, aAccount().now(), null, false, "Transaction date is null"),
                Arguments.of("Valid Transaction", 100.0, aAccount().now(), LocalDateTime.now(), false, "Transaction date should not be greater than 5")
        );
    }

    @Test
    @DisplayName("Should set status False when status is null")
    void save_shouldSetStatusFalseWhenStatusIsNull() {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction().withStatus(null).now();
        //WHEN
        when(transactionDAO.save(transaction)).thenReturn(transaction);
        LocalDateTime desiredData = LocalDateTime.of(2023, 1, 1, 4, 30, 28);

        try(MockedStatic<LocalDateTime> ldt = mockStatic(LocalDateTime.class)) {
            ldt.when(LocalDateTime::now).thenReturn(desiredData);
            transactionService.save(transaction);
        }
        //THEN
        verify(transactionDAO).save(transactionCaptor.capture());
        assertFalse(transactionCaptor.getValue().getStatus());
    }


//    private static boolean isValidHour() {
//        return LocalDateTime.now().getHour() > 16;
//    }
}