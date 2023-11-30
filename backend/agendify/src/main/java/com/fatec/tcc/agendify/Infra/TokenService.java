package com.fatec.tcc.agendify.Infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fatec.tcc.agendify.Entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService  {
    @Value("${api.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Agendify_api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", user.getId()) // chave-valor
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro gerar token", exception);
        }
    }

    public String getSubject(String tokenJWT) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Agendify_api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido ou expirado. Tente fazer login novamente");
        }
    }

    public Long getClaimId(String tokenJWT) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Agendify_api")
                    .build()
                    .verify(tokenJWT)
                    .getClaim("id").asLong();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido ou expirado. Tente fazer login novamente");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
