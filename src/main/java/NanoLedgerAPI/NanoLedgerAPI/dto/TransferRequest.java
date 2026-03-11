package NanoLedgerAPI.NanoLedgerAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Objeto de transferencia de datos (DTO) para las solicitudes de transferencia.
 * Incluye validaciones de Bean Validation para asegurar la integridad de los datos de entrada.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {

    @NotNull(message = "El ID de la cuenta de origen es obligatorio")
    private Long fromAccountId; // ID de la cuenta que envía el dinero

    @NotNull(message = "El ID de la cuenta de destino es obligatorio")
    private Long toAccountId; // ID de la cuenta que recibe el dinero

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un valor positivo")
    private BigDecimal amount; // Monto a transferir

    private String description; // Descripción opcional de la transferencia
}
