package br.com.danilopaixao.api.controller;

import br.com.danilopaixao.api.request.UserRequest;
import br.com.danilopaixao.api.response.UserResponse;
import br.com.danilopaixao.core.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * @author user
 *
 */
@RestController
public class UserRestController {
	
	@Autowired
	private UserService service;

	@ApiOperation("EndPoint to get User by ID ")
	@GetMapping(value = "/api/v1/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    UserResponse getUser(
    		@PathVariable(value = "id", required = true) final Long id ) {
		return this.service.getById(id);
	}	
	
	@PreAuthorize("hasRole('ROLE_AD')")
	@ApiOperation("Endpoint to get ALL User")
	@GetMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<UserResponse> getAllUsers() {
		return this.service.getByAllUsers();
    }
	
	@ApiOperation("Endpoint to create new User")
	@PostMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody UserResponse saveUser(
    		@RequestBody(required = true) final UserRequest user) {
		return this.service.save(user);
    }
	
	@ApiOperation("Endpoint to update a User")
	@PostMapping(value = "/api/v1/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserResponse upDateUser(
    		@PathVariable(value = "id", required = true) final Long id ,
    		@RequestBody(required = true) final UserRequest user) {
		user.setId(id);
		return this.service.save(id, user);
    }
	
	@ApiOperation("Endpoint to inative a User")
	@DeleteMapping(value = "/api/v1/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserResponse cancelUser(
    		@PathVariable(value = "id", required = true) final Long id) {
		return this.service.inativeUser(id);
    }
	

}