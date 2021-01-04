package br.com.danilopaixao.ws.user;

import br.com.danilopaixao.ws.profile.Profile;
import br.com.danilopaixao.ws.profile.ProfileService;
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

import static br.com.danilopaixao.ws.user.UserException.invalidArgumentSupplier;

/**
 * Service responsable for manage users
 *
 * @author user
 */
@Slf4j
@Service
@Transactional
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ProfileService profileService;

	public void addProfile(UserRequest userRequest) {
		/* TODO: implement method to add profile to user */
	}

	@Override
    public UserResponse authenticate(String login) {
        return this.getByLogin(login);
    }

    @Override
    public UserResponse save(UserRequest userRequest) {

        log.info("save user => " + userRequest);

        final User user =
            Optional.ofNullable(userRequest)
				.map(mapUserRequestToUser)
				.orElseThrow(invalidArgumentSupplier);

        user.setProfiles(new ArrayList<Profile>());
        userRequest.getProfiles().stream().forEach(p -> {
            Profile profile = profileService.getUserById(p.getId());
            user.getProfiles().add(profile);
        });

        this.repository.save(user);

        return Optional.ofNullable(user)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public UserResponse save(Long id, UserRequest userRequest) {
        log.info("save user => " + userRequest);
        final User user = this.repository.findById(id).orElseThrow(invalidArgumentSupplier);

        String password = userRequest.getPassword();
        String hashedPassword = encriptPassword(password);
        user.setPassword(hashedPassword);

        user.setLogin(userRequest.getLogin());
        user.setName(userRequest.getName());
        user.setStatus(userRequest.getStatus());

        user.setProfiles(new ArrayList<Profile>());
        userRequest.getProfiles().stream().forEach(p -> {
            Profile profile = profileService.getUserById(p.getId());
            user.getProfiles().add(profile);
        });

        this.repository.save(user);

        return Optional.ofNullable(user)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public UserResponse inativeUser(Long id) {
        User user = this.repository.findById(id).orElse(null);
        user.setStatus(UserStatusEnum.INATIVO);
        this.repository.save(user);
        return Optional.ofNullable(user)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = this.repository.findById(id).orElse(null);
        return Optional.ofNullable(user)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

    @Override
    public User getUserById(Long id) {
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
        User user = this.repository.findByLogin(login);
        return Optional.ofNullable(user)
			.map(mapUserToUserResponse)
			.orElseThrow(invalidArgumentSupplier);
    }

	private String encriptPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

}
