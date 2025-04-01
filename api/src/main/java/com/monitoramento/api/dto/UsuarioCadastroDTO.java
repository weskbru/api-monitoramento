package com.monitoramento.api.dto;


import com.monitoramento.api.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class UsuarioCadastroDTO {
    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    private Role role;
}
