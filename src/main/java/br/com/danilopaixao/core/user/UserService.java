package br.com.danilopaixao.core.user;

import br.com.danilopaixao.api.request.UserRequest;
import br.com.danilopaixao.api.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

	UserResponse save(UserRequest user);
	UserResponse save(Long id, UserRequest user);
	UserResponse getById(Long id);
	List<UserResponse> getByAllUsers();
	UserResponse inativeUser(Long id);
	UserEntity getUserById(Long id);
	UserResponse authenticate(String username);

}
