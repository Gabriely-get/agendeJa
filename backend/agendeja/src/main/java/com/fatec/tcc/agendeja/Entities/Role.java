package com.fatec.tcc.agendeja.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

}