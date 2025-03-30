package com.monitoramento.api.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String SECRET_KEY = "secrect_key_exemplo";
    private final long EXPIRATION_TIME = 863_000_000L;

    // Gera o token JWT com regras de expiraçao
    public String generateToken(String email) {
        return Jwts.builder()  // Inicia a criação do token
                .setSubject(email)  // Adiciona o email como a "identidade" do token
                .setIssuedAt(new Date())  // Data em que o token foi criado
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Define a data de expiração
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)  // Assina o token com uma chave secreta
                .compact();  // Constrói o token
    }


    // Valida o tokrn JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJwt(token);
            return true; // Se não houver erro, o token é válido
        } catch (JwtException | IllegalArgumentException e) { // Se houver erro, o token é inválido
            return false;
        }
    }

    // Extair o email do token
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJwt(token).getBody();// Pega o conteúdo do token
        return claims.getSubject(); // Retorna o email (ou "subject") do token
    }
}
