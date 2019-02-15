package br.com.danilopaixao.ws.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	private String name;
	private String description;
	private String flAdmin;
	private ProfileStatusEnum status;
	
	@JsonCreator
	public ProfileRequest(
			@JsonProperty("name") final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("flAdmin") final String flAdmin,
			@JsonProperty("status") final ProfileStatusEnum status) {
		this.name = name;		
		this.description = description;
		this.flAdmin = flAdmin;
		this.status = status;
	}
}
