package br.com.danilopaixao.ws.profile;

import br.com.danilopaixao.ws.role.Role;
import br.com.danilopaixao.ws.role.RoleRequest;
import br.com.danilopaixao.ws.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.danilopaixao.ws.profile.ProfileException.invalidArgumentSupplier;

/**
 * 
 * @author user
 *
 */
@Slf4j
@Service
@Transactional
class ProfileServiceImpl implements ProfileService {

	@Autowired
    private ProfileRepository repository;
	
	@Autowired
	private RoleService roleService; 
	
	public void addRole(ProfileRequest profileRequest) {
		/* TODO: implement method to add role into profile */
	}
	
	@Override
	public ProfileResponse save(ProfileRequest profileRequest) {
		log.info("save profile => " + profileRequest);
		Profile profile = Optional.ofNullable(profileRequest).map(mapProfileRequestToProfile).orElseThrow(invalidArgumentSupplier);
		profile.setRoles(new ArrayList<Role>());
		for (RoleRequest r : profileRequest.getRoles()) {
			Role role = this.roleService.getRoleById(r.getId());
			profile.getRoles().add(role);
		}
		this.repository.save(profile);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);
		
	}
	
	@Override
	public ProfileResponse save(Long id, ProfileRequest profileRequest) {
		log.info("save profile => " + profileRequest);
		Profile profile = this.repository.findById(profileRequest.getId()).orElseThrow(invalidArgumentSupplier);

		profile.setDescription(profileRequest.getDescription());
		profile.setFlAdmin(profileRequest.getFlAdmin());
		profile.setName(profileRequest.getName());
		profile.setStatus(profileRequest.getStatus());
		profile.setRoles(new ArrayList<Role>());
		for (RoleRequest r : profileRequest.getRoles()) {
			Role role = this.roleService.getRoleById(r.getId());
			profile.getRoles().add(role);
		}
		
		this.repository.save(profile);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);
		
	}
	
	@Override
	public ProfileResponse inativeProfile(Long id) {
		Profile profile = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
		profile.setStatus(ProfileStatusEnum.INATIVO);
		this.repository.save(profile);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);
	}

	@Override
	public ProfileResponse getById(Long id) {
		Profile profile = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
		return Optional.ofNullable(profile).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);
	}
	
	@Override
	public Profile getUserById(Long id) {
		return this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
	}
	
	@Override
	public List<ProfileResponse> getByAllProfiles() {
		List<Profile> result = this.repository.findAll();
		return result.stream().map(mapProfileToProfileResponse).collect(Collectors.toList());		
	}
	
	
}
