package br.com.danilopaixao.core.user;

import br.com.danilopaixao.api.request.UserRequest;
import br.com.danilopaixao.api.response.ProfileResponse;
import br.com.danilopaixao.api.response.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class UserMaper {

	public static final Function<UserEntity, UserEntity> mapEndodePassword = userEntity ->
		Optional.ofNullable(userEntity)
				.map(u -> {
					String password = u.getPassword();
					PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String hashedPassword = passwordEncoder.encode(password);
					u.setPassword(hashedPassword);
					return u;
				})
		.orElseThrow(UserException.invalidArgumentSupplier);
		
	public static final Function<UserRequest, UserEntity> mapUserRequestToUser = userRequest ->
	Optional.ofNullable(userRequest)
		.map(user -> { 
				UserEntity usertmp = UserEntity.builder()
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
		.orElseThrow(UserException.invalidArgumentSupplier);

	public static final Function<UserEntity, UserResponse> mapUserToUserResponse = userEntity ->
		Optional.ofNullable(userEntity)
			.map( u -> UserResponse.builder()
					.id(u.getId())
					.name(u.getName())
					.login(u.getLogin())
					.status(u.getStatus())
					.password(u.getPassword())
					.profiles(u.getProfileEntities()
								.stream()
								.map(p -> ProfileResponse.builder()
										.id(p.getId())
										.name(p.getName())
										.description(p.getDescription())
										.status(p.getStatus().getValue())
										.flAdmin(p.getFlAdmin())
										.build()
							).collect(Collectors.toList()))
					.build()
				).orElseThrow(UserException.invalidArgumentSupplier);

}
