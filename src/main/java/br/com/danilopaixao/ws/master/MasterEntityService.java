package br.com.danilopaixao.ws.master;

import java.util.List;

public interface MasterEntityService {

	MasterEntityResponse save(MasterEntityRequest process);
	MasterEntityResponse save(Long id, MasterEntityRequest process);
	MasterEntityResponse getById(Long id);
	List<MasterEntityResponse> getByAllMasterEntity();
	MasterEntity getMasterEntityById(Long valueOf);

}
