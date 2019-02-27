package br.com.danilopaixao.ws.profile;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.danilopaixao.ws.role.RoleResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProfileResponse implements Serializable{

	private static final long serialVersionUID = 2572139086998939238L;

	private Long id;
	private String name;
	private String description;
	private String flAdmin;
	private ProfileStatusEnum status;
	private List<RoleResponse> roles;
	
	@JsonCreator
	public ProfileResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("flAdmin") final String flAdmin,
			@JsonProperty("status") final ProfileStatusEnum status,
			@JsonProperty("roles") List<RoleResponse> roles
			) {
		this.id = id;
		this.name = name;		
		this.description = description;
		this.flAdmin = flAdmin;
		this.status = status;
		this.roles = roles;
	}
	
}
