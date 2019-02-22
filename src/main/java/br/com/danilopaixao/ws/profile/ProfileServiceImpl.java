package br.com.danilopaixao.ws.profile;

import java.util.Iterator;
import java.util.List;
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
		Profile profile = Profile.builder()
				.name(profileRequest.getName())
				.description(profileRequest.getDescription())
				.flAdmin(profileRequest.getFlAdmin())
				.status(profileRequest.getStatus())
				.build();
		this.repository.save(profile);
		return ProfileResponse
					.builder()
					.description(profile.getDescription())
					.name(profile.getName())
					.flAdmin(profile.getFlAdmin())
					.status(profile.getStatus())
					.build();
		
	}
	
	@Override
	public ProfileResponse save(Long id, ProfileRequest profileRequest) {
		log.info("save profile", profileRequest);
		Profile profile = this.repository.findOne(id);
		profile.setName(profileRequest.getName());
		profile.setDescription(profileRequest.getDescription());
		profile.setStatus(profileRequest.getStatus());
		this.repository.save(profile);
		return ProfileResponse
					.builder()
					.id(profile.getId())
					.description(profile.getDescription())
					.name(profile.getName())
					.flAdmin(profile.getFlAdmin())
					.status(profile.getStatus())
					.build();
		
	}
	
	@Override
	public ProfileResponse inativeProfile(Long id) {
		Profile profile = this.repository.findOne(id);
		profile.setStatus(ProfileStatusEnum.INATIVO);
		this.repository.save(profile);
		return ProfileResponse.builder()
				.id(profile.getId())
				.description(profile.getDescription())
				.name(profile.getName())
				.flAdmin(profile.getFlAdmin())
				.status(profile.getStatus())
				.build();
	}

	@Override
	public ProfileResponse getById(Long id) {
		Profile profile = this.repository.findOne(id);
		return ProfileResponse.builder()
				.id(profile.getId())
				.description(profile.getDescription())
				.name(profile.getName())
				.flAdmin(profile.getFlAdmin())
				.status(profile.getStatus())
				.build();
	}
	
	@Override
	public Profile getUserById(Long id) {
		return this.repository.findOne(id);
	}
	
	@Override
	public List<ProfileResponse> getByAllProfiles() {
		List<Profile> result = this.repository.findAll();
		
		List<RoleResponse> teste = result.get(0).getRoles().stream().map(r -> RoleResponse.builder().name("TESTE").build()).collect(Collectors.toList()); //.collect(Collectors.toList());
		
		return result.stream().map(profile -> ProfileResponse.builder()
								.id(profile.getId())
								.name(profile.getName())
								.description(profile.getDescription())
								.status(profile.getStatus())
								.flAdmin(profile.getFlAdmin())
								.roles(profile.getRoles().stream().map(r -> RoleResponse.builder().name("TESTE").build()).collect(Collectors.toList()))
								.build()
				).collect(Collectors.toList());		
	}
	
}
