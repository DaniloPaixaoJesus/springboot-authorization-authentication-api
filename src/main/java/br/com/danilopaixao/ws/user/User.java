package br.com.danilopaixao.ws.user;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name="user", schema = ContantsUtil.DB_SCHEMA)
public class User extends BaseEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -206160057837677423L;

	@Column(name = "name")
	private String name;
	
	@Column(name = "login")
	private String login;
	
	@Column(name = "password")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private UserStatusEnum status;
	
	@ManyToMany//(cascade = { CascadeType.MERGE })
	@JoinTable(
			schema = ContantsUtil.DB_SCHEMA,
			name = "profile_user",
			joinColumns = { @JoinColumn(name="id_user")},
			inverseJoinColumns = { @JoinColumn(name="id_profile")}
	)
	private List<Profile> profiles = new ArrayList<Profile>();
	
}
