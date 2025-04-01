package com.monitoramento.api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDTO {
    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email inválido")
    private  String email;

    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;


}
