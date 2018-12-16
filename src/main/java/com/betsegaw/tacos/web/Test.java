package com.betsegaw.tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/test")
@SessionAttributes("testmsg")
public class Test {
	
	@ModelAttribute("testmsg")
	String testMessage() {
		return "This is test message";
	}
	
	@GetMapping
	String showTest(Model model) {
		return "test";
	}

}
