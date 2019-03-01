package br.com.danilopaixao.ws.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

import br.com.danilopaixao.ws.profile.ProfileResponse;

@Data
@Builder
public class UserResponse implements Serializable{

	private static final long serialVersionUID = 2572139086998939238L;

	private Long id;
	private String name;
	private String login;
	private List<ProfileResponse> profiles = new ArrayList<ProfileResponse>();
	private UserStatusEnum status;
	
	@JsonCreator
	public UserResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("login") final String login,
			@JsonProperty("profiles") final List<ProfileResponse> profiles,
			@JsonProperty("status") final UserStatusEnum status) {
		this.id = id;
		this.name = name;		
		this.login = login;
		this.profiles = profiles;
		this.status = status;
	}
	
}
