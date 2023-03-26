package com.fatec.tcc.agendeja.Services;

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

    public List<User> findAllPageable(int offset, int limit) {
        int findAllSize = this.userRepository.findAll().size();
        if (findAllSize == 0 || offset*limit > findAllSize) return new ArrayList<>();

        Pageable usersPageable = PageRequest.of(offset,limit);
        Page<User> usersPage = this.userRepository.findAll(usersPageable);

        return usersPage.toList();
    }

    public List<User> sortByUsernameAsc() {
        return this.userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }

    public List<User> sortByUsernameDesc() {
        return this.userRepository.findAll(Sort.by(Sort.Direction.DESC, "username"));
    }

    public List<User> sortByBirthdayAsc() {
        return this.userRepository.findAll(Sort.by(Sort.Direction.ASC, "birthday"));
    }

    public List<User> sortByBirthdayDesc() {
        return this.userRepository.findAll(Sort.by(Sort.Direction.DESC, "birthday"));
    }

    public List<User> searchUsersByUsernameContains(String word) {
        return this.userRepository.findAllByUsernameContains(word);
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

        return this.userRepository.findAllByUsernameContainsOrderByAsc(word);
    }

    public List<User> searchUsersByUsernameContainsOrderByDesc(String word) {
        return this.userRepository.findAllByUsernameContainsOrderByDesc(word);
    }

}
