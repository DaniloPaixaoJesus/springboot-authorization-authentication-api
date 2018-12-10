package br.com.danilopaixao.ws.detail;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3471504460137480458L;
	
	private Long id;
	private Long masterId;
	private String description;
	
	@JsonCreator
	public DetailResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("masterId") final Long masterId,
			@JsonProperty("description") final String description) {
		this.id = id;		
		this.masterId = masterId;
		this.description = description;
	}
	
}