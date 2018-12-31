package com.betsegaw.tacos.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.betsegaw.tacos.repositories.IngredientRepository;
import com.betsegaw.tacos.repositories.TacoRepository;
import com.betsegaw.tacos.security.User;
import com.betsegaw.tacos.services.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesginTacoController {
	
	private IngredientRepository ingredientRepository;
	private TacoRepository tacoRepository;
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	public DesginTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
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
	public UserDetails user(Principal principal) {
		String username = principal.getName();
		User user = (User) userDetailsServiceImpl.loadUserByUsername(username);
		return user;
	}
	
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll()
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
	public String processDesign(@Valid Taco tacoDesign, Errors errors, Order order) {
		
		if(errors.hasErrors()) {
			return "design";
		}
		
		Taco savedTaco = tacoRepository.save(tacoDesign);
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
