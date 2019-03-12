package br.com.danilopaixao.ws.role;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.danilopaixao.ws.profile.ProfileResponse;

public interface RoleService {

	RoleResponse save(Long id, RoleRequest role);
	RoleResponse save(RoleRequest role);
	RoleResponse getById(Long id);
	List<RoleResponse> getByAllRoles();
	RoleResponse inativeRole(Long id);
	Role getRoleById(Long id);
	List<RoleResponse> getRoleByLogin(String login);


	public static final Function<RoleRequest, Role> mapRoleRequestToRole = roleRequest ->
		Optional.ofNullable(roleRequest)
				.map(role -> { 
						Role roleTmp = Role.builder()
								//.id(roleRequest.getId())
								.name(roleRequest.getName())
								.description(roleRequest.getDescription())
								.status(roleRequest.getStatus())
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
							.profiles(role.getProfiles()
											.stream()
											.map(p -> ProfileResponse.builder()
													.id(p.getId())
													.name(p.getName())
													.description(p.getDescription())
													.flAdmin(p.getFlAdmin())
													.status(p.getStatus())
													.build()
											).collect(Collectors.toList())
							).build())
					.orElse(null);

}
