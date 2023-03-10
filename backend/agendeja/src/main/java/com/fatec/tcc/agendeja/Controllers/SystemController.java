package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.Entities.Login;
import com.fatec.tcc.agendeja.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/agenda")
public class SystemController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/")
    public String healthcheck() {
        return "OK";
    }

    @PostMapping("/login")
    public ResponseEntity<ObjectNode> login(@RequestBody Login login) {
        try {
            String userLogin = this.loginService.login(login.getEmail(), login.getPassword());
            return new ResponseEntity<>(this.jsonResponseBuilder.withMessage(userLogin).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
}
