package br.com.danilopaixao.ws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopaixao.ws.profile.ProfileRequest;
import br.com.danilopaixao.ws.profile.ProfileResponse;
import br.com.danilopaixao.ws.profile.ProfileService;
import br.com.danilopaixao.ws.profile.ProfileStatusEnum;
import br.com.danilopaixao.ws.role.RoleRequest;
import br.com.danilopaixao.ws.role.RoleResponse;
import br.com.danilopaixao.ws.role.RoleService;
import br.com.danilopaixao.ws.role.RoleStatusEnum;
import br.com.danilopaixao.ws.user.UserRequest;
import br.com.danilopaixao.ws.user.UserResponse;
import br.com.danilopaixao.ws.user.UserService;
import br.com.danilopaixao.ws.user.UserStatusEnum;
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
    public @ResponseBody UserResponse initUsers() {
		
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
		
		/** create test profile */
		List<RoleRequest> roles = new ArrayList<RoleRequest>();
		roles.add(role);
		ProfileRequest profile = ProfileRequest.builder()
						.description("initial profile ADMIN")
						.flAdmin("S")
						.name("Initial ADMIN")
						.status(ProfileStatusEnum.ATIVO)
						.roles(roles)
						.build();
		ProfileResponse profileResponse = profileService.save(profile);
		profile.setId(profileResponse.getId());
		
		/** create test user */
		List<ProfileRequest> profiles = new ArrayList<ProfileRequest>();
		profiles.add(profile);
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
