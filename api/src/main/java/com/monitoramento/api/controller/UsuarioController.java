package com.monitoramento.api.controller;

import com.monitoramento.api.dto.UsuarioCadastroDTO;
import com.monitoramento.api.dto.UsuarioResponseDTO;
import com.monitoramento.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {
    private final AuthenticationService authenticationService;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody UsuarioCadastroDTO usuarioCadastroDTO) {
        UsuarioResponseDTO usuarioResponseDTO = authenticationService.cadastrarUsuario(usuarioCadastroDTO);
        return new ResponseEntity<>(usuarioResponseDTO, HttpStatus.CREATED);
    }

}
