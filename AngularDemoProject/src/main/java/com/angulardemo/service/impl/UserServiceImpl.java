package com.angulardemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.angulardemo.dao.UserDao;
import com.angulardemo.entity.User;
import com.angulardemo.security.UserSecurity;
import com.angulardemo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserSecurity loadUserByUsername(final String username) throws UsernameNotFoundException {
		User user = userDao.loadUser(username);
		UserSecurity us = new UserSecurity();
		us.fromUser(user);
		return us;
	}
}