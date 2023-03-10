package com.fatec.tcc.agendeja.Repositories;

import com.fatec.tcc.agendeja.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(String name);

}