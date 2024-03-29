package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.RequestTemplate.*;
import com.fatec.tcc.agendify.Entities.User;
import com.fatec.tcc.agendify.Infra.DataTokenJWT;
import com.fatec.tcc.agendify.Infra.TokenService;
import com.fatec.tcc.agendify.Services.CepApiService;
import com.fatec.tcc.agendify.Services.LoginService;
import com.fatec.tcc.agendify.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/agenda")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @Autowired
    private CepApiService cepService;

    @GetMapping("/")
    public String healthcheck() {
        return "<html><h1>OI</h1></html>";
    }

    @GetMapping("/auth")
    public String auth() {
        return "This is a secure text";
    }

    @GetMapping("/noauth/{cep}")
    public ResponseEntity<ObjectNode> noAuth(@PathVariable("cep") String cep) {
        System.out.println();
        try {
            CepApi user = this.cepService.getAddressByCep(cep);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(user).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
//        return "This is not a secure text";
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<ObjectNode> createUser(@RequestBody UserBody user) {
        try {
            UserFields user1 = user.getIsJobProvider()
                    ? this.userService.createUserEnterprise(user)
                    : this.userService.createUserClient(user);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(user1).build(), HttpStatus.CREATED);
        } catch (RuntimeException | SQLIntegrityConstraintViolationException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            var token = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

            var auth = manager.authenticate(token);
            if (auth == null) throw new RuntimeException("Usuário não encontrado. Verifique suas informações de login");

            var tokenJWT = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new MessageResponseAPI(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = this.extractBearerToken(authorizationHeader);
            var idByToken = tokenService.getClaimId(token);

            return ResponseEntity.ok(new IdResponse(idByToken));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // 7 is the length of "Bearer "
        }
        return null;
    }

}
