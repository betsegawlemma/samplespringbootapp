package com.betsegaw.tacos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.betsegaw.tacos.domains.Order;
import com.betsegaw.tacos.security.User;
import com.betsegaw.tacos.services.OrderService;
import com.betsegaw.tacos.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	private OrderService orderService;
	private UserService userService;

	@Autowired
	public OrderController(OrderService orderService, UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	@GetMapping("/current")
	public String orderForm(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Order order) {

		User user = userService.findUserByUsername(userDetails.getUsername());
		
		log.info("Order submited by: " + user.getUsername());

		
		if (order.getFirstName() == null) {
			order.setFirstName(user.getFirstName());
		}
		
		if (order.getLastName() == null) {
			order.setLastName(user.getLastName());
		}
		
		if (order.getDeliveryStreet() == null) {
			order.setDeliveryStreet(user.getDeliveryStreet());
		}
		
		if (order.getDeliveryCity() == null) {
			order.setDeliveryCity(user.getDeliveryCity());
		}
		
		if (order.getDeliveryState() == null) {
			order.setDeliveryState(user.getDeliveryState());
		}
		
		if (order.getDeliveryZip() == null) {
			order.setDeliveryZip(user.getDeliveryZip());
		}

		return "order_form";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, 
			SessionStatus sessionStatus, @AuthenticationPrincipal User user) {

		if (errors.hasErrors()) {
			return "order_form";
		}
		
		order.setUser(user);
		
		Order savedOrder = orderService.save(order);
		log.info("Order submitted: " + savedOrder);
		sessionStatus.setComplete();
		return "redirect:/";
	}

}
