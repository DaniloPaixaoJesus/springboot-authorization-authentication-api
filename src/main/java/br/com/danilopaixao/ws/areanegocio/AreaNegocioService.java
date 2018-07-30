package br.com.danilopaixao.ws.areanegocio;

import java.util.List;

import br.com.danilopaixao.ws.areanegocio.api.request.ReturnPegRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.request.SaveProtocolRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.request.StartProcessRequestDto;
import br.com.danilopaixao.ws.areanegocio.api.response.DocumentPegDetailResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.ListStartProcessResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.ReturnPegResponseDto;
import br.com.danilopaixao.ws.areanegocio.api.response.SaveProtocolResponseDto;
import br.com.danilopaixao.ws.areanegocio.dto.TaskConfirmaDevolucaoDto;



public interface AreaNegocioService {

	List<DocumentPegDetailResponseDto> searchTasksByPegDetail(String taskDefinitionKey);

	TaskConfirmaDevolucaoDto completeTasksDevolucao(TaskConfirmaDevolucaoDto request);

	ListStartProcessResponseDto batchStartProcess(List<StartProcessRequestDto> request);

	SaveProtocolResponseDto saveProtocol(String processInstanceId, SaveProtocolRequestDto request);

	ReturnPegResponseDto devolverPeg(String processInstanceId, ReturnPegRequestDto request);

}
