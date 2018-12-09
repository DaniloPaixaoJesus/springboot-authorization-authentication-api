package br.com.danilopaixao.ws.detail;

import java.util.List;

public interface DetailEntityService {

	DetailResponse save(DetailRequest process);
	DetailResponse save(Long id, DetailRequest process);
	DetailResponse getById(Long id);
	List<DetailResponse> getAllDetailEntity();

}
