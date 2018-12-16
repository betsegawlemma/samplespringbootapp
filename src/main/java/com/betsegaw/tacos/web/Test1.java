package com.betsegaw.tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/test1")
@SessionAttributes("testmsg")
public class Test1 {

	@GetMapping
	String showTest1(String testmsg) {
		log.info(testmsg);
		return "test";
	}
}
