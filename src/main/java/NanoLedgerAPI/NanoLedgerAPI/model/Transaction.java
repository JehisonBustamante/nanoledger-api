package NanoLedgerAPI.NanoLedgerAPI.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa una transacción en el sistema, la cual agrupa múltiples asientos contables (entries).
 * Sigue el principio de atomicidad: todas las entradas de una transacción deben persistirse juntas.
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la transacción

    private String description; // Descripción o motivo de la transacción

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now(); // Fecha y hora en que ocurrió la transacción

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Entry> entries; // Lista de asientos (débitos y créditos) asociados a esta transacción
}
