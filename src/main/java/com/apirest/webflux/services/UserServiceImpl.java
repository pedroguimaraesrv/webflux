package com.apirest.webflux.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.webflux.document.User;
import com.apirest.webflux.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements ReactiveUserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Mono<UserDetails> findByUsername(String username){
		return userRepository.findByUsername(username)
				.cast(UserDetails.class);
	}
	
	public Flux<User> findAll() {
		return userRepository.findAll();
	}

	public Mono<User> findById(String id) {
		return userRepository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
	}

	public Mono<User> save(User user) {
		return userRepository.save(user);
	}
	
	
}
