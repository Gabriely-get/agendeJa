package com.fatec.tcc.agendeja.Repositories;

import com.fatec.tcc.agendeja.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(String name);

}