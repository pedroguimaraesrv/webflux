package com.apirest.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.webflux.document.User;
import com.apirest.webflux.services.UserServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping(value= "/users")
	public Flux<User> getUser(){
		return userService.findAll();
	}
	
	@GetMapping(value="/users/{id}")
	public Mono<User> getUserId(@PathVariable String id){
		return userService.findById(id);
	}
	
	@PostMapping(value="/users")
	public Mono<User> save(@RequestBody User user){
		return userService.save(user);
	}
	
}
