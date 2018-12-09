package br.com.danilopaixao.ws.master;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.danilopaixao.ws.detail.DetailResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MasterResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1517670884022709952L;
	
	private Long id;
	private String code;
	private String summary;
	private String description;
	private List<DetailResponse> details;
	
	@JsonCreator
	public MasterResponse(
			@JsonProperty("id") final Long id,
			@JsonProperty("code") final String code,
			@JsonProperty("summary") final String summary,
			@JsonProperty("description") final String description,
			@JsonProperty("details") final List<DetailResponse> details) {
		this.id = id;		
		this.code = code;
		this.summary = summary;
		this.description = description;
		this.details = details;
	}
}