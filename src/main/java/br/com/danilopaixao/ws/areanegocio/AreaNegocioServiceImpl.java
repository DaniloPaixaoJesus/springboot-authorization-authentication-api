package br.com.danilopaixao.ws.areanegocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.areanegocio.api.request.ReturnPegRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.request.SaveProtocolRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.request.StartProcessRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.response.DocumentPegDetailResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.ListStartProcessResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.ReturnPegResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.SaveProtocolResponseDto;
import br.com.danilopaixao.ws.areanegocio.client.AreaNegocioEngineRestClient;
import br.com.danilopaixao.ws.areanegocio.dto.TaskConfirmaDevolucaoDto;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
class AreaNegocioServiceImpl implements AreaNegocioService {

	@Value("${br.com.danilopaixao.ws.variavel}")
	private String processDefinitionKey;

	@Value("${br.com.danilopaixao.ws.variavel}")
	private String subProcesses;
	
	@Autowired
	private AreaNegocioEngineRestClient bpmClient;
	
	@Override
	public TaskConfirmaDevolucaoDto completeTasksDevolucao(TaskConfirmaDevolucaoDto request) {
		log.debug("sf4j");
		return null;
	}

	@Override
	public ListStartProcessResponseDto batchStartProcess(List<StartProcessRequestDto> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SaveProtocolResponseDto saveProtocol(String processInstanceId, SaveProtocolRequestDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnPegResponseDto devolverPeg(String processInstanceId, ReturnPegRequestDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentPegDetailResponseDto> searchTasksByPegDetail(String taskDefinitionKey) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
