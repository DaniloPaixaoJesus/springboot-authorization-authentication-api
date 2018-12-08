package br.com.danilopaixao.ws.master;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.danilopaixao.ws.detail.DetailEntityResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MasterEntityResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1517670884022709952L;
	
	private Long id;
	private String code;
	private String summary;
	private String description;
	private List<DetailEntityResponse> details;
	
	@JsonCreator
	public MasterEntityResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("code") final String code,
			@JsonProperty("summary") final String summary,
			@JsonProperty("description") final String description,
			@JsonProperty("details") final List<DetailEntityResponse> details) {
		this.id = id;		
		this.code = code;
		this.summary = summary;
		this.description = description;
		this.details = details;
	}
}