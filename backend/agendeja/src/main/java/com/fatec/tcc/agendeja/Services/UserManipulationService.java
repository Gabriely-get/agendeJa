package com.fatec.tcc.agendeja.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fatec.tcc.agendeja.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendeja.Entities.PaginationType;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManipulationService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllPageable(String paginationType, int offset, int limit) {
        //TODO
        // "welcome to baeldung", s.replace('s', 'e') replace / in date for -
        // check if datew is vallid
        // asc or desc -> isAsc true or false :) ; maybe turn pageable a boolean param too
        // refactor to make equals(?) rick and morty api

        if (offset < 0 || limit < 0) throw new IllegalArgumentException("Invalid pagination arguments!");

        int paginationTypeId = 0;
        Pageable usersPageable = PageRequest.of(offset,limit);
        Page<User> usersPage;

        if (paginationType.matches("[0-9]+")) {
            if ( !PaginationType.paginationTypeExists( Integer.parseInt(paginationType) ) )
                throw new IllegalArgumentException("Is not possible to make pagination. This type of pagination does not exists!");
            paginationTypeId = Integer.parseInt( paginationType );
        } else {
            if (!PaginationType.paginationTypeExists(paginationType))
                throw new IllegalArgumentException("Is not possible to make pagination. This type of pagination does not exists!");
        }

        if (paginationType.equals("SNA") || paginationTypeId == 1) {
            usersPage = this.userRepository.getAllOrderNameByAscPageable(usersPageable);
            return usersPage.toList();
        }

        if (paginationType.equals("SND") || paginationTypeId == 2) {
            usersPage = this.userRepository.getAllOrderNameByDescPageable(usersPageable);
            return usersPage.toList();
        }

        if (paginationType.equals("SBA") || paginationTypeId == 3) {
            usersPage = this.userRepository.getAllOrderBirthdayByAscPageable(usersPageable);
            return usersPage.toList();
        }

        if (paginationType.equals("SBD") || paginationTypeId == 4) {
            usersPage = this.userRepository.getAllOrderBirthdayByDescPageable(usersPageable);
            return usersPage.toList();
        }

        throw new RuntimeException("Some error occurred on pagination");
    }

    //TODO concatenar e fazer query select * e ordenar o selecionado
    public List<User> sortByUsernameAsc() {
        return this.userRepository.getAllOrderNameByAsc();
    }

    public List<User> sortByUsernameDesc() {
        return this.userRepository.getAllOrderNameByDesc();
    }

    public List<User> sortByBirthdayAsc() {
        return this.userRepository.findAll(Sort.by(Sort.Direction.ASC, "birthday"));
    }

    public List<User> sortByBirthdayDesc() {
        return this.userRepository.findAll(Sort.by(Sort.Direction.DESC, "birthday"));
    }

    public List<User> searchUsersByUsernameContains(String word) {
        return this.userRepository.findAllByFullusernameContains(word);
    }

    public List<User> searchUsersByBirthdayContains(String date) {
        return this.userRepository.findAllByBirthdayContains(date);
    }

    public List<User> searchUsersByBirthdayContainsOrderByAsc(String date) {

        return this.userRepository.findAllByBirthdayContainsOrderByAsc(date);
    }

    public List<User> searchUsersByBirthdayContainsOrderByDesc(String date) {
        return this.userRepository.findAllByBirthdayContainsOrderByDesc(date);
    }

    public List<User> searchUsersByUsernameContainsOrderByAsc(String word) {

        return this.userRepository.getAllFullusernameContainsOrderByAsc(word);
    }

    public List<User> searchUsersByUsernameContainsOrderByDesc(String word) {
        return this.userRepository.findAllByFullusernameContainsOrderByDesc(word);
    }

    public List<User> getAllUsersOrderByOlder() {
        return this.userRepository.findAllOrderByCreateAtAsc();
    }

    public List<User> getAllUsersOrderByNewest() {
        return this.userRepository.findAllOrderByCreateAtDesc();
    }

}
