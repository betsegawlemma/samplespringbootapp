package com.betsegaw.tacos.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.betsegaw.tacos.Order;
import com.betsegaw.tacos.Taco;
import com.betsegaw.tacos.repositories.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderRepository orderRepository;
	
	@Autowired
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@GetMapping("/current")
	public String orderForm() {
		return "order_form";
	}
	
	@PostMapping
	public String processOrder(
			@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		
		if(errors.hasErrors()) {
			return "order_form";
		}
		Order savedOrder = orderRepository.save(order);
		log.info("Order submitted: " + savedOrder);
		sessionStatus.setComplete();
		return "redirect:/";
	}

}
