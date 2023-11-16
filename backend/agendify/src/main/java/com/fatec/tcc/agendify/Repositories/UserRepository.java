package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.Role;
import com.fatec.tcc.agendify.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByCpf(String cpf);
    boolean existsUserByEmail(String email);
    boolean existsUserByPhone(String phone);
    Optional<User> findUserByEmail(String email);

    @Query("Select u from User u where u.role = :roleType")
    List<User> findAllByRole_Value(Role roleType);

    @Query("Select u from User u order by concat(u.firstName, ' ', u.lastName) asc")
    List<User> getAllOrderNameByAsc();

    @Query("Select u from User u order by concat(u.firstName, ' ', u.lastName) desc")
    List<User> getAllOrderNameByDesc();

    @Query(value = "Select u from User u order by concat(u.firstName, ' ', u.lastName) asc")
    Page<User> getAllOrderNameByAscPageable(Pageable pageable);
    @Query(value = "Select u from User u order by concat(u.firstName, ' ', u.lastName) desc")
    Page<User> getAllOrderNameByDescPageable(Pageable pageable);

    @Query(value = "Select u from User u order by u.birthday asc")
    Page<User> getAllOrderBirthdayByAscPageable(Pageable pageable);
    @Query(value = "Select u from User u order by u.birthday desc")
    Page<User> getAllOrderBirthdayByDescPageable(Pageable pageable);

//    @Query("Select u from User u order by concat(u.firstName, ' ', u.lastName) desc limit 10 offset 5")
//    List<User> getAllOrderByDesc();


    @Query("Select u from User u where u.firstName like %:word% or u.lastName like %:word%")
    List<User> findAllByFullusernameContains(String word);

    @Query("Select u from User u where cast(u.birthday as string) like %:date%")
    List<User> findAllByBirthdayContains(String date);

    @Query("Select u from User u where cast(u.birthday as string) like %:date% order by u.birthday asc")
    List<User> findAllByBirthdayContainsOrderByAsc(String date);

    @Query("Select u from User u where cast(u.birthday as string) like %:date% order by u.birthday desc")
    List<User> findAllByBirthdayContainsOrderByDesc(String date);

    @Query("Select u from User u where u.firstName like %:word% or u.lastName like %:word% order by concat(u.firstName,' ',u.lastName) asc")
    List<User> getAllFullusernameContainsOrderByAsc(String word);

    @Query("Select u from User u where u.firstName like %:word% or u.lastName like %:word% order by concat(u.firstName,' ',u.lastName) desc")
    List<User> findAllByFullusernameContainsOrderByDesc(String word);

    @Query("Select u from User u order by u.createAt asc")
    List<User> findAllOrderByCreateAtAsc();

    @Query("Select u from User u order by u.createAt desc")
    List<User> findAllOrderByCreateAtDesc();

}