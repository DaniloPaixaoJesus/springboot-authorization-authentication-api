package br.com.danilopaixao.ws.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.danilopaixao.ws.core.BaseEntity;
import br.com.danilopaixao.ws.core.ContantsUtil;
import br.com.danilopaixao.ws.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role", schema = ContantsUtil.DB_SCHEMA)
public class Role extends BaseEntity<Long> implements Serializable{

	
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
	
	@ManyToMany(mappedBy = "roles")
    private List<Profile> profiles = new ArrayList<Profile>();
	
}
