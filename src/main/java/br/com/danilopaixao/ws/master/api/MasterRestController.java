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

import br.com.danilopaixao.ws.master.MasterEntityRequest;
import br.com.danilopaixao.ws.master.MasterEntityResponse;
import br.com.danilopaixao.ws.master.MasterEntityService;
import io.swagger.annotations.ApiOperation;

@RestController
public class MasterRestController {
	
	@Autowired
	private MasterEntityService service;
	
	@ApiOperation("EndPoint to get Process by ID ")
	@GetMapping(value = "/api/v1/processes/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody MasterEntityResponse getProcess(
    		@PathVariable(value = "id", required = true) final Long id ) {
		return service.getById(id);
	}	
	
	@ApiOperation("Endpoint to get ALL Processes")
	@GetMapping(value = "/api/v1/processes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody List<MasterEntityResponse> getAllProcesses() {
		return service.getByAllMasterEntity();
    }
	
	@ApiOperation("Endpoint to create new Process")
	@PostMapping(value = "/api/v1/processes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody MasterEntityResponse saveProcess(
    		@RequestBody(required = true) final MasterEntityRequest process) {
		return service.save(process);
    }
	
	@ApiOperation("Endpoint to update a Process")
	@PostMapping(value = "/api/v1/processes/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody MasterEntityResponse upDateProcess(
    		@PathVariable(value = "id", required = true) final Long id ,
    		@RequestBody(required = true) final MasterEntityRequest process) {
		return service.save(id, process);
    }
	

}

