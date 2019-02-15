package br.com.danilopaixao.ws.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse implements Serializable{

	private static final long serialVersionUID = 2572139086998939238L;

	private Long id;
	private String name;
	private String description;
	private String flAdmin;
	private ProfileStatusEnum status;
	
	@JsonCreator
	public ProfileResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("flAdmin") final String flAdmin,
			@JsonProperty("status") final ProfileStatusEnum status) {
		this.id = id;
		this.name = name;		
		this.description = description;
		this.flAdmin = flAdmin;
		this.status = status;
	}
	
}
