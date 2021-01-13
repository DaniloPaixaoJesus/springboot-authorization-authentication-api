package br.com.danilopaixao;

import java.util.ArrayList;
import java.util.List;

import br.com.danilopaixao.api.request.ProfileRequest;
import br.com.danilopaixao.api.response.ProfileResponse;
import br.com.danilopaixao.core.profile.ProfileService;
import br.com.danilopaixao.core.profile.ProfileStatusEnum;
import br.com.danilopaixao.api.request.RoleRequest;
import br.com.danilopaixao.api.response.RoleResponse;
import br.com.danilopaixao.core.role.RoleService;
import br.com.danilopaixao.core.role.RoleStatusEnum;
import br.com.danilopaixao.api.request.UserRequest;
import br.com.danilopaixao.api.response.UserResponse;
import br.com.danilopaixao.core.user.UserStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopaixao.core.user.UserService;
import io.swagger.annotations.ApiOperation;


@RestController
public class InitController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private RoleService roleService;

	@ApiOperation("Endpoint to init Users")
	@PostMapping(value = "/init/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    UserResponse initUsers() {

		UserResponse userTest = this.userService.authenticate("user");
		if(userTest != null) {
			return userTest;
		}

		/** create test role */
		RoleRequest role = RoleRequest.builder()
							.description("initial role ADMIN")
							.name("ADMIN")
							.status(RoleStatusEnum.ATIVO)
							.build();
		RoleResponse roleResponse = roleService.save(role);
		role.setId(roleResponse.getId());

		/** create test profileEntity */
		List<RoleRequest> roles = new ArrayList<RoleRequest>();
		roles.add(role);
		ProfileRequest profileEntity = ProfileRequest.builder()
						.description("initial profileEntity ADMIN")
						.flAdmin("S")
						.name("Initial ADMIN")
						.status(ProfileStatusEnum.ATIVO.getValue())
						.roles(roles)
						.build();
		ProfileResponse profileResponse = profileService.save(profileEntity);
		profileEntity.setId(profileResponse.getId());

		/** create test user */
		List<ProfileRequest> profiles = new ArrayList<ProfileRequest>();
		profiles.add(profileEntity);
		UserRequest user = UserRequest.builder()
					.login("user")
					.name("initial user")
					.password("123456")
					.status(UserStatusEnum.ATIVO)
					.profiles(profiles)
					.build();
		UserResponse userResponse = this.userService.save(user);


		 return userResponse;
    }

}
