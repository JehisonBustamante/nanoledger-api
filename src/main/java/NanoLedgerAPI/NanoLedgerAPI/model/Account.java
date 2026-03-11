package NanoLedgerAPI.NanoLedgerAPI.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa una cuenta financiera en el sistema.
 * Utiliza Lombok para reducir el código repetitivo y JPA para la persistencia.
 */
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único interno (llave primaria)

    @Column(unique = true, nullable = false)
    private String accountNumber; // Número de cuenta público. Ej: "ACC-001"

    @Column(nullable = false)
    private String holderName; // Nombre del titular de la cuenta

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO; // Saldo de la cuenta manejado con precisión decimal

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Entry> entries; // Relación con los asientos contables (debitos/créditos)

    /**
     * Valida si la cuenta tiene saldo suficiente para una operación.
     * @param amount El monto a validar.
     * @return true si el saldo es mayor o igual al monto, false en caso contrario.
     */
    public boolean hasEnoughBalance(BigDecimal amount) {
        return this.balance != null && this.balance.compareTo(amount) >= 0;
    }
}
