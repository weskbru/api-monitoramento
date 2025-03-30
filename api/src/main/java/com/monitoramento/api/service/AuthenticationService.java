package com.monitoramento.api.service;

import com.monitoramento.api.model.Usuario;
import com.monitoramento.api.repository.UsuarioRepository;
import com.monitoramento.api.configuration.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok gera automaticamente o construtor com parâmetros finais
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // Metodo para autenticar o usuário e gerar o token JWT
    public String autenticar(String email, String senha) {
        // Buscar o usuário pelo e-mail
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        // Se o usuário não existir ou a senha não for válida, lança uma exceção
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar se a senha fornecida corresponde à senha armazenada no banco de dados
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        // Gerar o token JWT para o usuário
        return jwtTokenProvider.generateToken(usuario.getEmail());
    }
}
