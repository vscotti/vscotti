package com.angulardemo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.angulardemo.security.UserSecurity;

public interface UserService {

	UserSecurity loadUserByUsername(final String username) throws UsernameNotFoundException;
}