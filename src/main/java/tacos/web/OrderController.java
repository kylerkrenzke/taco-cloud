package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tacos.TacoOrder;
import tacos.data.OrderRepository;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
  private final OrderRepository orderRepository;

  @GetMapping("/current")
  public String orderForm() {
    return "order-form";
  }

  @PostMapping
  public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus status) {
    log.info("Order submitted: {}", order);
    if (errors.hasErrors()) {
      log.info("Order contains errors");
      return "order-form";
    }
    orderRepository.save(order);
    status.setComplete();
    return "redirect:/";
  }
}
