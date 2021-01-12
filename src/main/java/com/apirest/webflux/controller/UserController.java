package com.apirest.webflux.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.webflux.document.User;
import com.apirest.webflux.services.UserServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@GetMapping()
	public Flux<User> getUser(){
		return userServiceImpl.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<User> getUserId(@PathVariable String id){
		return userServiceImpl.findById(id)
				.switchIfEmpty(monoResponseStatusNotFoundException());
	}
	
	@PostMapping
	public Mono<User> save(@RequestBody User user){
		PasswordEncoder passEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
	    List<String> rolesArray = user.getRolesArray();
		user.setPassword(passEncoder.encode(user.getPassword()));
		for (int i = 0; i < rolesArray.size(); i++) {
			roles.add(new SimpleGrantedAuthority(rolesArray.get(i)));
		}
		user.setRoles(roles);
		user.deleteJson();
		return userServiceImpl.save(user);
	}
	
	public <T> Mono<T> monoResponseStatusNotFoundException(){
		return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
	
}
