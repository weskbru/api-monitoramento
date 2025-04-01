package com.monitoramento.api.controller;


import com.monitoramento.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        try {
            // Chama o serviço de autenticação para gerar o token JWT
            String token = authenticationService.autenticar(email, senha);
            return ResponseEntity.ok(token);// Retorna o token no corpo da resposta
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());// Retorna uma mensagem de erro caso falhe
        }
    }
}


