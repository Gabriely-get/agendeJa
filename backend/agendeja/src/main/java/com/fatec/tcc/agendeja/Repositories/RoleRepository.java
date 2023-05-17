//package com.fatec.tcc.agendeja.Repositories;
//
//import com.fatec.tcc.agendeja.Entities.Role;
//import com.fatec.tcc.agendeja.Entities.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Set;
//
//@Repository
//public interface RoleRepository extends JpaRepository<Role, Long> {
//
//  Role findByName(String name);
//
////  @Query("Select ur from user_role ur where ur.user_id like %user_id%")
////  List<Role> findByUserId(Long user_id);
//
//}