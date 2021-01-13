package br.com.danilopaixao.core.profile;

import br.com.danilopaixao.api.request.ProfileRequest;
import br.com.danilopaixao.api.response.ProfileResponse;
import br.com.danilopaixao.core.role.RoleEntity;
import br.com.danilopaixao.api.request.RoleRequest;
import br.com.danilopaixao.core.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.danilopaixao.core.profile.ProfileException.invalidArgumentSupplier;
import static br.com.danilopaixao.core.profile.ProfileMapper.mapProfileRequestToProfile;
import static br.com.danilopaixao.core.profile.ProfileMapper.mapProfileToProfileResponse;

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
		/* TODO: implement method to add role into profileEntity */
	}

	@Override
	public ProfileResponse save(ProfileRequest profileRequest) {
		log.info("save profileEntity => " + profileRequest);
		ProfileEntity profileEntity = Optional.ofNullable(profileRequest).map(mapProfileRequestToProfile).orElseThrow(invalidArgumentSupplier);
		profileEntity.setRoleEntities(new ArrayList<RoleEntity>());
		for (RoleRequest r : profileRequest.getRoles()) {
			RoleEntity roleEntity = this.roleService.getRoleById(r.getId());
			profileEntity.getRoleEntities().add(roleEntity);
		}
		this.repository.save(profileEntity);
		return Optional.ofNullable(profileEntity).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);

	}

	@Override
	public ProfileResponse save(Long id, ProfileRequest profileRequest) {
		log.info("save profileEntity => " + profileRequest);
		ProfileEntity profileEntity = this.repository.findById(profileRequest.getId()).orElseThrow(invalidArgumentSupplier);

		profileEntity.setDescription(profileRequest.getDescription());
		profileEntity.setFlAdmin(profileRequest.getFlAdmin());
		profileEntity.setName(profileRequest.getName());
		profileEntity.setStatus(ProfileStatusEnum.getInstance(profileRequest.getStatus()));
		profileEntity.setRoleEntities(new ArrayList<RoleEntity>());
		for (RoleRequest r : profileRequest.getRoles()) {
			RoleEntity roleEntity = this.roleService.getRoleById(r.getId());
			profileEntity.getRoleEntities().add(roleEntity);
		}

		this.repository.save(profileEntity);
		return Optional.ofNullable(profileEntity).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);

	}

	@Override
	public ProfileResponse inativeProfile(Long id) {
		ProfileEntity profileEntity = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
		profileEntity.setStatus(ProfileStatusEnum.INATIVO);
		this.repository.save(profileEntity);
		return Optional.ofNullable(profileEntity).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);
	}

	@Override
	public ProfileResponse getById(Long id) {
		ProfileEntity profileEntity = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
		return Optional.ofNullable(profileEntity).map(mapProfileToProfileResponse).orElseThrow(invalidArgumentSupplier);
	}

	@Override
	public ProfileEntity getUserById(Long id) {
		return this.repository.findById(id).orElseThrow(invalidArgumentSupplier);
	}

	@Override
	public List<ProfileResponse> getByAllProfiles() {
		List<ProfileEntity> result = this.repository.findAll();
		return result.stream().map(mapProfileToProfileResponse).collect(Collectors.toList());
	}


}
