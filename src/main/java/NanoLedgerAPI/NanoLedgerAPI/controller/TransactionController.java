package NanoLedgerAPI.NanoLedgerAPI.controller;

import NanoLedgerAPI.NanoLedgerAPI.dto.TransferRequest;
import NanoLedgerAPI.NanoLedgerAPI.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para manejar las peticiones relacionadas con transacciones.
 * Expone los endpoints necesarios para que el frontend o servicios externos interactúen con la API.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Endpoint para procesar una transferencia entre cuentas.
     * Recibe un JSON que mapea a {@link TransferRequest}.
     * 
     * @param request El objeto con los datos de la transferencia.
     * @return Respuesta HTTP 200 si la transacción fue exitosa.
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> process(@Valid @RequestBody TransferRequest request) {
        transactionService.processTransaction(request);
        return ResponseEntity.ok(Map.of("message", "Transacción procesada correctamente"));
    }
}
