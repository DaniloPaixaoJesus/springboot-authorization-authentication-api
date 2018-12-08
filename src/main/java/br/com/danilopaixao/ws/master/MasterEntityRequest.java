package br.com.danilopaixao.ws.master;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.danilopaixao.ws.detail.DetailEntityRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MasterEntityRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1517670884022709952L;
	
	private String code;
	private String summary;
	private String description;
	private List<DetailEntityRequest> detailEntityRequests;
	
	@JsonCreator
	public MasterEntityRequest(
			@JsonProperty("code") final String code,
			@JsonProperty("summary") final String summary,
			@JsonProperty("description") final String description,
			@JsonProperty("detailEntity") final List<DetailEntityRequest> detailEntityRequests) {
		this.code = code;
		this.summary = summary;
		this.description = description;
		this.detailEntityRequests = detailEntityRequests;
	}
}
