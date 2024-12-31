package barriga.service;

import barriga.domain.Transaction;
import barriga.domain.builders.TransactionBuilder;
import barriga.exceptions.ValidationException;
import barriga.external.ClockService;
import barriga.repositories.TransactionDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TransactionServiceSpyTest {
    @Mock private TransactionDAO transactionDAO;
    @InjectMocks @Spy private TransactionServiceSpy transactionService;

    @Test
    void save_shouldSaveValidTransaction() {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction().now();

        //WHEN
        when(transactionDAO.save(transaction)).thenReturn(transaction);
        doReturn(LocalDateTime.of(2023, 1, 1, 4, 30, 28)).when(transactionService).getTime();

        //THEN
        assertAll("Valid Transaction",
                () -> assertEquals(transaction, transactionService.save(transaction)),
                () -> verify(transactionDAO, times(1)).save(transaction)
        );
    }

    // Testando métodos privados
    @Test
    void save_shouldThrowExceptionWhenTransactionInvalid() throws Exception {
        //GIVEN
        Transaction transaction = TransactionBuilder.aTransaction().withValue(null).now();
        Method method = TransactionServiceSpy.class.getDeclaredMethod("assertValidData", Transaction.class);
        method.setAccessible(true);

        //WHEN
        Exception exception = assertThrows(Exception.class, () -> method.invoke(transactionService, transaction));

        //THEN
        assertEquals("Transaction value is null", exception.getCause().getMessage());
    }
}
