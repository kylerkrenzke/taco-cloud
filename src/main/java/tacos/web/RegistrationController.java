package tacos.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import tacos.data.TacoCloudUserRepository;
import tacos.security.Registration;

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
