package com.kkrenzke.tacos.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    List<UserDetails> userDetails = new ArrayList<>();
    userDetails
        .add(new User("buzz", passwordEncoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_USER"))));
    userDetails
        .add(new User("woody", passwordEncoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_USER"))));
    return new InMemoryUserDetailsManager(userDetails);
  }
}
