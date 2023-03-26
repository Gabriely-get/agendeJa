package com.fatec.tcc.agendeja.Repositories;

import com.fatec.tcc.agendeja.Entities.User;
import jakarta.persistence.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByCpf(String cpf);
    boolean existsUserByEmail(String email);
    boolean existsUserByPhone(String phone);
    Optional<User> findUserByEmail(String email);
    List<User> findAllByUsernameContains(String word);

    @Query("Select u from User u where cast(u.birthday as string) like %:date%")
    List<User> findAllByBirthdayContains(String date);

    @Query("Select u from User u where cast(u.birthday as string) like %:date% order by u.birthday asc")
    List<User> findAllByBirthdayContainsOrderByAsc(String date);

    @Query("Select u from User u where cast(u.birthday as string) like %:date% order by u.birthday desc")
    List<User> findAllByBirthdayContainsOrderByDesc(String date);

    @Query("Select u from User u where u.username like %:word% order by u.username asc")
    List<User> findAllByUsernameContainsOrderByAsc(String word);

    @Query("Select u from User u where u.username like %:word% order by u.username desc")
    List<User> findAllByUsernameContainsOrderByDesc(String word);

}