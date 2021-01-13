package br.com.danilopaixao.core.profile;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
}
