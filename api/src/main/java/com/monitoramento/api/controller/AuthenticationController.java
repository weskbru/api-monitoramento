package com.monitoramento.api.controller;

import com.monitoramento.api.dto.UsuarioLoginDTO;
import com.monitoramento.api.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
                    @ApiResponse(responseCode = "200", description = "Login bem-sucedido, retorna um token JWT"),
                    @ApiResponse(responseCode = "400", description = "Erro de autenticação (usuário não encontrado ou senha inválida)")
            }
    )
    public ResponseEntity<String> login(@RequestBody @Valid UsuarioLoginDTO loginDTO) {
        try {
            String token = authenticationService.autenticar(loginDTO);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
