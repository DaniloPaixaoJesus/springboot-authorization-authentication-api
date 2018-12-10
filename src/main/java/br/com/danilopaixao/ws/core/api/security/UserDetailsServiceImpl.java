package br.com.danilopaixao.ws.core.api.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder encoder;

    public UserDetailImpl loadUserByUsername(String email) {
    	UserDetailImpl userDetail = new UserDetailImpl();
    	userDetail.setEmail("danilo.paixao@gingaone.com.br");
    	userDetail.setNome("Danilo Paixao de Jesus");
    	userDetail.setSenha(encoder.encode("123456"));
    	userDetail.setRoles(new ArrayList<RoleGrantedAuthority>());
    	userDetail.getRoles().add(new RoleGrantedAuthority("ROLE_ADMIN"));
        if(userDetail == null) {
            throw new UsernameNotFoundException("Usuário " + email + " não foi encontrado");
        }

        return userDetail;
    }
    
public UserDetails loadUserByUsername2(String username) throws UsernameNotFoundException {
		
		// hard coding the users. All passwords must be encoded.
		final List<UserDetailImpl> users = Arrays.asList(
			new UserDetailImpl(1L, "omar", encoder.encode("12345"), "USER"),
			new UserDetailImpl(2L, "admin", encoder.encode("12345"), "ADMIN")
		);
		

		for(UserDetailImpl appUser: users) {
			if(appUser.getUsername().equals(username)) {
				
				// Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
				// So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
				List<GrantedAuthority> grantedAuthorities = AuthorityUtils
		                	.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
				
				// The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
				// And used by auth manager to verify and check user authentication.
				return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
			}
		}
		
		// If user not found. Throw this exception.
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}
}
