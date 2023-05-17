package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.Entities.Login;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Services.LoginService;
import com.fatec.tcc.agendeja.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@CrossOrigin
@RestController
@RequestMapping("/agenda")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/")
    public String healthcheck() {
        return "OK";
    }

    @GetMapping("/auth")
    public String auth() {
        return "This is a secure text";
    }

    @GetMapping("/noauth")
    public String noAuth() {
        return "This is not a secure text";
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectNode> createUser(@RequestBody User user) {
        try {
            this.userService.createUser(user);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException | SQLIntegrityConstraintViolationException e) {
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
