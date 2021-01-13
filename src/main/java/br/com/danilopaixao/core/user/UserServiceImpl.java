package br.com.danilopaixao.core.user;

import br.com.danilopaixao.api.request.UserRequest;
import br.com.danilopaixao.api.response.UserResponse;
import br.com.danilopaixao.core.profile.ProfileEntity;
import br.com.danilopaixao.core.profile.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.danilopaixao.core.user.UserException.invalidArgumentSupplier;
import static br.com.danilopaixao.core.user.UserMaper.mapUserRequestToUser;
import static br.com.danilopaixao.core.user.UserMaper.mapUserToUserResponse;

/**
 * Service responsable for manage users
 *
 * @author user
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ProfileService profileService;

	public void addProfile(UserRequest userRequest) {
		/* TODO: implement method to add profileEntity to user */
	}

	@Override
    public UserResponse authenticate(String login) {
        return this.getByLogin(login);
    }

    @Override
    public UserResponse save(UserRequest userRequest) {

        log.info("save user => " + userRequest);

        final UserEntity userEntity =
            Optional.ofNullable(userRequest)
				.map(mapUserRequestToUser)
				.orElseThrow(invalidArgumentSupplier);

        userEntity.setProfileEntities(new ArrayList<ProfileEntity>());
        userRequest.getProfiles().stream().forEach(p -> {
            ProfileEntity profileEntity = profileService.getUserById(p.getId());
            userEntity.getProfileEntities().add(profileEntity);
        });

        this.repository.save(userEntity);

        return Optional.ofNullable(userEntity)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public UserResponse save(Long id, UserRequest userRequest) {
        log.info("save user => " + userRequest);
        final UserEntity userEntity = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);

        String password = userRequest.getPassword();
        String hashedPassword = encriptPassword(password);
        userEntity.setPassword(hashedPassword);

        userEntity.setLogin(userRequest.getLogin());
        userEntity.setName(userRequest.getName());
        userEntity.setStatus(userRequest.getStatus());

        userEntity.setProfileEntities(new ArrayList<ProfileEntity>());
        userRequest.getProfiles().stream().forEach(p -> {
            ProfileEntity profileEntity = profileService.getUserById(p.getId());
            userEntity.getProfileEntities().add(profileEntity);
        });

        this.repository.save(userEntity);

        return Optional.ofNullable(userEntity)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public UserResponse inativeUser(Long id) {
        UserEntity userEntity = this.repository.findById(id).orElse(null);
        userEntity.setStatus(UserStatusEnum.INATIVO);
        this.repository.save(userEntity);
        return Optional.ofNullable(userEntity)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public UserResponse getById(Long id) {
        UserEntity userEntity = this.repository.findById(id).orElse(null);
        return Optional.ofNullable(userEntity)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public UserEntity getUserById(Long id) {
    	return this.repository.findById(id)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public List<UserResponse> getByAllUsers() {
        return this.repository.findAll()
			.stream()
			.map(mapUserToUserResponse)
			.collect(Collectors.toList());
    }

    private UserResponse getByLogin(String login) {
        UserEntity userEntity = this.repository.findByLogin(login);
        return Optional.ofNullable(userEntity)
			.map(mapUserToUserResponse)
			.orElse(null);
    }

	private String encriptPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

}
