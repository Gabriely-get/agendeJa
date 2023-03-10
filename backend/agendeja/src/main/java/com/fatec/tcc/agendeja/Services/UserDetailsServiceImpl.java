//package com.fatec.tcc.agendeja.Services;
//
//import com.fatec.tcc.agendeja.Entities.Role;
//import com.fatec.tcc.agendeja.Entities.User;
//import com.fatec.tcc.agendeja.Repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = userRepository.findByUsername(username);
//    if (user == null) {
//      throw new UsernameNotFoundException(username);
//    }
//    return new org.springframework.security.core.userdetails.User(
//        user.getUsername(), user.getPassword(), getAuthorities(user));
//  }
//
//  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
//    List<GrantedAuthority> authorities = new ArrayList<>();
//    for (Role role : user.getRoles()) {
//      authorities.add(new SimpleGrantedAuthority(role.getName()));
//    }
//    return authorities;
//  }
//
//}
