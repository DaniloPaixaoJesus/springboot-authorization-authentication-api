package br.com.danilopaixao.ws.role;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
		Role role = Optional.ofNullable(roleRequest).map(mapRoleRequestToRole).orElse(null);
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
	public Role getUserById(Long id) {
		return this.repository.findOne(id);
	}
	
	@Override
	public List<RoleResponse> getByAllRoles() {
		return this.repository
				.findAll()
				.stream()
				.map(mapRoleToRoleResponse).collect(Collectors.toList());		
	}
	
	public static final Function<RoleRequest, Role> mapRoleRequestToRole = roleRequest ->
		Optional.ofNullable(roleRequest)
				.map(role -> { 
						Role roleTmp = Role.builder()
								//.id(roleRequest.getId())
								.name(roleRequest.getName())
								.description(roleRequest.getDescription())
								.status(roleRequest.getStatus())
								.profiles(roleRequest.getProfiles()
										.stream()
										.map(p -> {
												Profile profileTmp = Profile.builder()
														//.id(p.getId())
														.name(p.getName())
														.description(p.getDescription())
														.status(p.getStatus())
														.build();
												profileTmp.setId(p.getId());
												return profileTmp; 
											}).collect(Collectors.toList()))
								.build();
						roleTmp.setId(roleRequest.getId());
						return roleTmp; 
				}).orElse(null);
	
	public static final Function<Role, RoleResponse> mapRoleToRoleResponse = role ->
		Optional.ofNullable(role)
					.map(r -> RoleResponse.builder()
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
							.build())
					.orElse(null);
}
