package br.com.danilopaixao.ws.role;

import java.util.List;

public interface RoleService {

	RoleResponse save(RoleRequest role);
	RoleResponse save(Long id, RoleRequest role);
	RoleResponse getById(Long id);
	List<RoleResponse> getByAllRoles();
	RoleResponse inativeRole(Long id);
	Role getUserById(Long id);

}
