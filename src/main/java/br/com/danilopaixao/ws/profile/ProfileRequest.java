package br.com.danilopaixao.ws.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.danilopaixao.ws.role.RoleRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ProfileRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3733146230737994444L;
	
	private Long id;
	private String name;
	private String description;
	private String flAdmin;
	private List<RoleRequest> roles = new ArrayList<RoleRequest>();
	private ProfileStatusEnum status;
	
	@JsonCreator
	public ProfileRequest(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("flAdmin") final String flAdmin,
			@JsonProperty("roles") final List<RoleRequest> roles,
			@JsonProperty("status") final ProfileStatusEnum status) {
		this.id = id;
		this.name = name;		
		this.description = description;
		this.flAdmin = flAdmin;
		this.roles = roles;
		this.status = status;
	}
}
