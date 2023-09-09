package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.RequestTemplate.CepApi;
import com.fatec.tcc.agendify.Entities.RequestTemplate.Login;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Services.CepApiService;
import com.fatec.tcc.agendify.Services.LoginService;
import com.fatec.tcc.agendify.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@CrossOrigin
@RestController
@RequestMapping("/agenda")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @Autowired
    private CepApiService cepService;

    @GetMapping("/")
    public String healthcheck() {
        return "OK";
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
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
//        return "This is not a secure text";
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectNode> createUser(@RequestBody UserBody user) {
        try {
            this.userService.createUser(user);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException | SQLIntegrityConstraintViolationException | IOException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ObjectNode> login(@RequestBody Login login) {
        try {
            Long userLogin = this.loginService.login(login.getEmail(), login.getPassword());
            return new ResponseEntity<>(this.jsonResponseBuilder.withMessage(userLogin.toString()).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
}
