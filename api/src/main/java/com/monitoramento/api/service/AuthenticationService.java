package com.monitoramento.api.service;

import com.monitoramento.api.configuration.JwtTokenProvider;
import com.monitoramento.api.dto.UsuarioCadastroDTO;
import com.monitoramento.api.dto.UsuarioLoginDTO;
import com.monitoramento.api.dto.UsuarioResponseDTO;
import com.monitoramento.api.model.Usuario;
import com.monitoramento.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // Método para registrar o usuário
    public UsuarioResponseDTO cadastrarUsuario(UsuarioCadastroDTO usuarioCadastroDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioCadastroDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioCadastroDTO.getSenha())); // Criptografando a senha
        usuario.setRole(usuarioCadastroDTO.getRole());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioSalvo.getId(), usuarioSalvo.getEmail(), usuarioSalvo.getRole().name());
    }

    // Metodo para autenticar o usuário e gerar o token
    public String autenticar(UsuarioLoginDTO loginDTO) {
        // Verifica se o usuário existe no banco
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(loginDTO.getEmail());
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        // Verifica se a senha está correta
        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        // Se a autenticação for bem-sucedida, gera o token
        return jwtTokenProvider.generateToken(usuario.getEmail());
    }


}
