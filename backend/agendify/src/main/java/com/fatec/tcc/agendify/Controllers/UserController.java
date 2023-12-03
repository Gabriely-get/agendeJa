package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.RequestTemplate.EnterpriseProfileResponse;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Entities.User;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserFields;
import com.fatec.tcc.agendify.Services.CompanyBranchService;
import com.fatec.tcc.agendify.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyBranchService companyBranchService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getUser(@PathVariable("id") Long id) {
        try {
            UserFields user = this.userService.getUserById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(user).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ObjectNode> getUserProfile(@PathVariable("id") Long id) {
        try {
            EnterpriseProfileResponse profile = this.companyBranchService.getProfile(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(profile).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getUsers() {
        try {
            List<User> users = this.userService.getAllUsers();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(users).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/provider")
    public ResponseEntity<ObjectNode> getUsersEnterprise() {
        try {
            List<User> users = this.userService.getAllUsersEnterprise();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(users).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ObjectNode> getActiveUsers() {
        try {
            List<User> users = this.userService.getActiveUsers();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(users).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updateUser(@PathVariable("id") Long id, @RequestBody UserBody user) {
        try {
            this.userService.updateUser(id, user);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deleteUser(@PathVariable("id") Long id) {
        try {
            this.userService.deleteUser(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    //TODO method restore/active user, with permission from ADMIN
    // discover what is needed be active -> update, delete prob

}
