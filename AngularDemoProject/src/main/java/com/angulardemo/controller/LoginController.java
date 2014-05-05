package com.angulardemo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.angulardemo.controller.form.RegisterInfo;
import com.angulardemo.entity.User;
import com.angulardemo.service.AngularDemoBusinessDelegate;

@Controller
public class LoginController {

	@Resource  
	private AngularDemoBusinessDelegate angularDemoBusinessDelegate;  

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
           new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@ModelAttribute RegisterInfo registerInfo) {
		ModelAndView model = new ModelAndView();
		if(registerInfo != null) {
			if(registerInfo.getUsername() != null) {
				String message = new String();
				boolean hasErrors = false;
				if(registerInfo.getUsername().isEmpty()) {
					message += "User Name is required <BR>";
					hasErrors = true;
				}
				if(registerInfo.getEmail().isEmpty()) {
					message += "Email is required <BR>";
					hasErrors = true;
				}
				if(registerInfo.getPassword().isEmpty()) {
					message += "Password is required <BR>";
					hasErrors = true;
				}
				if(registerInfo.getRetypepassword().isEmpty()) {
					message += "Retype Password is required <BR>";
					hasErrors = true;
				}
				if(!registerInfo.getPassword().equals(registerInfo.getRetypepassword())) {
					message += "Password and Rety Password are not equals <BR>";
					hasErrors = true;
				}
				User user = angularDemoBusinessDelegate.getUser(registerInfo.getUsername());
				if(user != null) {
					message += "User Name already registered <BR>";
					hasErrors = true;
				} else {
					if(!hasErrors) {
						user = new User();
						user.setUserName(registerInfo.getUsername());
						user.setPassword(registerInfo.getPassword());
						user.setEmail(registerInfo.getEmail());
						angularDemoBusinessDelegate.addNewUser(user);
						model.addObject("msg", "User sucessful registered.");
						model.setViewName("login");
						return model;
					}
				}
				if(hasErrors) {
					model.addObject("error", message);
				}
			}
		}
		model.setViewName("register");
		return model;
	}
}
