package com.angulardemo.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.angulardemo.entity.Priority;
import com.angulardemo.entity.ToDoItem;
import com.angulardemo.entity.User;
import com.angulardemo.service.AngularDemoBusinessDelegate;
import com.angulardemo.utils.Priorities;
import com.angulardemo.utils.Status;

@Controller
@RequestMapping("/rest")
public class ToDoItemServiceController {

	@Resource  
	private AngularDemoBusinessDelegate angularDemoBusinessDelegate;  

    @RequestMapping(value = { "/getUser" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getUser(@RequestParam(value = "username", required=true) String username) {
		User user = angularDemoBusinessDelegate.getUser(username);
        return user;
    }
    
    @RequestMapping(value = { "/getItem" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ToDoItem getUser(@RequestParam(value = "id", required=true) Long id) {
    	ToDoItem toDoItem = angularDemoBusinessDelegate.getItem(id);
        return toDoItem;
    }
    
    @RequestMapping(value = { "/getItemsCompleted" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<ToDoItem> getItemsCompleted() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName(); 
		User currentUser = angularDemoBusinessDelegate.getUser(userName);
		List<ToDoItem> items = angularDemoBusinessDelegate.getUsersCompletedItems(currentUser);
        return items;
    }
	
    @RequestMapping(value = { "/getAllItems" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<ToDoItem> getAllItems() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName(); 
		User currentUser = angularDemoBusinessDelegate.getUser(userName);
		List<ToDoItem> items = angularDemoBusinessDelegate.getUsersPendingItems(currentUser);
        return items;
    }

    
    @RequestMapping(value = { "/getLoggedUser" }, method = RequestMethod.GET)
    public @ResponseBody User getLoggedUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName(); 
		User currentUser = angularDemoBusinessDelegate.getUser(userName);
		return currentUser;
    }

    @RequestMapping(value = { "/addNewItem" }, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ToDoItem> addNewItem(@RequestParam(value = "desc", required=true) String description, 
															 @RequestParam(value = "prior", required=true) String priority, 
															 @RequestParam(value = "dueDate", required=true) Long dueDate) throws Exception {
		ToDoItem item = null;
		try {
			if((description != null && !description.isEmpty()) &&
					(priority != null && Integer.valueOf(priority) >= 0 && Integer.valueOf(priority) <= 3)) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String userName = auth.getName(); 
				User currentUser = angularDemoBusinessDelegate.getUser(userName);
				item = new ToDoItem();
				item.setDescription(description);
				item.setPriority(Integer.valueOf(priority));
				item.setStatus(Status.OPEN.ordinal());
				item.setUser(currentUser);
				item.setCreationDate(new Date().getTime());
				item.setDueDate(dueDate);
				angularDemoBusinessDelegate.addItem(item);
			} else {
				return new ResponseEntity<ToDoItem>(item, HttpStatus.BAD_REQUEST);
			}
		} catch(NumberFormatException e) {
			return new ResponseEntity<ToDoItem>(item, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ToDoItem>(item, HttpStatus.OK);
    }
    
    @RequestMapping(value = { "/modifyItem" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<ToDoItem> modifyItem(@RequestParam(value="id", required=true) Long id,
			 								 @RequestParam(value="desc", required=false) String description,
			 								 @RequestParam(value="priority", required=false) Integer priority,
			 								 @RequestParam(value="status", required=false) Integer status, 
											 @RequestParam(value = "dueDate", required=false) Long dueDate) {
    	ToDoItem toDoItem = null;
    	if((description != null && !description.isEmpty()) ||
    			(priority != null && priority >= Priorities.CRITICAL.ordinal() && priority <= Priorities.LOW.ordinal()) ||
    			(status != null && status >= Status.COMPLETED.ordinal() && status <= Status.OPEN.ordinal())) {
	    	toDoItem = angularDemoBusinessDelegate.getItem(id);
	    	if(description != null &&
	    			!description.isEmpty()) {
	    		toDoItem.setDescription(description);
	    	}
	    	if(priority != null && priority >= Priorities.CRITICAL.ordinal() && priority <= Priorities.LOW.ordinal()) {
	    		toDoItem.setPriority(priority);
	    	}
	    	if(status != null && status >= Status.COMPLETED.ordinal() && status <= Status.OPEN.ordinal()) {
	    		toDoItem.setStatus(status);
	    	}
	    	if(dueDate != null) {
	    		toDoItem.setDueDate(dueDate);
	    	}
			angularDemoBusinessDelegate.updateItem(toDoItem);
    	} else {
        	return new ResponseEntity<ToDoItem>(toDoItem, HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<ToDoItem>(toDoItem, HttpStatus.OK);
    }
    
    @RequestMapping(value = { "/deleteItem" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteItem(@RequestParam(value="id", required=true) Long id) {
		angularDemoBusinessDelegate.deleteItem(id);
		return new ResponseEntity<String>("Item deleted.", HttpStatus.OK);
    }

    @RequestMapping(value = { "/getPriorities" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Priority> getPriorities() {
    	Priorities[] p = Priorities.values();
    	List<Priority> list = new ArrayList<>();
    	for (Priorities priority : p) {
			list.add(new Priority((long)priority.ordinal(), priority.name()));
		}
    	return list;
    }

    @RequestMapping(value = { "/getStatuses" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<com.angulardemo.entity.Status> getStatuses() {
    	Status[] p = Status.values();
    	List<com.angulardemo.entity.Status> list = new ArrayList<>();
    	for (Status status : p) {
			list.add(new com.angulardemo.entity.Status((long)status.ordinal(), status.name()));
		}
    	return list;
    }
}
