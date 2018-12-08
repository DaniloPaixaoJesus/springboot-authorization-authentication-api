package br.com.danilopaixao.ws.detail;

import java.util.List;

public interface DetailEntityService {

	DetailEntityResponse save(DetailEntityRequest process);
	DetailEntityResponse save(Long id, DetailEntityRequest process);
	DetailEntityResponse getById(Long id);
	List<DetailEntityResponse> getAllDetailEntity();

}
