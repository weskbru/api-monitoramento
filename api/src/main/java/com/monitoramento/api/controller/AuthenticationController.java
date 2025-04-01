package com.monitoramento.api.controller;

import com.monitoramento.api.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(
            summary = "Autenticação de Usuário",
            description = "Endpoint para autenticar um usuário e retornar um token JWT válido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login bem-sucedido, retorna um token JWT",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = @ExampleObject(value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Erro de autenticação (usuário não encontrado ou senha inválida)",
                            content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = "Usuário não encontrado")))
            }
    )
    public ResponseEntity<String> login(
            @RequestParam @Schema(description = "E-mail do usuário", example = "user@example.com") String email,
            @RequestParam @Schema(description = "Senha do usuário", example = "senha123") String senha
    ) {
        try {
            String token = authenticationService.autenticar(email, senha);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
