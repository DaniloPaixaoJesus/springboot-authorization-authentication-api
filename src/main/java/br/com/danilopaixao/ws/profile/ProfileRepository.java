package br.com.danilopaixao.ws.profile;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProfileRepository extends JpaRepository<Profile, Long> {
}
