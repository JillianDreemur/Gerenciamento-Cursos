package appGerente.GerenciamentoCursos.controller;

import appGerente.GerenciamentoCursos.models.dto.ApiResponse;
import appGerente.GerenciamentoCursos.models.dto.LoginRequest;
import appGerente.GerenciamentoCursos.models.dto.LoginResponse;
import appGerente.GerenciamentoCursos.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Login realizado com sucesso", response));
    }
}

