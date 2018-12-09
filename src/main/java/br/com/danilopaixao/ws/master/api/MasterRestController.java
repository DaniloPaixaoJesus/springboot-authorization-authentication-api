package br.com.danilopaixao.ws.master.api;

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

import br.com.danilopaixao.ws.master.MasterRequest;
import br.com.danilopaixao.ws.master.MasterResponse;
import br.com.danilopaixao.ws.master.MasterEntityService;
import io.swagger.annotations.ApiOperation;

@RestController
public class MasterRestController {
	
	@Autowired
	private MasterEntityService service;
	
	@ApiOperation("EndPoint to get Master by ID ")
	@GetMapping(value = "/api/v1/masters/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody MasterResponse getMaster(
    		@PathVariable(value = "id", required = true) final Long id ) {
		return service.getById(id);
	}	
	
	@ApiOperation("Endpoint to get ALL Masteres")
	@GetMapping(value = "/api/v1/masters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody List<MasterResponse> getAllMaster() {
		return service.getByAllMasterEntity();
    }
	
	@ApiOperation("Endpoint to create new Master")
	@PostMapping(value = "/api/v1/masters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody MasterResponse saveMaster(
    		@RequestBody(required = true) final MasterRequest master) {
		return service.save(master);
    }
	
	@ApiOperation("Endpoint to update a Master")
	@PostMapping(value = "/api/v1/masters/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody MasterResponse upDateMaster(
    		@PathVariable(value = "id", required = true) final Long id ,
    		@RequestBody(required = true) final MasterRequest master) {
		return service.save(id, master);
    }
	

}

