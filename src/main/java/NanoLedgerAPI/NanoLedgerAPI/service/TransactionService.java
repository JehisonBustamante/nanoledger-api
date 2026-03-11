package NanoLedgerAPI.NanoLedgerAPI.service;

import NanoLedgerAPI.NanoLedgerAPI.dto.TransferRequest;
import NanoLedgerAPI.NanoLedgerAPI.exception.InsufficientBalanceException;
import NanoLedgerAPI.NanoLedgerAPI.model.Account;
import NanoLedgerAPI.NanoLedgerAPI.model.Entry;
import NanoLedgerAPI.NanoLedgerAPI.model.EntryType;
import NanoLedgerAPI.NanoLedgerAPI.model.Transaction;
import NanoLedgerAPI.NanoLedgerAPI.repository.AccountRepository;
import NanoLedgerAPI.NanoLedgerAPI.repository.EntryRepository;
import NanoLedgerAPI.NanoLedgerAPI.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Servicio encargado de procesar las transacciones financieras.
 * Implementa la lógica de negocio para transferencias entre cuentas siguiendo los principios de partida doble.
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final EntryRepository entryRepository;

    /**
     * Procesa una transferencia de dinero entre dos cuentas.
     * Esta operación es atómica gracias a @Transactional; si algo falla, se revierte todo.
     * 
     * @param request Datos de la transferencia (origen, destino, monto, descripción).
     */
    @Transactional
    public void processTransaction(TransferRequest request) {
        // 1. Validación: Las cuentas de origen y destino deben ser diferentes
        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new IllegalArgumentException("Las cuentas de origen y destino deben ser diferentes");
        }

        // 2. Validación: Existencia de las cuentas
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de origen no encontrada (ID: " + request.getFromAccountId() + ")"));

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de destino no encontrada (ID: " + request.getToAccountId() + ")"));

        // 3. Validación: Saldo suficiente en la cuenta de origen
        if (!fromAccount.hasEnoughBalance(request.getAmount())) {
            throw new InsufficientBalanceException("Saldo insuficiente en la cuenta: " + fromAccount.getAccountNumber() + 
                ". Disponible: " + fromAccount.getBalance() + ", Solicitado: " + request.getAmount());
        }

        // 4. Crear el contenedor de la Transacción
        Transaction transaction = Transaction.builder()
                .description(request.getDescription() != null ? request.getDescription() : "Transferencia")
                .timestamp(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction); // Se persiste para obtener el ID

        // 5. Crear los asientos contables (Lógica de partida doble)
        BigDecimal amount = request.getAmount();
        
        // Asiento de débito (resta de la cuenta de origen)
        Entry debitEntry = Entry.builder()
                .account(fromAccount)
                .transaction(transaction)
                .amount(amount.negate()) // El monto se guarda negativo para indicar salida
                .type(EntryType.DEBIT)
                .build();

        // Asiento de crédito (suma a la cuenta de destino)
        Entry creditEntry = Entry.builder()
                .account(toAccount)
                .transaction(transaction)
                .amount(amount) // El monto se guarda positivo para indicar entrada
                .type(EntryType.CREDIT)
                .build();

        // 6. Validación de integridad: La suma de debito + credito debe ser cero
        if (debitEntry.getAmount().add(creditEntry.getAmount()).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("Error interno: La transacción no está balanceada (Suma != 0)");
        }

        // 7. Actualizar los saldos de las cuentas y persistir los cambios
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        entryRepository.save(debitEntry);
        entryRepository.save(creditEntry);
        
        // Al terminar el método exitosamente, Spring confirma (commit) la transacción en la BD.
    }
}
