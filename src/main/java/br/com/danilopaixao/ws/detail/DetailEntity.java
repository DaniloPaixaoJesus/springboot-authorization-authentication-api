package br.com.danilopaixao.ws.detail;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.danilopaixao.ws.core.ContantsUtil;
import br.com.danilopaixao.ws.master.MasterEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="detail", schema = ContantsUtil.DB_SCHEMA)
public class DetailEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4835910182046216415L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_master", referencedColumnName="id", nullable = false, updatable = true, insertable = true)
	private MasterEntity master;
	
	
}
