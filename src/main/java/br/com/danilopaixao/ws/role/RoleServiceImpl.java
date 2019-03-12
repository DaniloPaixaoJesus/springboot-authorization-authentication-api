package br.com.danilopaixao.ws.role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
class RoleServiceImpl implements RoleService {

	@Autowired
    private RoleRepository repository;
	
	@Override
	public RoleResponse save(RoleRequest roleRequest) {
		log.info("save role => " + roleRequest);
		Role role = Optional.ofNullable(roleRequest).map(mapRoleRequestToRole).orElse(null);
		this.repository.save(role);
		return Optional.ofNullable(role).map(mapRoleToRoleResponse).orElse(null);
		
	}
	
	@Override
	public RoleResponse save(Long id, RoleRequest roleRequest) {
		log.info("save role => " + roleRequest);
		//Role role = Optional.ofNullable(roleRequest).map(mapRoleRequestToRole).orElse(null);
		Role role = this.repository.findOne(id);		
		role.setDescription(roleRequest.getDescription());
		role.setName(roleRequest.getName());
		role.setStatus(roleRequest.getStatus());
		this.repository.save(role);
		return Optional.ofNullable(role).map(mapRoleToRoleResponse).orElse(null);
	}
	
	@Override
	public RoleResponse inativeRole(Long id) {
		Role role = this.repository.findOne(id);
		role.setStatus(RoleStatusEnum.INATIVO);
		this.repository.save(role);
		return Optional.ofNullable(role).map(mapRoleToRoleResponse).orElse(null);
	}

	@Override
	public RoleResponse getById(Long id) {
		Role role = this.repository.findOne(id);
		return Optional.ofNullable(role).map(mapRoleToRoleResponse).orElse(null);
	}
	
	@Override
	public Role getRoleById(Long id) {
		return this.repository.findOne(id);
	}
	
	@Override
	public List<RoleResponse>getRoleByLogin(String login) {
		return this.repository.findByLogin(login).stream()
				.map(mapRoleToRoleResponse).collect(Collectors.toList());
	}
	
	@Override
	public List<RoleResponse> getByAllRoles() {
		return this.repository
				.findAll()
				.stream()
				.map(mapRoleToRoleResponse).collect(Collectors.toList());		
	}
	
}
