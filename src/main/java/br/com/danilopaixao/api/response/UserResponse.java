package br.com.danilopaixao.api.response;

import br.com.danilopaixao.core.user.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponse implements Serializable{

	private static final long serialVersionUID = 2572139086998939238L;

	private Long id;
	private String name;
	private String login;
	private String password;
	private List<ProfileResponse> profiles = new ArrayList<ProfileResponse>();
	private UserStatusEnum status;
	
	@JsonCreator
	public UserResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("login") final String login,
			@JsonProperty("password") final String password,
			@JsonProperty("profiles") final List<ProfileResponse> profiles,
			@JsonProperty("status") final UserStatusEnum status) {
		this.id = id;
		this.name = name;		
		this.login = login;
		this.password = password;
		this.profiles = profiles;
		this.status = status;
	}
	
	public List<ProfileResponse> getProfiles(){
		if(this.profiles == null) {
			return new ArrayList<ProfileResponse>();
		}else {
			return this.profiles;
		}
	}
}
