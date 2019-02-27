package br.com.danilopaixao.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.APIRestApplication;
import br.com.danilopaixao.ws.profile.ProfileRequest;
import br.com.danilopaixao.ws.profile.ProfileResponse;
import br.com.danilopaixao.ws.profile.ProfileService;
import br.com.danilopaixao.ws.profile.ProfileStatusEnum;
import br.com.danilopaixao.ws.role.RoleRequest;
import br.com.danilopaixao.ws.role.RoleStatusEnum;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
@Transactional(propagation=Propagation.REQUIRED)//default
public class ProfileServiceTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
    private ProfileService service;
	
	//@Before
    public void initTest() {
		//em.getTransaction().begin();
    	//it is not needed because of @Transactional
    }
	
	//@After
	public void finishTest() {
		//em.getTransaction().rollback();
		//it is not needed because of @Transactional
	}
	
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
		role1.setId(9999991L); 
		
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setName("name NEW profile - junit running");
		profileRequest.setDescription("description NEW profile - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO);
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		profileRequest.getRoles().add(role1);
		
		ProfileResponse profileResponse = service.save(profileRequest);
		
		ProfileResponse profile = service.getById(profileResponse.getId());
		
        assertNotNull(profile);
        assertEquals(profileResponse.getId(), profile.getId());
    }
	
	@Test
    public void testUpdate() {
		RoleRequest role1 = RoleRequest.builder().build();
		role1.setId(9999991L);
		role1.setName("name role 9999991L - junit running");
		role1.setDescription("description role 9999991L - junit running");
		role1.setStatus(RoleStatusEnum.ATIVO);
		
		Long idProfile = 9999991L;
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setId(idProfile);
		profileRequest.setName("name profile- junit running");
		profileRequest.setDescription("description profile - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO);
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		
		profileRequest.getRoles().add(role1);
		
		ProfileResponse profileResponseSaved = service.save(profileRequest);
		
		ProfileResponse profileResponseGet = service.getById(profileResponseSaved.getId());
		
        assertNotNull(profileResponseGet);
        assertEquals(profileResponseSaved.getId(), profileResponseGet.getId());
        assertArrayEquals(profileResponseSaved.getRoles().toArray(), profileResponseGet.getRoles().toArray());
    }


}
