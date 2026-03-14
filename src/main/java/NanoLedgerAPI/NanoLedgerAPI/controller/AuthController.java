package NanoLedgerAPI.NanoLedgerAPI.controller;

import NanoLedgerAPI.NanoLedgerAPI.security.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    // MVP: Autenticación simulada. En un entorno real se verifica contra la BD usando AuthenticationManager y PasswordEncoder
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        
        // Simulación: Si el usuario es "admin" y la contraseña es "password", acceso concedido.
        if ("admin".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
            
            // Generamos el token usando nuestro JwtUtil
            String token = jwtUtil.generateToken(authRequest.getUsername());
            
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }
    }
}

@Data
class AuthRequest {
    private String username;
    private String password;
}

@Data
class AuthResponse {
    private final String jwt;
}
