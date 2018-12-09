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

import br.com.danilopaixao.ws.detail.DetailRequest;
import br.com.danilopaixao.ws.detail.DetailResponse;
import br.com.danilopaixao.ws.detail.DetailEntityService;
import io.swagger.annotations.ApiOperation;

@RestController
public class DetailRestController {
	
	@Autowired
	private DetailEntityService service;
	
	@ApiOperation("EndPoint to get Detail by ID ")
	@GetMapping(value = "/api/v1/details/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DetailResponse getDetail(
    		@PathVariable(value = "id", required = true) final Long id ) {
		return service.getById(id);
	}	

	@ApiOperation("Endpoint to get ALL Detail")
	@GetMapping(value = "/api/v1/details", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody List<DetailResponse> getAllDetail() {
		return service.getAllDetailEntity();
    }
	
	@ApiOperation("Endpoint to create new Detail")
	@PostMapping(value = "/api/v1/details", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody DetailResponse saveDetail(
    		@RequestBody(required = true) final DetailRequest detail) {
		return service.save(detail);
    }
	
	@ApiOperation("Endpoint to update a Detail")
	@PostMapping(value = "/api/v1/details/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody DetailResponse upDateDetail(
    		@PathVariable(value = "id", required = true) final Long id ,
    		@RequestBody(required = true) final DetailRequest detail) {
		return service.save(id, detail);
    }
	

}