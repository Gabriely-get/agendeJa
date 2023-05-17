package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Services.UserManipulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/user")
public class UserManipulationController {
    @Autowired
    private UserManipulationService userManipulationService;
    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/pag/{offset}/{limit}/{typePagination}")
    public ResponseEntity<ObjectNode> getUsersPageable(
            @PathVariable("typePagination") String paginationType,
            @PathVariable("offset") int offset,
            @PathVariable("limit") int limit
    ) throws JsonProcessingException {
        try {
            List<User> usersPaged = this.userManipulationService.findAllPageable(paginationType, offset, limit);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(usersPaged).build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
//
//    @GetMapping("/sort/username/asc")
//    public ResponseEntity<ObjectNode> getUsersOrderByUsernameAsc() {
//        try {
//            List<User> sortedUsers = this.userManipulationService.sortByUsernameAsc();
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(sortedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/sort/username/desc")
//    public ResponseEntity<ObjectNode> getUsersOrderByUsernameDesc() {
//        try {
//            List<User> sortedUsers = this.userManipulationService.sortByUsernameDesc();
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(sortedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/sort/birthday/asc")
//    public ResponseEntity<ObjectNode> getUsersOrderByBirthdayAsc() {
//        try {
//            List<User> sortedUsers = this.userManipulationService.sortByBirthdayAsc();
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(sortedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/sort/birthday/desc")
//    public ResponseEntity<ObjectNode> getUsersOrderByBirthdayDesc() {
//        try {
//            List<User> sortedUsers = this.userManipulationService.sortByBirthdayDesc();
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(sortedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/search/username")
//    public ResponseEntity<ObjectNode> searchUsersByUsernameContains(@Param("word") String word) {
//        try {
//            List<User> searchedUsers = this.userManipulationService.searchUsersByUsernameContains(word);
//            System.out.println(word);
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(searchedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/search/birthday")
//    public ResponseEntity<ObjectNode> searchUsersByBirthdayContains(@Param("date") String date) {
//        try {
//            List<User> searchedUsers = this.userManipulationService.searchUsersByBirthdayContains(date);
//            System.out.println(date);
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(searchedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/search/birthday/asc")
//    public ResponseEntity<ObjectNode> searchUsersByBirthdayContainsAndOrderAsc(@Param("date") String date) {
//        try {
//            List<User> searchedUsers = this.userManipulationService.searchUsersByBirthdayContainsOrderByAsc(date);
//            System.out.println(date);
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(searchedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/search/birthday/desc")
//    public ResponseEntity<ObjectNode> searchUsersByBirthdayContainsAndOrderDesc(@Param("date") String date) {
//        try {
//            List<User> searchedUsers = this.userManipulationService.searchUsersByBirthdayContainsOrderByDesc(date);
//            System.out.println(date);
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(searchedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/search/username/asc")
//    public ResponseEntity<ObjectNode> searchUsersByUsernameContainsAndOrderAsc(@Param("word") String word) {
//        try {
//            List<User> searchedUsers = this.userManipulationService.searchUsersByUsernameContainsOrderByAsc(word);
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(searchedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/search/username/desc")
//    public ResponseEntity<ObjectNode> searchUsersByUsernameContainsAndOrderDesc(@Param("word") String word) {
//        try {
//            List<User> searchedUsers = this.userManipulationService.searchUsersByUsernameContainsOrderByDesc(word);
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(searchedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/sort/oldest")
//    public ResponseEntity<ObjectNode> getUsersOrderByOldest() {
//        try {
//            List<User> sortedUsers = this.userManipulationService.getAllUsersOrderByOlder();
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(sortedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/sort/newest")
//    public ResponseEntity<ObjectNode> getUsersOrderByNewest() {
//        try {
//            List<User> sortedUsers = this.userManipulationService.getAllUsersOrderByNewest();
//
//            return new ResponseEntity<>(this.jsonResponseBuilder.withAllUsers(sortedUsers).build(), HttpStatus.OK);
//        } catch (RuntimeException | JsonProcessingException e) {
//            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
//        }
//    }

}
