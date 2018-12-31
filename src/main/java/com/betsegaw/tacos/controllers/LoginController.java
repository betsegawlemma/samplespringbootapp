package com.betsegaw.tacos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.betsegaw.tacos.security.User;
import com.betsegaw.tacos.services.UserDetailsServiceImpl;

@Controller
public class LoginController {
	
	@Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@ModelAttribute(name="user")
	public User user() {
		return new User();
	}

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        User userExists = userDetailsServiceImpl.findUserByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("user", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
        	
            userDetailsServiceImpl.saveUser(user);
            
            model.addAttribute("successMessage", "User has been registered successfully");
            
            return "registration";
        }
    }
    
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "access_denied";
    }

}
