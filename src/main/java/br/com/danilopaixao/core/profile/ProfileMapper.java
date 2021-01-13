package br.com.danilopaixao.core.profile;

import br.com.danilopaixao.api.request.ProfileRequest;
import br.com.danilopaixao.api.response.ProfileResponse;
import br.com.danilopaixao.api.response.RoleResponse;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class ProfileMapper {

	public static final Function<ProfileRequest, ProfileEntity> mapProfileRequestToProfile = profileRequest ->
		Optional.ofNullable(profileRequest)
			.map(p -> {
					ProfileEntity profileEntityTmp = ProfileEntity.builder()
							//.id(profileRequest.getId())
							.name(profileRequest.getName())
							.description(profileRequest.getDescription())
							.flAdmin(profileRequest.getFlAdmin())
							.status(ProfileStatusEnum.getInstance(profileRequest.getStatus()))
							.build();
					profileEntityTmp.setId(profileRequest.getId());
					return profileEntityTmp;
			})
			.orElse(null);

	public static final Function<ProfileEntity, ProfileResponse> mapProfileToProfileResponse = profileEntity ->
		Optional.ofNullable(profileEntity)
				.map(p -> ProfileResponse.builder()
						.id(p.getId())
						.name(p.getName())
						.description(p.getDescription())
						.flAdmin(p.getFlAdmin())
						.status(p.getStatus().getValue())
						.roles(p.getRoleEntities().stream().map(r -> RoleResponse
								.builder()
								.id(r.getId())
								.name(r.getName())
								.description(r.getDescription())
								.status(r.getStatus())
								.build()).collect(Collectors.toList()))
						.build()
				)
				.orElse(null);

}
