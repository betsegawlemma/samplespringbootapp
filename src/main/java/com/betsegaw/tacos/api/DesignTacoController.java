package com.betsegaw.tacos.api;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.betsegaw.tacos.domains.Taco;
import com.betsegaw.tacos.services.TacoService;

@RestController
@RequestMapping(path="/design", produces="application/json")
public class DesignTacoController {
	
	private TacoService tacoService;
	
	public DesignTacoController(TacoService tacoService) {
		this.tacoService = tacoService;
	}
	
	@GetMapping("/recent")
	public Iterable<Taco> recentTacos(@RequestParam(name="page",defaultValue="0") int page, @RequestParam(name="size", defaultValue="2") int size) {
		
		PageRequest pageRequest = PageRequest.of(page,size,Sort.by("createdAt").descending());
		return tacoService.findAll(pageRequest).getContent();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoService.findById(id);
		if(optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco saveTaco(@RequestBody Taco taco) {
		return tacoService.save(taco);
	}
	
	@PutMapping(path="/{tacoId}", consumes="application/json")
	public Taco wholesaleTacoUpdate(@RequestBody Taco taco) {
		return tacoService.save(taco);
	}
	
	@PatchMapping(path="/{tacoId}", consumes="application/json")
	public Taco partialTacoUpdate(@PathVariable("tacoId") Long id, @RequestBody Taco partialTaco) {
		
		Taco taco = tacoService.findById(id).get();
		
		if(taco != null) {
			if(partialTaco.getName()!= null) {
				taco.setName(partialTaco.getName());
			}
			
			if(partialTaco.getCreatedAt()!=null) {
				taco.setCreatedAt(partialTaco.getCreatedAt());
			}
			
			if(!partialTaco.getIngredients().isEmpty()) {
				taco.setIngredients(partialTaco.getIngredients());
			}
		}
		
		return tacoService.save(taco);
	}
	
	@DeleteMapping(path="/{tacoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTaco(@PathVariable("tacoId") Long id) {
		try {
			tacoService.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			
		}
	}

}
