package br.com.danilopaixao.ws.detail.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopaixao.ws.detail.DetailEntityRequest;
import br.com.danilopaixao.ws.detail.DetailEntityResponse;
import br.com.danilopaixao.ws.detail.DetailEntityService;
import io.swagger.annotations.ApiOperation;

@RestController
public class DetailEntityRestController {
	
	@Autowired
	private DetailEntityService service;
	
	@ApiOperation("EndPoint to get Detail by ID ")
	@GetMapping(value = "/api/v1/detail/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DetailEntityResponse getDetailEntity(
    		@PathVariable(value = "id", required = true) final Long id ) {
		return service.getById(id);
	}	

	@ApiOperation("Endpoint to get ALL Detail")
	@GetMapping(value = "/api/v1/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody List<DetailEntityResponse> getAllDetailEntity() {
		return service.getAllDetailEntity();
    }
	
	@ApiOperation("Endpoint to create new Detail")
	@PostMapping(value = "/api/v1/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody DetailEntityResponse saveDetailEntity(
    		@RequestBody(required = true) final DetailEntityRequest detail) {
		return service.save(detail);
    }
	
	@ApiOperation("Endpoint to update a Detail")
	@PostMapping(value = "/api/v1/detail/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody DetailEntityResponse upDateDetailEntity(
    		@PathVariable(value = "id", required = true) final Long id ,
    		@RequestBody(required = true) final DetailEntityRequest detail) {
		return service.save(id, detail);
    }
	

}