package br.com.danilopaixao.api.response;

import br.com.danilopaixao.core.role.RoleStatusEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RoleResponse implements Serializable{

	private static final long serialVersionUID = 2572139086998939238L;

	private Long id;
	private String name;
	private String description;
	private RoleStatusEnum status;
	private List<ProfileResponse> profiles;
	
	@JsonCreator
	public RoleResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("status") final RoleStatusEnum status,
			@JsonProperty("profiles") final List<ProfileResponse> profiles) {
		this.id = id;
		this.name = name;		
		this.description = description;
		this.status = status;
		this.profiles = profiles;
	}
	
	public List<ProfileResponse> getProfiles(){
		if(profiles == null) {
			return new ArrayList<ProfileResponse>();
		}else {
			return profiles;
		}
	}
	
}
