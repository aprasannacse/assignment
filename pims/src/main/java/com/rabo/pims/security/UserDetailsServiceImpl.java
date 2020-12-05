package com.rabo.pims.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rabo.pims.entity.User;
import com.rabo.pims.exception.UserNameNotFoundException;
import com.rabo.pims.repository.RolesRepository;
import com.rabo.pims.repository.UserRepository;
import com.rabo.pims.utils.Log4jManager;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	RolesRepository rolesRepo;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Log4jManager.writeDebugLog("Invoked UserDetailsServiceImpl Class:loadUserByUsername() and Input UserName is "+name);		
		
		User user = userRepo.findByName(name);		

		if(user == null) {
			throw new UserNameNotFoundException("User Name "+name);
		}
		Log4jManager.writeDebugLog("User Details "+user.getName());
		return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassowrd(),getGrantedAuthority(user));
	}
	private Collection<GrantedAuthority> getGrantedAuthority(User user){
		Log4jManager.writeDebugLog("UserDetailsServiceImpl Class:getGrantedAuthority() | Associated Role for the input UserName is "+user.getRoles().getName());
		Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		if(user.getRoles().getName().equalsIgnoreCase("admin")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		if(user.getRoles().getName().equalsIgnoreCase("user")) {
		authorities.add(new SimpleGrantedAuthority("ROLE_MAKER") );
		}
		return authorities;
	}

}
