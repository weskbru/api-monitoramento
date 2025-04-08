
package com.monitoramento.api.controller;

import com.monitoramento.api.dto.UsuarioCadastroDTO;
import com.monitoramento.api.dto.UsuarioLoginDTO;
import com.monitoramento.api.dto.UsuarioResponseDTO;
import com.monitoramento.api.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoramento/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para gerenciamento de autenticação (login, cadastro e tokens)")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // ========== LOGIN ==========
    @PostMapping("/login")
    @Operation(
            summary = "Autenticação de Usuário",
            description = "Autentica um usuário com e-mail e senha válidos, retornando um token JWT para autorização.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "email": "usuario@exemplo.com",
                                        "senha": "123456"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticação bem-sucedida",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(
                                            value = "\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Credenciais inválidas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "usuario_nao_encontrado",
                                                    value = "{\"error\": \"Usuário não encontrado\"}"
                                            ),
                                            @ExampleObject(
                                                    name = "senha_invalida",
                                                    value = "{\"error\": \"Senha incorreta\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Falha ao processar autenticação\"}")
                            )
                    )
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

    // ========== CADASTRO ==========
    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastro de Novo Usuário",
            description = "Registra um novo usuário no sistema. Requer e-mail único e senha com mínimo 6 caracteres.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "nome": "João Silva",
                                        "email": "joao@exemplo.com",
                                        "senha": "123456",
                                        "role": "USER"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário cadastrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "id": 1,
                                                "nome": "João Silva",
                                                "email": "joao@exemplo.com",
                                                "role": "USER"
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "email_duplicado",
                                                    value = "{\"error\": \"E-mail já cadastrado\"}"
                                            ),
                                            @ExampleObject(
                                                    name = "dados_invalidos",
                                                    value = "{\"error\": \"Nome, e-mail ou senha inválidos\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO) {
        UsuarioResponseDTO response = authenticationService.cadastrarUsuario(usuarioCadastroDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}