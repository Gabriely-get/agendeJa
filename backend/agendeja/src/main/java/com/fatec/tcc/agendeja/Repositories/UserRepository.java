package com.fatec.tcc.agendeja.Repositories;

import com.fatec.tcc.agendeja.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsUserByEmailAndPassword(String email, String password);

    boolean existsUserByCpf(String cpf);

    boolean existsUserByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByEmail(String email);
}