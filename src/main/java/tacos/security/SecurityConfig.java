package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.TacoCloudUser;
import tacos.data.TacoCloudUserRepository;

@Configuration
public class SecurityConfig {
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(TacoCloudUserRepository userRepository) {
    return username -> {
      TacoCloudUser user = userRepository.findByUsername(username);
      if (user != null) {
        return user;
      }

      throw new UsernameNotFoundException("User " + username + " not found");
    };
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .authorizeRequests()
        .requestMatchers("/design", "/orders").hasRole("USER")
        .requestMatchers("/", "/**").permitAll()
        .and()
        .formLogin().loginPage("/login")
        .and()
        .build();
  }
}
