package br.com.danilopaixao.core.role;

import br.com.danilopaixao.core.profile.ProfileEntity;
import br.com.danilopaixao.core.persistence.BaseEntity;
import br.com.danilopaixao.core.persistence.DataBaseConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role")
public class RoleEntity extends BaseEntity<Long> implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4326207161860945877L;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private RoleStatusEnum status;
	
	@ManyToMany(mappedBy = "roleEntities",
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProfileEntity> profileEntities = new ArrayList<ProfileEntity>();
	
	public List<ProfileEntity> getProfileEntities(){
		if(profileEntities == null) {
			return new ArrayList<ProfileEntity>();
		}else {
			return profileEntities;
		}
	}
	
}
