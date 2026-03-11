package NanoLedgerAPI.NanoLedgerAPI.repository;

import NanoLedgerAPI.NanoLedgerAPI.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de persistencia de la entidad Transaction.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
