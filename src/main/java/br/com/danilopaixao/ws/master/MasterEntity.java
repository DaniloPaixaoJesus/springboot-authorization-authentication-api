package br.com.danilopaixao.ws.master;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.danilopaixao.ws.detail.DetailEntity;
import br.com.danilopaixao.ws.user.User;
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
@Table(name="master")
public class MasterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4835910182046216415L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code")
	private String code;
	
	@Column(name = "summary")
	private String summary;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "master")
	//@JoinColumn(name = "id_master")
	private List<DetailEntity> details;
	
}
