package com.monitoramento.api.service;

import com.monitoramento.api.configuration.JwtTokenProvider;
import com.monitoramento.api.dto.UsuarioCadastroDTO;
import com.monitoramento.api.dto.UsuarioLoginDTO;
import com.monitoramento.api.dto.UsuarioResponseDTO;
import com.monitoramento.api.exceptions.CredenciaisInvalidasException;
import com.monitoramento.api.exceptions.EmailJaCadastradoException;
import com.monitoramento.api.model.Usuario;
import com.monitoramento.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // ========== CADASTRO ==========
    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioCadastroDTO dto) {
        String emailNormalizado = dto.getEmail().toLowerCase().trim();

        if (usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)) {
            throw new EmailJaCadastradoException("Erro no processamento do cadastro");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome().trim())
                .email(emailNormalizado)
                .senha(passwordEncoder.encode(dto.getSenha()))
                .role(dto.getRole())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return toResponseDTO(usuarioSalvo);
    }

    // ========== LOGIN ==========
    public String autenticar(UsuarioLoginDTO loginDTO) {
        String emailNormalizado = loginDTO.getEmail().toLowerCase().trim();
        Usuario usuario = usuarioRepository.findByEmail(emailNormalizado)
                .orElseThrow(() -> new CredenciaisInvalidasException("Credenciais inválidas"));

        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException("Credenciais inválidas");
        }

        return jwtTokenProvider.generateToken(usuario.getEmail());
    }

    // Metodo auxiliar
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .build();
    }
}