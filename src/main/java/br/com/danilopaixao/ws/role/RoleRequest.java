package br.com.danilopaixao.ws.role;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3733146230737994444L;
	
	private String name;
	private String description;
	private RoleStatusEnum status;
	
	@JsonCreator
	public RoleRequest(
			@JsonProperty("name") final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("status") final RoleStatusEnum status) {
		this.name = name;		
		this.description = description;
		this.status = status;
	}
}
