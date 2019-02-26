package br.com.danilopaixao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.danilopaixao.ws.APIRestApplication;
import br.com.danilopaixao.ws.profile.ProfileRequest;
import br.com.danilopaixao.ws.profile.ProfileResponse;
import br.com.danilopaixao.ws.profile.ProfileService;
import br.com.danilopaixao.ws.profile.ProfileStatusEnum;
import br.com.danilopaixao.ws.role.RoleRequest;
import br.com.danilopaixao.ws.role.RoleStatusEnum;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
public class ProfileServiceTest {
	
	@Autowired
    private ProfileService service;
	
	@Autowired
	
	@Test
    public void testFindById() {
		Long idProfile = 9999991L;
		ProfileResponse profile = service.getById(idProfile);
		assertNotNull(profile);
        assertEquals(profile.getId(), idProfile);
    }
	
	@Test
    public void testInsert() {
		
		RoleRequest role1 = RoleRequest.builder().build();
		//role1.setId(9999991L); //DÁ ERRO PARA ROLES JÁ EXISTENTES 
		//Caused by: org.hibernate.PersistentObjectException: detached entity passed to persist: br.com.danilopaixao.ws.role.Role
		// funciona quando retira o cascade ALL, porem, ele nao cria um registro novo, como faz o CASCADE.ALL
		// analisar vantagens e desvantagens
		// prefiro deixar SEM o CASCADE.ALL, pois, sempre será necessário criar a ROLE antes de vinculá-la ao PROFILE
		role1.setName("name role- junit running");
		role1.setDescription("description role - junit running");
		role1.setStatus(RoleStatusEnum.ATIVO);
  
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setName("name profile- junit running");
		profileRequest.setDescription("description profile - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO);
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		profileRequest.getRoles().add(role1);
		
		ProfileResponse profileResponse = service.save(profileRequest);
		
		ProfileResponse profile = service.getById(profileResponse.getId());
		
        assertNotNull(profile);
        assertEquals(profileResponse.getId(), profile.getId());
    }
	
	//@Test
    public void testUpdate() {
		RoleRequest role6 = RoleRequest.builder().build();
		role6.setId(6L);
		role6.setName("name role- junit running");
		role6.setDescription("description role - junit running");
		role6.setStatus(RoleStatusEnum.ATIVO);
		
		RoleRequest role8 = RoleRequest.builder().build();
		role8.setId(8L);
		role8.setName("name role- junit running");
		role8.setDescription("description role - junit running");
		role8.setStatus(RoleStatusEnum.ATIVO);
		
		RoleRequest role10 = RoleRequest.builder().build();
		role10.setId(10L);
		role10.setName("name role- junit running");
		role10.setDescription("description role - junit running");
		role10.setStatus(RoleStatusEnum.ATIVO);
		
		RoleRequest role12 = RoleRequest.builder().build();
		role12.setId(12L);
		role12.setName("name role- junit running");
		role12.setDescription("description role - junit running");
		role12.setStatus(RoleStatusEnum.ATIVO);
		
		RoleRequest newRole = RoleRequest.builder().build();
		newRole.setName("name role- junit running");
		newRole.setDescription("description role - junit running");
		newRole.setStatus(RoleStatusEnum.ATIVO);
  
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setName("name profile- junit running");
		profileRequest.setDescription("description profile - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO);
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		
		profileRequest.getRoles().add(role6);
		profileRequest.getRoles().add(role8);
		profileRequest.getRoles().add(role10);
		profileRequest.getRoles().add(role12);
		profileRequest.getRoles().add(newRole);
		
		Long idProfile = 9999991L;
		ProfileResponse profileResponse = service.save(idProfile, profileRequest);
		
		ProfileResponse profile = service.getById(profileResponse.getId());
		
        assertNotNull(profile);
        assertEquals(profileResponse.getId(), profile.getId());
    }


}
