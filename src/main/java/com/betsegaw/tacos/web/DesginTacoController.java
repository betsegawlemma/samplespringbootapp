package com.betsegaw.tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.betsegaw.tacos.Ingredient;
import com.betsegaw.tacos.Ingredient.Type;
import com.betsegaw.tacos.Order;
import com.betsegaw.tacos.Taco;
import com.betsegaw.tacos.repositories.IngredientRepository;
import com.betsegaw.tacos.repositories.TacoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesginTacoController {
	
	private IngredientRepository ingredientRepository;
	private TacoRepository tacoRepository;
	
	@Autowired
	public DesginTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
		
	}
	
	@ModelAttribute(name="order")
	public Order order(Model model) {
		return new Order();
	}
	
	@ModelAttribute(name="tacoDesign")
	public Taco tacoDesign() {
		return new Taco();
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
