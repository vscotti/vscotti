package com.angulardemo.service;

import java.util.List;

import com.angulardemo.entity.ToDoItem;
import com.angulardemo.entity.User;

public interface AngularDemoBusinessDelegate {

	void addNewUser(User user);
	
	User getUser(String userName);

	User getUser(String userName, String password);

	List<ToDoItem> getUsersPendingItems(User user);

	List<ToDoItem> getUsersCompletedItems(User user);

	void addItem(ToDoItem item);

	ToDoItem getItem(Long id);

	void deleteItem(Long itemid);

	void updateItem(ToDoItem item);
}
