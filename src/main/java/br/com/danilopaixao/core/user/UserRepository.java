package br.com.danilopaixao.core.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	@Query("SELECT u FROM UserEntity u WHERE login = :login")
	public UserEntity findByLogin(@Param("login") String login);
}
