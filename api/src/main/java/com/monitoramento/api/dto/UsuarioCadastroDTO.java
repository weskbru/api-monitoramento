package com.monitoramento.api.dto;

import com.monitoramento.api.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCadastroDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Insira um email válido (ex: usuario@provedor.com)")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "Senha muito curta (mínimo 6 caracteres)")
    private String senha;

    @NotNull(message = "O perfil de acesso é obrigatório (USER ou ADMIN)")
    private Role role;
}