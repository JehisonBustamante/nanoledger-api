package NanoLedgerAPI.NanoLedgerAPI.controller;

import NanoLedgerAPI.NanoLedgerAPI.model.Account;
import NanoLedgerAPI.NanoLedgerAPI.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de cuentas.
 * Proporciona endpoints para la creación y consulta de cuentas.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Crea una nueva cuenta en el sistema.
     * 
     * @param account El objeto Account a guardar.
     * @return La cuenta guardada con su ID generado.
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    /**
     * Obtiene la lista de todas las cuentas registradas.
     * 
     * @return Una lista de cuentas.
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
