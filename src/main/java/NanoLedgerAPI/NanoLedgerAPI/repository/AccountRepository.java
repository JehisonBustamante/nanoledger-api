package NanoLedgerAPI.NanoLedgerAPI.repository;

import NanoLedgerAPI.NanoLedgerAPI.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestión de persistencia de la entidad Account.
 * Proporciona métodos para búsqueda y manipulación de cuentas en la base de datos.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Busca una cuenta por su número de cuenta público.
     * @param accountNumber El número de cuenta (ej: "ACC-001").
     * @return Un Optional que contiene la cuenta si se encuentra.
     */
    Optional<Account> findByAccountNumber(String accountNumber);
}
