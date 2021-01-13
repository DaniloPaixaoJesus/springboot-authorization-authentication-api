package br.com.danilopaixao.core.role;

import br.com.danilopaixao.api.request.RoleRequest;
import br.com.danilopaixao.api.response.ProfileResponse;
import br.com.danilopaixao.api.response.RoleResponse;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class RoleMapper {

	public static final Function<RoleRequest, RoleEntity> mapRoleRequestToRole = roleRequest ->
		Optional.ofNullable(roleRequest)
				.map(role -> { 
						RoleEntity roleEntityTmp = RoleEntity.builder()
								//.id(roleRequest.getId())
								.name(roleRequest.getName())
								.description(roleRequest.getDescription())
								.status(roleRequest.getStatus())
								.build();
						roleEntityTmp.setId(roleRequest.getId());
						return roleEntityTmp;
				}).orElse(null);

	public static final Function<RoleEntity, RoleResponse> mapRoleToRoleResponse = role ->
		Optional.ofNullable(role)
					.map(r -> RoleResponse.builder()
							.id(role.getId())
							.name(role.getName())
							.description(role.getDescription())
							.status(role.getStatus())
							.profiles(role.getProfileEntities()
											.stream()
											.map(p -> ProfileResponse.builder()
													.id(p.getId())
													.name(p.getName())
													.description(p.getDescription())
													.flAdmin(p.getFlAdmin())
													.status(p.getStatus().getValue())
													.build()
											).collect(Collectors.toList())
							).build())
					.orElse(null);

}
