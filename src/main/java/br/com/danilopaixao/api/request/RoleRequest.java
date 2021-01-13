package br.com.danilopaixao.api.request;

import br.com.danilopaixao.core.role.RoleStatusEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3733146230737994444L;
	
	private Long id;
	private String name;
	private String description;
	private RoleStatusEnum status;
	
	@JsonCreator
	public RoleRequest(
			@JsonProperty("id") final Long id,
			@JsonProperty("name") final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("status") final RoleStatusEnum status) {
		this.id = id;
		this.name = name;
		this.name = name;		
		this.description = description;
		this.status = status;
	}

}
