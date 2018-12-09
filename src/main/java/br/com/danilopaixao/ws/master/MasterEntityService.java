package br.com.danilopaixao.ws.master;

import java.util.List;

public interface MasterEntityService {

	MasterResponse save(MasterRequest process);
	MasterResponse save(Long id, MasterRequest process);
	MasterResponse getById(Long id);
	List<MasterResponse> getByAllMasterEntity();
	MasterEntity getMasterEntityById(Long valueOf);

}
