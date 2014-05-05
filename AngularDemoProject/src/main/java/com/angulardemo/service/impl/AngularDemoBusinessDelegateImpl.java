package com.angulardemo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angulardemo.dao.ToDoItemDao;
import com.angulardemo.dao.UserDao;
import com.angulardemo.entity.ToDoItem;
import com.angulardemo.entity.User;
import com.angulardemo.service.AngularDemoBusinessDelegate;
import com.angulardemo.utils.Status;

@Service
@Transactional
public class AngularDemoBusinessDelegateImpl implements AngularDemoBusinessDelegate {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ToDoItemDao toDoItemDao;
	
	@Override
	public void addNewUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public User getUser(String userName) {
		return userDao.loadUser(userName);
	}

	@Override
	public List<ToDoItem> getUsersPendingItems(User user) {
		return toDoItemDao.loadUserItems(user, Status.OPEN);
	}

	@Override
	public List<ToDoItem> getUsersCompletedItems(User user) {
		return toDoItemDao.loadUserItems(user, Status.COMPLETED);
	}

	@Override
	public void addItem(ToDoItem item) {
		toDoItemDao.addItem(item);
	}

	@Override
	public void deleteItem(Long itemid) {
		ToDoItem item = getItem(itemid);
		toDoItemDao.deleteItem(item);
	}

	@Override
	public void updateItem(ToDoItem item) {
		toDoItemDao.updateItem(item);
	}

	@Override
	public ToDoItem getItem(Long id) {
		return toDoItemDao.getItemById(id);
	}

	@Override
	public User getUser(String userName, String password) {
		return userDao.loadUser(userName, password);
	}
}
 