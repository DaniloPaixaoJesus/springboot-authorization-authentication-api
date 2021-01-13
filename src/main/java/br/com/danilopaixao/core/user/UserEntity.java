package br.com.danilopaixao.core.user;

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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class UserEntity extends BaseEntity<Long> implements Serializable{

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
			schema = DataBaseConstants.DB_SCHEMA,
			name = "profile_user",
			joinColumns = { @JoinColumn(name="id_user")},
			inverseJoinColumns = { @JoinColumn(name="id_profile")}
	)
	private List<ProfileEntity> profileEntities = new ArrayList<ProfileEntity>();
	
}
