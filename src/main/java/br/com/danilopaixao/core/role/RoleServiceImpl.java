package br.com.danilopaixao.core.role;

import br.com.danilopaixao.api.request.RoleRequest;
import br.com.danilopaixao.api.response.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.danilopaixao.core.role.RoleException.invalidArgumentSupplier;
import static br.com.danilopaixao.core.role.RoleMapper.mapRoleRequestToRole;
import static br.com.danilopaixao.core.role.RoleMapper.mapRoleToRoleResponse;

/**
 * 
 * @author user
 *
 */
@Slf4j
@Service
@Transactional
class RoleServiceImpl implements RoleService {

	@Autowired
    private RoleRepository repository;
	
	@Override
	public RoleResponse save(RoleRequest roleRequest) {
		log.info("save role => " + roleRequest);
		RoleEntity roleEntity = Optional.ofNullable(roleRequest).map(mapRoleRequestToRole).orElseThrow(invalidArgumentSupplier);
		this.repository.save(roleEntity);
		return Optional.ofNullable(roleEntity).map(mapRoleToRoleResponse).orElseThrow(invalidArgumentSupplier);
		
	}
	
	@Override
	public RoleResponse save(Long id, RoleRequest roleRequest) {
		log.info("save role => " + roleRequest);
		//Role role = Optional.ofNullable(roleRequest).map(mapRoleRequestToRole).orElseThrow(invalidArgumentSupplier);
		RoleEntity roleEntity = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
		roleEntity.setDescription(roleRequest.getDescription());
		roleEntity.setName(roleRequest.getName());
		roleEntity.setStatus(roleRequest.getStatus());
		this.repository.save(roleEntity);
		return Optional.ofNullable(roleEntity).map(mapRoleToRoleResponse).orElseThrow(invalidArgumentSupplier);
	}
	
	@Override
	public RoleResponse inativeRole(Long id) {
		RoleEntity roleEntity = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
		roleEntity.setStatus(RoleStatusEnum.INATIVO);
		this.repository.save(roleEntity);
		return Optional.ofNullable(roleEntity).map(mapRoleToRoleResponse).orElseThrow(invalidArgumentSupplier);
	}

	@Override
	public RoleResponse getById(Long id) {
		RoleEntity roleEntity = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
		return Optional.ofNullable(roleEntity).map(mapRoleToRoleResponse).orElseThrow(invalidArgumentSupplier);
	}
	
	@Override
	public RoleEntity getRoleById(Long id) {
		return this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
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
