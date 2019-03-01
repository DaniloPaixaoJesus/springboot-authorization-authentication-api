package br.com.danilopaixao.ws.profile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.danilopaixao.ws.role.Role;
import br.com.danilopaixao.ws.role.RoleResponse;
import br.com.danilopaixao.ws.role.RoleService;

public interface ProfileService {

	ProfileResponse save(ProfileRequest profile);
	ProfileResponse save(Long id, ProfileRequest profile);
	ProfileResponse getById(Long id);
	List<ProfileResponse> getByAllProfiles();
	ProfileResponse inativeProfile(Long id);
	Profile getUserById(Long id);
	

	public static final Function<ProfileRequest, Profile> mapProfileRequestToProfile = profileRequest ->
		Optional.ofNullable(profileRequest)
			.map(p -> {
					Profile profileTmp = Profile.builder()
							//.id(profileRequest.getId())
							.name(profileRequest.getName())
							.description(profileRequest.getDescription())
							.flAdmin(profileRequest.getFlAdmin())
							.status(profileRequest.getStatus())
							.roles(profileRequest.getRoles()
											.stream()
											.map(r -> { 
												Role roleTmp = Role.builder()
												//.id(r.getId())
												.name(r.getName())
												.description(r.getDescription())
												.status(r.getStatus())
												.build();
												roleTmp.setId(r.getId());
												return roleTmp;
											}).collect(Collectors.toList())
							).build();
					profileTmp.setId(profileRequest.getId()); 
					return profileTmp;
			})
			.orElse(null);
	
	public static final Function<Profile, ProfileResponse> mapProfileToProfileResponse = profile ->
		Optional.ofNullable(profile)
				.map(p -> ProfileResponse.builder()
						.id(p.getId())
						.name(p.getName())
						.description(p.getDescription())
						.flAdmin(p.getFlAdmin())
						.status(p.getStatus())
						.roles(p.getRoles().stream().map(r -> RoleResponse
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
