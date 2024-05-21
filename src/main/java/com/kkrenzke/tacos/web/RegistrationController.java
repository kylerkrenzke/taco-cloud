package com.kkrenzke.tacos.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkrenzke.tacos.data.TacoCloudUserRepository;
import com.kkrenzke.tacos.security.Registration;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
  private final TacoCloudUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public String registerForm() {
    return "registration";
  }

  @PostMapping
  public String processRegistration(Registration form) {
    userRepository.save(form.toUser(passwordEncoder));
    return "redirect:/login";
  }
}
