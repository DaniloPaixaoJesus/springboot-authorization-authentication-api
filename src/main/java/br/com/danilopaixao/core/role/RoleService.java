package br.com.danilopaixao.core.role;

import br.com.danilopaixao.api.request.RoleRequest;
import br.com.danilopaixao.api.response.RoleResponse;

import java.util.List;

public interface RoleService {

	RoleResponse save(Long id, RoleRequest role);
	RoleResponse save(RoleRequest role);
	RoleResponse getById(Long id);
	List<RoleResponse> getByAllRoles();
	RoleResponse inativeRole(Long id);
	RoleEntity getRoleById(Long id);
	List<RoleResponse> getRoleByLogin(String login);

}
