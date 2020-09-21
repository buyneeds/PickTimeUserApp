package com.picktime.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picktime.dao.User;
import com.picktime.exception.UserException;
import com.picktime.service.UserService;

@Controller
public class UserController {

	ObjectMapper objectMapper = new ObjectMapper();
       
    @Autowired
    UserService userService; 

    @RequestMapping(value = "/login")
    public String login(Model model) {
    	System.out.println("Login request");
        return "login";
    }
    
    
    @RequestMapping(value = "/signup")
    public String signup(Model model) {
    	System.out.println("Signup request");
        return "signup";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
    	System.out.println("Index Page request");
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }
    
    @RequestMapping(value = "/checkLogin")
    public String checkLogin(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String password,Model model,HttpSession session) {
    	System.out.println("Checking Login request");
    	
    	User user = userService.checkUser(firstname,lastname,password);
		if(user!=null && user.getId().length()>0){
			model.addAttribute("message", "Logged in successfully..");
			model.addAttribute("user", user);
			session.setAttribute("userId", user.getId());
			return "redirect:/show";
		}
		model.addAttribute("message", "Login failed..");
		model.addAttribute("users", userService.getAllUsers());
		return "index";
    }

    @RequestMapping(value = "/save")
    public String save(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String password,Model model,HttpSession session) {
    	System.out.println("Signup Save request");
    	User user = userService.saveUser(firstname,lastname,password);
    	if(user!=null && !user.getId().isEmpty()){
    		model.addAttribute("user", user);
            session.setAttribute("userId", user.getId());
            return "redirect:/show";
    	}else{
    		model.addAttribute("message", "Signup failed.. User already exist");
			model.addAttribute("users", userService.getAllUsers());
    		return "index";
    	}
    }

    @RequestMapping(value = "/show")
    public String show(Model model,HttpSession session) throws JsonProcessingException {
    	System.out.println("After signup/login request");
    	
    	try {
			String userId = (String) session.getAttribute("userId");
			Optional<User> optional = userService.getUser(userId);
			if(optional.isPresent()){
				model.addAttribute("message", "Logged in successfully..");
			    model.addAttribute("user", optional.get());
			    return "dashboard";
			}
		} catch (Exception e) {
			new UserException(e);
		}
    	session.setAttribute("userId",null);
    	model.addAttribute("message", "Invalid Session..");
    	return "redirect:/index";
    }
    
    @RequestMapping(value = "/logout/{id}")
    public String logout(@PathVariable String id, Model model,HttpSession session) {
    	System.out.println("Logout request");
    	session.setAttribute("userId",null);
    	model.addAttribute("user", null);
        return "redirect:/index";
    }
}
