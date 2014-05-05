package com.angulardemo.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.angulardemo.dao.ToDoItemDao;
import com.angulardemo.entity.ToDoItem;
import com.angulardemo.entity.User;
import com.angulardemo.utils.Status;

@Repository
public class ToDoItemDaoImpl extends AbstractDao<ToDoItem> implements ToDoItemDao {

	@Override
	public List<ToDoItem> loadUserItems(final User user, final Status status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("status", status.ordinal());
		return findAll(map);
	}

	@Override
	public void addItem(ToDoItem item) {
		create(item);
	}

	@Override
	public void deleteItem(ToDoItem item) {
		remove(item);
	}

	@Override
	public void updateItem(ToDoItem item) {
		edit(item);
	}

	@Override
	public ToDoItem getItemById(Long id) {
		return find(id);
	}

}
