//package com.fatec.tcc.agendeja.Entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public @Data class Role {
//
//  public Role() {
//
//  }
//
//  public Role(String roleName) {
//    this.setName(roleName);
//  }
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @Column(nullable = false, unique = true)
//  private String name;
//
////  @ManyToMany(fetch = FetchType.EAGER, cascade = {
////          CascadeType.ALL})
////  @JoinTable(
////          name = "user_role",
////          joinColumns = @JoinColumn(
////                  name = "user_id", referencedColumnName = "id"),
////          inverseJoinColumns = @JoinColumn(
////                  name = "role_id", referencedColumnName = "id"))
//
////  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
////  private List<User> users = new ArrayList<>();
//
//}