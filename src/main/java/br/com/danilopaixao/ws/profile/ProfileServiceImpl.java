package br.com.danilopaixao.ws.profile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.role.Role;
import br.com.danilopaixao.ws.role.RoleResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
class ProfileServiceImpl implements ProfileService {

	@Autowired
    private ProfileRepository repository;
	
	@Override
	public ProfileResponse save(ProfileRequest profileRequest) {
		log.info("save profile => " + profileRequest);
		Profile profile = Optional.ofNullable(profileRequest).map(mapProfileRequestToProfile).orElse(null);
		this.repository.save(profile);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElse(null);
		
	}
	
	@Override
	public ProfileResponse save(Long id, ProfileRequest profileRequest) {
		log.info("save profile => " + profileRequest);
		Profile profile = this.repository.findOne(profileRequest.getId());
		profile.setDescription(profileRequest.getDescription());
		profile.setFlAdmin(profileRequest.getFlAdmin());
		profile.setName(profileRequest.getName());
		profile.setStatus(profileRequest.getStatus());
		profile.setRoles(profileRequest.getRoles()
				.stream()
				.map(r -> { Role roleTmp = Role.builder()
								//.id(p.getId())
								.name(r.getName())
								.description(r.getDescription())
								.status(r.getStatus())
								.build();
							roleTmp.setId(r.getId());
							return roleTmp;
					}).collect(Collectors.toList())
				);
		
		this.repository.save(profile);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElse(null);
		
	}
	
	@Override
	public ProfileResponse inativeProfile(Long id) {
		Profile profile = this.repository.findOne(id);
		profile.setStatus(ProfileStatusEnum.INATIVO);
		this.repository.save(profile);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElse(null);
	}

	@Override
	public ProfileResponse getById(Long id) {
		Profile profile = this.repository.findOne(id);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElse(null);
	}
	
	@Override
	public Profile getUserById(Long id) {
		return this.repository.findOne(id);
	}
	
	@Override
	public List<ProfileResponse> getByAllProfiles() {
		List<Profile> result = this.repository.findAll();
		return result.stream().map(mapProfileToProfileResponse).collect(Collectors.toList());		
	}
	
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
