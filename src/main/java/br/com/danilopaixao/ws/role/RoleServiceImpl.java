package br.com.danilopaixao.ws.role;

import java.util.List;
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
		Role role = Role.builder()
				.name(roleRequest.getName())
				.description(roleRequest.getDescription())
				.status(roleRequest.getStatus())
				.build();
		this.repository.save(role);
		return RoleResponse
					.builder()
					.description(role.getDescription())
					.name(role.getName())
					.status(role.getStatus())
					.build();
		
	}
	
	@Override
	public RoleResponse save(Long id, RoleRequest roleRequest) {
		log.info("save role", roleRequest);
		Role role = this.repository.findOne(id);
		role.setName(roleRequest.getName());
		role.setDescription(roleRequest.getDescription());
		role.setStatus(roleRequest.getStatus());
		this.repository.save(role);
		return RoleResponse
					.builder()
					.id(role.getId())
					.description(role.getDescription())
					.name(role.getName())
					.status(role.getStatus())
					.build();
		
	}
	
	@Override
	public RoleResponse inativeRole(Long id) {
		Role role = this.repository.findOne(id);
		role.setStatus(RoleStatusEnum.INATIVO);
		this.repository.save(role);
		return RoleResponse.builder()
				.id(role.getId())
				.description(role.getDescription())
				.name(role.getName())
				.status(role.getStatus())
				.build();
	}

	@Override
	public RoleResponse getById(Long id) {
		Role role = this.repository.findOne(id);
		return RoleResponse.builder()
				.id(role.getId())
				.description(role.getDescription())
				.name(role.getName())
				.status(role.getStatus())
				.build();
	}
	
	@Override
	public Role getUserById(Long id) {
		return this.repository.findOne(id);
	}
	
	@Override
	public List<RoleResponse> getByAllRoles() {
		return this.repository
				.findAll()
				.stream()
				.map(role -> RoleResponse.builder()
								.id(role.getId())
								.name(role.getName())
								.description(role.getDescription())
								.status(role.getStatus())
								.build()
				).collect(Collectors.toList());		
	}
	
}
