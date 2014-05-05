package com.angulardemo.controller.rest;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.angulardemo.entity.User;
import com.angulardemo.service.AngularDemoBusinessDelegate;

@Controller
@RequestMapping("/restnosecurity")
public class ToDoItemServiceNoSecurityController {

	@Resource  
	private AngularDemoBusinessDelegate angularDemoBusinessDelegate;  

    @RequestMapping(value = { "/getUser" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getUser(@RequestParam(value = "username", required=true) String username) {
		User user = angularDemoBusinessDelegate.getUser(username);
		if(user != null) {
			user.setId(null);
			user.setUserName(null);
			user.setPassword(null);
			user.setActive(false);
		}
        return user;
    }

    @RequestMapping(value = { "/getUserForLogin" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getUser(@RequestParam(value = "username", required=true) String username,
    								  @RequestParam(value = "password", required=true) String password) {
		User user = angularDemoBusinessDelegate.getUser(username, password);
		if(user != null) {
			user.setId(null);
			user.setUserName(null);
			user.setPassword(null);
			user.setActive(false);
		}
        return user;
    }
}
