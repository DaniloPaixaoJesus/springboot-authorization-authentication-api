package br.com.danilopaixao.ws.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.profile.Profile;
import br.com.danilopaixao.ws.profile.ProfileResponse;
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
				.id(roleRequest.getId())
				.name(roleRequest.getName())
				.description(roleRequest.getDescription())
				.status(roleRequest.getStatus())
				.profiles(roleRequest.getProfiles()
						.stream()
						.map(p -> Profile.builder()
									.id(p.getId())
									.name(p.getName())
									.description(p.getDescription())
									.status(p.getStatus())
									.build()
						).collect(Collectors.toList()))
				.build();
		this.repository.save(role);
		return RoleResponse
					.builder()
					.id(role.getId())
					.name(role.getName())
					.description(role.getDescription())
					.status(role.getStatus())
					.profiles(role.getProfiles().stream().map(p -> ProfileResponse
							.builder()
							.id(p.getId())
							.name(p.getName())
							.description(p.getDescription())
							.status(p.getStatus())
							.build()).collect(Collectors.toList()))
					.build();
		
	}
	
	@Override
	public RoleResponse inativeRole(Long id) {
		Role role = this.repository.findOne(id);
		role.setStatus(RoleStatusEnum.INATIVO);
		this.repository.save(role);
		return RoleResponse.builder()
				.id(role.getId())
				.name(role.getName())
				.description(role.getDescription())
				.status(role.getStatus())
				.profiles(role.getProfiles().stream().map(p -> ProfileResponse
						.builder()
						.id(p.getId())
						.name(p.getName())
						.description(p.getDescription())
						.status(p.getStatus())
						.build()).collect(Collectors.toList()))
				.build();
	}

	@Override
	public RoleResponse getById(Long id) {
		Role role = this.repository.findOne(id);
		return RoleResponse.builder()
				.id(role.getId())
				.name(role.getName())
				.description(role.getDescription())
				.status(role.getStatus())
				.profiles(role.getProfiles().stream().map(p -> ProfileResponse
						.builder()
						.id(p.getId())
						.name(p.getName())
						.description(p.getDescription())
						.status(p.getStatus())
						.build()).collect(Collectors.toList()))
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
								.profiles(role.getProfiles().stream().map(p -> ProfileResponse
										.builder()
										.id(p.getId())
										.name(p.getName())
										.description(p.getDescription())
										.status(p.getStatus())
										.build()).collect(Collectors.toList()))
								.build()
				).collect(Collectors.toList());		
	}
	
}
