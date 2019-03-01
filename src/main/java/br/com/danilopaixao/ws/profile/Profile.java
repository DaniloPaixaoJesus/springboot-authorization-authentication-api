package br.com.danilopaixao.ws.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.danilopaixao.ws.core.BaseEntity;
import br.com.danilopaixao.ws.core.ContantsUtil;
import br.com.danilopaixao.ws.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="profile", schema = ContantsUtil.DB_SCHEMA)
public class Profile extends BaseEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7324091354016656912L;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fl_admin")
	private String flAdmin;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ProfileStatusEnum status;
	
//	@ManyToMany(mappedBy = "profiles")
//    private List<User> users = new ArrayList<User>();
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
			schema = ContantsUtil.DB_SCHEMA,
			name = "role_profile",
			joinColumns = { @JoinColumn(name="id_profile")},
			inverseJoinColumns = { @JoinColumn(name="id_role")}
	)
	private List<Role> roles = new ArrayList<Role>();
	
}
