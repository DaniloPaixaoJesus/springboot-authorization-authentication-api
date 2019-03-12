package br.com.danilopaixao.ws.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE login = :login")
	public User findByLogin(@Param("login") String login);
}
