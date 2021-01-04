package br.com.danilopaixao.ws.user;

import br.com.danilopaixao.ws.profile.ProfileResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.com.danilopaixao.ws.user.UserException.invalidArgumentSupplier;

public interface UserService {

	UserResponse save(UserRequest user);
	UserResponse save(Long id, UserRequest user);
	UserResponse getById(Long id);
	List<UserResponse> getByAllUsers();
	UserResponse inativeUser(Long id);
	User getUserById(Long id);
	UserResponse authenticate(String username);


	
	public static final Function<User, User> mapEndodePassword = user ->
		Optional.ofNullable(user)
				.map(u -> {
					String password = u.getPassword();
					PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String hashedPassword = passwordEncoder.encode(password);
					u.setPassword(hashedPassword);
					return u;
				})
		.orElseThrow(invalidArgumentSupplier);
		
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
		})
		.map(mapEndodePassword)
		.orElseThrow(invalidArgumentSupplier);

	public static final Function<User, UserResponse> mapUserToUserResponse = user ->
		Optional.ofNullable(user)
			.map( u -> UserResponse.builder()
					.id(u.getId())
					.name(u.getName())
					.login(u.getLogin())
					.status(u.getStatus())
					.password(u.getPassword())
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
				).orElseThrow(invalidArgumentSupplier);

}
