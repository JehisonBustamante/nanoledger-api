package NanoLedgerAPI.NanoLedgerAPI.model;

/**
 * Define los tipos de asientos contables permitidos en el sistema.
 */
public enum EntryType {
    DEBIT,  // Débito: Representa una salida de dinero (resta al saldo)
    CREDIT  // Crédito: Representa una entrada de dinero (suma al saldo)
}
