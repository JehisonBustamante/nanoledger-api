package NanoLedgerAPI.NanoLedgerAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Redirecciona la raíz (/) hacia Swagger UI para que los reclutadores
     * vean la documentación de la API inmediatamente.
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }
}

