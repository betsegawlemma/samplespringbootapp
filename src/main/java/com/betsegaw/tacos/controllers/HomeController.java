package com.betsegaw.tacos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller        
public class HomeController {
  
  @GetMapping("/")
  public String home() {
	 // throw new RuntimeException("Test Exception");
    return "home";    
  }
  
  @GetMapping("/testsec")
  public String testSec() {
    return "testsec";    
  }

}
