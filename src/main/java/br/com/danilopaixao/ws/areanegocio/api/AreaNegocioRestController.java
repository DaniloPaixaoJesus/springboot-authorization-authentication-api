package br.com.danilopaixao.ws.areanegocio.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopaixao.ws.areanegocio.AreaNegocioService;
import br.com.danilopaixao.ws.areanegocio.api.request.ReturnPegRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.request.SaveProtocolRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.request.StartProcessRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.response.DocumentPegDetailResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.ListStartProcessResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.ReturnPegResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.SaveProtocolResponseDto;
import br.com.danilopaixao.ws.areanegocio.dto.TaskConfirmaDevolucaoDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class AreaNegocioRestController {

	@Autowired
	private AreaNegocioService bpmService;

	@ApiOperation("Endpoint para retornar tarefas pelo id do processo ou id do processo pai TRATAMENTO_FISICO")
	@GetMapping(value = "/if-bpm/v1/tratamentofisico/tasks/grouped", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<DocumentPegDetailResponseDto> getTasksGroupedByPeg(
    		@RequestParam(value = "taskDefinitionKey", required = true) final String taskDefinitionKey
    		) {

		return this.bpmService.searchTasksByPegDetail(taskDefinitionKey);
	}	
    		
	
	
	@ApiOperation("Endpoint para completar uma task de DEVOLUCAO do processo pai TRATAMENTO_FISICO")
	@PostMapping(value = "/if-bpm/v1/tratamentofisico/task/devolucao", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TaskConfirmaDevolucaoDto completeTasksDevolucao(
    		@RequestBody(required = true) final TaskConfirmaDevolucaoDto request) {
		return this.bpmService.completeTasksDevolucao(request);
    }

	@ApiOperation("Endpoint para iniciar uma lista de novas instância do processo TRATAMENTO_FISICO, inicia com protocolo, sem protocolo ou não conta médica além de iniciar tarefa assíncrona de carregar XML da porto")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully")
	})
    @PostMapping(value = "/if-bpm/v1/tratamentofisico/instances", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ListStartProcessResponseDto postListStartProcessInstance(
    		@RequestBody final List<StartProcessRequestDto> request
    		) {
		return this.bpmService.batchStartProcess(request);
    }

	@ApiOperation("Endpoint para salvar um protocolo gerado em um Documento Peg existente pelo `id`")
	@PostMapping(value = "/if-bpm/v1/tratamentofisico/instances/{id}/protocol", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody SaveProtocolResponseDto saveProtocol(
    		@PathVariable(value = "id", required = true) final String processInstanceId,
    		@RequestBody(required = true) final SaveProtocolRequestDto request) {

    	return this.bpmService.saveProtocol(processInstanceId, request);
    }
	
	@ApiOperation("Endpoint para devolver a Peg para a porto ")
	@PostMapping(value = "/if-bpm/v1/tratamentofisico/instances/{id}/return", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ReturnPegResponseDto devolverPeg(
    		@PathVariable(value = "id", required = true) final String processInstanceId,
    		@RequestBody(required = true) final ReturnPegRequestDto request) {
    	return this.bpmService.devolverPeg(processInstanceId, request);
    }

}

