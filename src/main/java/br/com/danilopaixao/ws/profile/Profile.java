package br.com.danilopaixao.ws.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4835910182046216415L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fl_admin")
	private String flAdmin;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ProfileStatusEnum status;
	
	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(
			schema = ContantsUtil.DB_SCHEMA,
			name = "role_profile",
			joinColumns = { @JoinColumn(name="id_profile")},
			inverseJoinColumns = { @JoinColumn(name="id_role")}
	)
	private List<Role> roles = new ArrayList<Role>();
	
}
