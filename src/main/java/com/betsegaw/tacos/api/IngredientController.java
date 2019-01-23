package com.betsegaw.tacos.api;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betsegaw.tacos.domains.Ingredient;
import com.betsegaw.tacos.services.IngredientService;



@RestController
@RequestMapping(path="/ingredients", produces="application/json")
@CrossOrigin(origins="*")
public class IngredientController {

  private IngredientService ingredientService;

  @Autowired
  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }

  @GetMapping
  public Iterable<Ingredient> allIngredients() {
    return ingredientService.findAll();
  }
  
  @GetMapping("/{id}")
  public Optional<Ingredient> byId(@PathVariable Long id) {
    return ingredientService.findById(id);
  }
  
  @PutMapping("/{id}")
  public void updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
    if (!ingredient.getId().equals(id)) {
      throw new IllegalStateException("Given ingredient's ID doesn't match the ID in the path.");
    }
    ingredientService.save(ingredient);
  }
  
  @PostMapping
  public ResponseEntity<Ingredient> postIngredient(@RequestBody Ingredient ingredient) {
    Ingredient saved = ingredientService.save(ingredient);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create("http://localhost:8080/ingredients/" + ingredient.getId()));
    return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
  }
  
  @DeleteMapping("/{id}")
  public void deleteIngredient(@PathVariable Long id) {
	  ingredientService.deleteById(id);
  }
  
}