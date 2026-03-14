package NanoLedgerAPI.NanoLedgerAPI.service;

import NanoLedgerAPI.NanoLedgerAPI.dto.TransferRequest;
import NanoLedgerAPI.NanoLedgerAPI.exception.InsufficientBalanceException;
import NanoLedgerAPI.NanoLedgerAPI.model.Account;
import NanoLedgerAPI.NanoLedgerAPI.repository.AccountRepository;
import NanoLedgerAPI.NanoLedgerAPI.repository.EntryRepository;
import NanoLedgerAPI.NanoLedgerAPI.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EntryRepository entryRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        fromAccount = Account.builder()
                .id(1L)
                .accountNumber("ACC-001")
                .balance(new BigDecimal("1000.00"))
                .build();

        toAccount = Account.builder()
                .id(2L)
                .accountNumber("ACC-002")
                .balance(new BigDecimal("500.00"))
                .build();
    }

    @Test
    void processTransaction_Success() {
        // Arrange
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal("200.00"));
        request.setDescription("Test Transfer");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        // Act
        transactionService.processTransaction(request);

        // Assert
        assertEquals(new BigDecimal("800.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("700.00"), toAccount.getBalance());

        verify(transactionRepository, times(1)).save(any());
        verify(entryRepository, times(2)).save(any());
        verify(accountRepository, times(2)).save(any());
    }

    @Test
    void processTransaction_ThrowsInsufficientBalanceException() {
        // Arrange
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal("1500.00")); // More than balance
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        // Act & Assert
        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.processTransaction(request);
        });

        assertEquals("Saldo insuficiente en la cuenta: ACC-001. Disponible: 1000.00, Solicitado: 1500.00", exception.getMessage());
        
        // Verificamos que no se haya guardado nada
        verify(transactionRepository, never()).save(any());
        verify(entryRepository, never()).save(any());
    }

    @Test
    void processTransaction_ThrowsIllegalArgumentException_SameAccounts() {
        // Arrange
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(1L); // Same account
        request.setAmount(new BigDecimal("100.00"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.processTransaction(request);
        });

        assertEquals("Las cuentas de origen y destino deben ser diferentes", exception.getMessage());
        verify(accountRepository, never()).findById(any());
    }
}
