package com.angulardemo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.angulardemo.entity.User;


public class UserSecurity implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	private List<Role> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	@Override
	public String getUsername() {
		return this.userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void fromUser(User user) {
		if(user != null) {
			this.userName = user.getUserName();
			this.password = user.getPassword();
			
			Role r = new Role();
			r.setName("ROLE_USER");
			List<Role> roles = new ArrayList<Role>();
			roles.add(r);
			authorities = roles;
		}
	}
		
}
