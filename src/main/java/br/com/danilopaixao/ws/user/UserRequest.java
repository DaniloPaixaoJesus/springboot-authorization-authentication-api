package br.com.danilopaixao.ws.user;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.danilopaixao.ws.profile.ProfileRequest;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3733146230737994444L;
	
	private Long id;
	private String name;
	private String login;
	private String password;
	private List<ProfileRequest> profiles;
	private UserStatusEnum status;
	
	@JsonCreator
	public UserRequest(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("login") final String login,
			@JsonProperty("password") final String password,
			@JsonProperty("profiles") final List<ProfileRequest> profiles,
			@JsonProperty("status") final UserStatusEnum status) {
		this.id = id;
		this.name = name;		
		this.login = login;
		this.password = password;
		this.profiles = profiles;
		this.status = status;
	}
}
