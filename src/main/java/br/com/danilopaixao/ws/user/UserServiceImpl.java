package br.com.danilopaixao.ws.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.profile.Profile;
import br.com.danilopaixao.ws.profile.ProfileResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository repository;
	
	@Override
	public UserResponse save(UserRequest userRequest) {
		log.info("save user => " + userRequest);
		User user = User.builder()
				.id(userRequest.getId())
				.name(userRequest.getName())
				.login(userRequest.getLogin())
				.password(userRequest.getPassword())
				.status(userRequest.getStatus())
				.profiles(userRequest.getProfiles()
							.stream()
							.map(p -> Profile.builder()
									.id(p.getId())
									.name(p.getName())
									.description(p.getDescription())
									.status(p.getStatus())
									.flAdmin(p.getFlAdmin())
									.build()
						).collect(Collectors.toList()))
				.build();
		this.repository.save(user);
		return UserResponse
					.builder()
					.id(user.getId())
					.login(user.getLogin())
					.name(user.getName())
					.status(user.getStatus())
					.profiles(user.getProfiles()
							.stream()
							.map(p -> ProfileResponse.builder()
										.id(p.getId())
										.name(p.getName())
										.description(p.getDescription())
										.status(p.getStatus())
										.flAdmin(p.getFlAdmin())
										.build()
							).collect(Collectors.toList()))
					.build();
		
	}
	
	@Override
	public UserResponse inativeUser(Long id) {
		User user = this.repository.findOne(id);
		user.setStatus(UserStatusEnum.INATIVO);
		this.repository.save(user);
		return UserResponse
				.builder()
				.id(user.getId())
				.login(user.getLogin())
				.name(user.getName())
				.status(user.getStatus())
				.profiles(user.getProfiles()
							.stream()
							.map(p -> ProfileResponse.builder()
									.id(p.getId())
									.name(p.getName())
									.description(p.getDescription())
									.status(p.getStatus())
									.flAdmin(p.getFlAdmin())
									.build()
						).collect(Collectors.toList()))
				.build();
	}

	@Override
	public UserResponse getById(Long id) {
		User user = this.repository.findOne(id);
		
		return UserResponse
				.builder()
				.id(user.getId())
				.name(user.getName())
				.login(user.getLogin())
				.status(user.getStatus())
				.profiles(user.getProfiles()
							.stream()
							.map(p -> ProfileResponse.builder()
									.id(p.getId())
									.name(p.getName())
									.description(p.getDescription())
									.status(p.getStatus())
									.flAdmin(p.getFlAdmin())
									.build()
						).collect(Collectors.toList()))
				.build();
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
				.map(u -> UserResponse.builder()
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
				).collect(Collectors.toList());		
	}
	
}
