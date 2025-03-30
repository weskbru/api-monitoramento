package com.monitoramento.api.configuration;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // Injeção de dependência do JwtTokenProvider para usar a lógica de validação e extração do token
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtém o token da requisição HTTP
        String token = getJwtFromRequest(request);

        // Se o token for válido, autentica o usuário
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Extrai o e-mail do token
            String email = jwtTokenProvider.getEmailFromToken(token);
            // Cria o objeto de autenticação
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, null);
            // Coloca a autenticação no contexto do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua com o filtro da requisição
        filterChain.doFilter(request, response);
    }

    // Método para extrair o token do cabeçalho "Authorization"
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove o prefixo "Bearer " do token
        }
        return null;
    }
}
