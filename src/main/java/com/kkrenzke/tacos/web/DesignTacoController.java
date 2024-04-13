package com.kkrenzke.tacos.web;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kkrenzke.tacos.Ingredient;
import com.kkrenzke.tacos.Taco;
import com.kkrenzke.tacos.TacoOrder;
import com.kkrenzke.tacos.Ingredient.Type;
import com.kkrenzke.tacos.data.IngredientRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
  private final IngredientRepository ingredientRepository;

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    List<Ingredient> ingredients = ingredientRepository.findAll();
    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @GetMapping
  public String showDesignForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
    log.info("Processing taco: {}", taco);
    if (errors.hasErrors()) {
      return "design";
    }
    tacoOrder.addTaco(taco);
    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
    return ingredients.stream()
        .filter(i -> i.type().equals(type))
        .collect(Collectors.toList());
  }
}
