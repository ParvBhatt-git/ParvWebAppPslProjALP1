package com.psl.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.psl.entity.UserEntity;
import com.psl.repository.IUserEntityRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	IUserEntityRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
	
		UserEntity entity = repo.findById(username).orElseThrow(()->  new UsernameNotFoundException("User Not found by UserName : "+username));
		
		return new User(entity.getUserName(),  "{noop}"+entity.getPassword() , new ArrayList<>());		
	}
}
