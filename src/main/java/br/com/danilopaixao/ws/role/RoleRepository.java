package br.com.danilopaixao.ws.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query(value=" select   " + 
					"  r.*  " + 
					" from public.role r  " + 
					"  inner join public.role_profile rp on rp.id_role = r.id  " + 
					"  inner join public.profile_user pu on pu.id_profile = rp.id_profile  " + 
					"  inner join public.user u on u.id = pu.id_user  " + 
					" where u.login = :login", 
			nativeQuery = true)
	public List<Role> findByLogin(@Param("login") String login);
}
