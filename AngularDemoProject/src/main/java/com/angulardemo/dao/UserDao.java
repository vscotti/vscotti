package com.angulardemo.dao;

import com.angulardemo.entity.User;

public interface UserDao {

	User loadUser(final String username);
	
	User loadUser(final String username, final String password);
	
	void addUser(User user);
}
