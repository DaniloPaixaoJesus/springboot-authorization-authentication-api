package br.com.danilopaixao.ws.areanegocio.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.danilopaixao.ws.core.http.HttpRestClient;

@Component
public class AreaNegocioEngineRestClient {
	
	@Value("${br.com.danilopaixao.ws.variavel}")
	private String camundaUrl;

	@Value("${br.com.danilopaixao.ws.variavel}")
	private String tenantId;

	@Autowired
	private HttpRestClient httpClient;

	public ResponseEntity<String[]> getTasksByProcessDefinitionKey(final Object processDefinitionKey, final String subProcesses) {
		ResponseEntity<String[]> tasks = httpClient.get(this.camundaUrl + "/task?processDefinitionKeyIn=" + processDefinitionKey + "," + subProcesses, String[].class);
		return tasks;
	}

}
