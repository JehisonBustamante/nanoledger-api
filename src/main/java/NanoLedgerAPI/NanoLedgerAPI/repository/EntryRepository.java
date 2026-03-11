package NanoLedgerAPI.NanoLedgerAPI.repository;

import NanoLedgerAPI.NanoLedgerAPI.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de persistencia de la entidad Entry (asientos contables).
 */
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
}
