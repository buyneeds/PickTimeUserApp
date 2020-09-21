package com.picktime.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picktime.dao.User;
import com.picktime.repository.UserRepository;

@Service
public class UserService {

	 @Autowired
	 UserRepository userRepository;

	public Object getAllUsers() {
		return userRepository.findAll();
	}

	public User checkUser(String firstname, String lastname, String password) {
		
		Optional<User> optional = userRepository.findByFirstnameAndLastname(firstname, lastname);
    	if(optional.isPresent()){
    		User user = optional.get();
    		if(user.getPassword().equals(password)){
        		return user;
    		}
    	}
		return null;
	}

	public User saveUser(String firstname, String lastname, String password) {
		Optional<User> optional = userRepository.findByFirstnameAndLastname(firstname, lastname);
    	if(!optional.isPresent()){
    		
    		User user = new User(firstname, lastname, password);
            userRepository.save(user);
            return user;
    			
    	}
		return null;
	}

	public Optional<User> getUser(String userId) {
		if(userRepository.findById(userId).isPresent()){
			return userRepository.findById(userId);
		}
		return null;
		
	}
}
