package com.angulardemo.dao;

import java.util.List;

import com.angulardemo.entity.ToDoItem;
import com.angulardemo.entity.User;
import com.angulardemo.utils.Status;

public interface ToDoItemDao {

	List<ToDoItem> loadUserItems(final User user, final Status status);
	
	void addItem(ToDoItem item);
	
	ToDoItem getItemById(Long id);
	
	void deleteItem(ToDoItem item);

	void updateItem(ToDoItem item);
}
