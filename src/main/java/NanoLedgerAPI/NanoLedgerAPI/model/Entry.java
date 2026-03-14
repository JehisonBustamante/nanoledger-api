package NanoLedgerAPI.NanoLedgerAPI.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa un asiento contable individual (entrada) dentro de una transacción.
 * Sigue el principio de partida doble.
 */
@Entity
@Table(name = "entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del asiento

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; // Cuenta asociada a este asiento

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction; // Transacción que agrupa este asiento

    @Column(nullable = false)
    private BigDecimal amount; // Monto del asiento (negativo para débitos, positivo para créditos)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType type; // Tipo de asiento: DEBIT (débito) o CREDIT (crédito)
}
