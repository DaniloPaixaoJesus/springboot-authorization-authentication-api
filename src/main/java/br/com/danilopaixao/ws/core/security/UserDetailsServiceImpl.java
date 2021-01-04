package br.com.danilopaixao.ws.core.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.danilopaixao.ws.role.RoleResponse;
import br.com.danilopaixao.ws.role.RoleService;
import br.com.danilopaixao.ws.user.UserResponse;
import br.com.danilopaixao.ws.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
    
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserResponse appUser = userService.authenticate(username);
		List<RoleResponse> roles = roleService.getRoleByLogin(username);
		if(appUser.getLogin().equals(username)) {
			List<GrantedAuthority> grantedAuthorities =
				roles.stream()
					.map(r -> new SimpleGrantedAuthority(r.getName()))
					.collect(Collectors.toList());
			return new User(appUser.getLogin(), appUser.getPassword(), grantedAuthorities);
		}
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}

}
