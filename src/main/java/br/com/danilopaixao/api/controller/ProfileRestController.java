package br.com.danilopaixao.api.controller;

import java.util.List;
import br.com.danilopaixao.core.profile.*;
import br.com.danilopaixao.api.request.ProfileRequest;
import br.com.danilopaixao.api.response.ProfileResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileRestController {

	@Autowired
	private ProfileService service;

	@ApiOperation("EndPoint to get Profile by ID ")
	@GetMapping(value = "/api/v1/profiles/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ProfileResponse getProfile(
    		@PathVariable(value = "id", required = true) final Long id ) {
		return this.service.getById(id);
	}

	@ApiOperation("Endpoint to get ALL Profile")
	@GetMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<ProfileResponse> getAllProfiles() {
		return this.service.getByAllProfiles();
    }

	@ApiOperation("Endpoint to create new Profile")
	@PostMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ProfileResponse saveProfile(
    		@RequestBody(required = true) final ProfileRequest profileEntity) {
		return this.service.save(profileEntity);
    }

	@ApiOperation("Endpoint to update a Profile")
	@PostMapping(value = "/api/v1/profiles/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
	ProfileResponse upDateProfile(
    		@PathVariable(value = "id", required = true) final Long id ,
    		@RequestBody(required = true) final ProfileRequest profileEntity) {
		profileEntity.setId(id);
		return this.service.save(id, profileEntity);
    }

	@ApiOperation("Endpoint to inative a Profile")
	@DeleteMapping(value = "/api/v1/profiles/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ProfileResponse cancelProfile(
    		@PathVariable(value = "id", required = true) final Long id) {
		return this.service.inativeProfile(id);
    }


}

