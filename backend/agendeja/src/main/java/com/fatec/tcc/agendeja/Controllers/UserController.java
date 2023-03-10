package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendeja.Entities.Login;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Services.LoginService;
import com.fatec.tcc.agendeja.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/agenda/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ObjectNode> createUser(@RequestBody User user) {
        try {
            this.userService.createUser(user);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (IllegalUserArgumentException | SQLIntegrityConstraintViolationException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

}
