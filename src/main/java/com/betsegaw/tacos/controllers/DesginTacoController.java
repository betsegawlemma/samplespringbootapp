package com.betsegaw.tacos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.betsegaw.tacos.domains.Ingredient;
import com.betsegaw.tacos.domains.Order;
import com.betsegaw.tacos.domains.Taco;
import com.betsegaw.tacos.domains.Ingredient.Type;
import com.betsegaw.tacos.security.User;
import com.betsegaw.tacos.services.IngredientService;
import com.betsegaw.tacos.services.TacoService;
import com.betsegaw.tacos.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesginTacoController {
	
	private IngredientService ingredientService;
	private TacoService tacoService;
	private UserService userService;
	
	@Autowired
	public DesginTacoController(IngredientService ingredientService, TacoService tacoService,
			UserService userService) {
		this.ingredientService = ingredientService;
		this.tacoService = tacoService;
		this.userService = userService;
	}
	
	@ModelAttribute(name="order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name="tacoDesign")
	public Taco tacoDesign() {
		return new Taco();
	}
	
	@ModelAttribute(name="user")
	public UserDetails user(@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		User user = userService.findUserByUsername(username);
		return user;
	}
	
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientService.findAll()
							.forEach(i->ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		
		for (Type type: types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}
	
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	@PostMapping
	public String processDesign(@Valid @ModelAttribute ("tacoDesign") Taco tacoDesign, Errors errors, Order order) {
		
		if(errors.hasErrors()) {
			return "design";
		}
		
		Taco savedTaco = tacoService.save(tacoDesign);
		order.addTacoDesign(savedTaco);
		log.info("Taco object after persisting: " + savedTaco);
		
		return "redirect:/orders/current";
		
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
		return ingredients
				.stream()
				.filter(x->x.getType().equals(type))
				.collect(Collectors.toList());
	}

}
