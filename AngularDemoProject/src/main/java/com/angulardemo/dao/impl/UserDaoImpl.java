package com.angulardemo.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.angulardemo.dao.UserDao;
import com.angulardemo.entity.User;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	@Override
	public User loadUser(String username) throws UsernameNotFoundException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", username);
		return find(map);
	}

	@Override
	public void addUser(User user) {
		create(user);
	}

	@Override
	public User loadUser(String username, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", username);
		map.put("password", password);
		return find(map);
	}

}
