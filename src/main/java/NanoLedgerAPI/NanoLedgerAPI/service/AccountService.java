package NanoLedgerAPI.NanoLedgerAPI.service;

import NanoLedgerAPI.NanoLedgerAPI.model.Account;
import NanoLedgerAPI.NanoLedgerAPI.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de las cuentas.
 * Actúa como intermediario entre el controlador y el repositorio, centralizando la lógica de negocio.
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * Crea una nueva cuenta aplicando las validaciones de negocio correspondientes.
     * 
     * @param account El objeto Account a procesar y guardar.
     * @return La cuenta guardada con su ID generado.
     */
    public Account createAccount(Account account) {
        // Aquí se pueden agregar validaciones futuras, por ejemplo:
        // si mañana quiero validar que el nombre del titular no tenga números.

        return accountRepository.save(account);
    }

    /**
     * Obtiene la lista de todas las cuentas registradas.
     * 
     * @return Una lista de cuentas.
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
