package com.fatec.tcc.agendify.Infra;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatec.tcc.agendify.Repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
public class SecurityFilter extends OncePerRequestFilter  {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws RuntimeException, ServletException, IOException {
        try {
            var tokenJWT = recuperarToken(request);

            if (tokenJWT != null) {
                var subject = this.tokenService.getSubject(tokenJWT);
                var usuario = this.repository.findUserByEmail(subject);

                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


            System.out.println(tokenJWT);
            //sem essa linha o spring n sabe o que fazer com o request. Neste caso: passa para o next/proxima função
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            // Captura exceções de autenticação (ou outras exceções, se necessário)
            handleAuthenticationException(response, e);
        }
    }

    private void handleAuthenticationException(HttpServletResponse response, RuntimeException e) throws IOException {
        // Configura a resposta HTTP com o código de status e a mensagem de erro desejados
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse("Falha na autenticação: " + e.getMessage())));
    }

    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

}
