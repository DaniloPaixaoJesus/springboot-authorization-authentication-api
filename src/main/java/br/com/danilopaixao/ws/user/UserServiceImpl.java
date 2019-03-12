package br.com.danilopaixao.ws.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.profile.Profile;
import br.com.danilopaixao.ws.profile.ProfileResponse;
import br.com.danilopaixao.ws.profile.ProfileService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository repository;
	
	@Autowired
	private ProfileService profileService;
	
	@Override
	public UserResponse save(UserRequest userRequest) {
		log.info("save user => " + userRequest);
		final User user = Optional.ofNullable(userRequest).map(mapUserRequestToUser).orElse(null);
		user.setProfiles(new ArrayList<Profile>());
		userRequest.getProfiles().stream().forEach(p -> {
			Profile profile = profileService.getUserById(p.getId());
			user.getProfiles().add(profile);
		});
		this.repository.save(user);
		return Optional.ofNullable(user).map(mapUserToUserResponse).orElse(null);
	}
	
	@Override
	public UserResponse save(Long id, UserRequest userRequest) {
		log.info("save user => " + userRequest);
		final User user = this.repository.findOne(id);
		user.setName(userRequest.getName());
		user.setLogin(userRequest.getLogin());
		user.setPassword(userRequest.getPassword());
		user.setStatus(userRequest.getStatus());
		user.setProfiles(new ArrayList<Profile>());
		userRequest.getProfiles().stream().forEach(p -> {
			Profile profile = profileService.getUserById(p.getId());
			user.getProfiles().add(profile);
		});
		this.repository.save(user);
		return Optional.ofNullable(user).map(mapUserToUserResponse).orElse(null);
	}
	
	@Override
	public UserResponse inativeUser(Long id) {
		User user = this.repository.findOne(id);
		user.setStatus(UserStatusEnum.INATIVO);
		this.repository.save(user);
		return Optional.ofNullable(user).map(mapUserToUserResponse).orElse(null);
	}

	@Override
	public UserResponse getById(Long id) {
		User user = this.repository.findOne(id);
		return Optional.ofNullable(user).map(mapUserToUserResponse).orElse(null);
	}
	
	@Override
	public User getUserById(Long id) {
		return this.repository.findOne(id);
	}
	
	@Override
	public List<UserResponse> getByAllUsers() {
		return this.repository
				.findAll()
				.stream()
				.map(mapUserToUserResponse).collect(Collectors.toList());		
	}
	
	public static final Function<UserRequest, User> mapUserRequestToUser = userRequest ->
		Optional.ofNullable(userRequest)
			.map(user -> { 
					User usertmp = User.builder()
					//.id(user.getId())
					.name(user.getName())
					.login(user.getLogin())
					.password(user.getPassword())
					.status(user.getStatus())
					.build();
					usertmp.setId(user.getId());
					return usertmp;
			}
					
			).orElse(null);
	
	public static final Function<User, UserResponse> mapUserToUserResponse = user ->
		Optional.ofNullable(user)
				.map( u -> UserResponse.builder()
						.id(u.getId())
						.name(u.getName())
						.login(u.getLogin())
						.status(u.getStatus())
						.profiles(u.getProfiles()
									.stream()
									.map(p -> ProfileResponse.builder()
											.id(p.getId())
											.name(p.getName())
											.description(p.getDescription())
											.status(p.getStatus())
											.flAdmin(p.getFlAdmin())
											.build()
								).collect(Collectors.toList()))
						.build()
		).orElse(null);
}
