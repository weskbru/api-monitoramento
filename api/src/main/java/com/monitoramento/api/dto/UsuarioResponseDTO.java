package com.monitoramento.api.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String role;

    public UsuarioResponseDTO(Long id, String nome, String email, String role){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }
}
