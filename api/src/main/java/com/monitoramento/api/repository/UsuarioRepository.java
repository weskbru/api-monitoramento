package com.monitoramento.api.repository;


import com.monitoramento.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Metodo para LOGIN (busca usuário por email)
    Optional<Usuario> findByEmail(String email);

    // Metodo para CADASTRO (verifica se email existe)
    boolean existsByEmailIgnoreCase(String email);
}
