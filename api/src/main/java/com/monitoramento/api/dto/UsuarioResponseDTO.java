package com.monitoramento.api.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long id;
    private String email;
    private String role;

    public UsuarioResponseDTO(Long id, String email, String role){
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
