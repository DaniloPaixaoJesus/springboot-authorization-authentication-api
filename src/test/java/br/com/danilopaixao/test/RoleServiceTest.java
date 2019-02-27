package br.com.danilopaixao.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.APIRestApplication;
import br.com.danilopaixao.ws.profile.ProfileRequest;
import br.com.danilopaixao.ws.profile.ProfileStatusEnum;
import br.com.danilopaixao.ws.role.RoleRequest;
import br.com.danilopaixao.ws.role.RoleResponse;
import br.com.danilopaixao.ws.role.RoleService;
import br.com.danilopaixao.ws.role.RoleStatusEnum;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
//@Transactional(propagation=Propagation.REQUIRED)//default
public class RoleServiceTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
    private RoleService service;
	
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
		Long idRole = 9999991L;
		RoleResponse role = service.getById(idRole);
		assertNotNull(role);
        assertEquals(role.getId(), idRole);
    }
	
	//@Test
    public void testInsertWithProfile() {
		
		ProfileRequest profile1 = ProfileRequest.builder().build();
		profile1.setId(9999991L); 
		
		RoleRequest roleRequest = RoleRequest.builder().build();
		roleRequest.setName("name NEW role - junit running");
		roleRequest.setDescription("description NEW role - junit running");
		roleRequest.setStatus(RoleStatusEnum.ATIVO);
		roleRequest.setProfiles(new ArrayList<ProfileRequest>());
		roleRequest.getProfiles().add(profile1);
		
		RoleResponse roleResponse = service.save(roleRequest);
		
		RoleResponse role = service.getById(roleResponse.getId());
		
        assertNotNull(role);
        assertEquals(roleResponse.getId(), role.getId());
    }
	
	@Test
    public void testUpdateWithProfile() {
		ProfileRequest profile1 = ProfileRequest.builder().build();
		profile1.setId(9999991L);
		profile1.setName("name role 9999991L - junit running");
		profile1.setDescription("description role 9999991L - junit running");
		profile1.setStatus(ProfileStatusEnum.ATIVO);
		
		Long idRole = 101L;
		RoleRequest roleRequest = RoleRequest.builder().build();
		roleRequest.setId(idRole);
		roleRequest.setName("name role- junit running");
		roleRequest.setDescription("description role - junit running");
		roleRequest.setStatus(RoleStatusEnum.ATIVO);
		roleRequest.setProfiles(new ArrayList<ProfileRequest>());
		
		roleRequest.getProfiles().add(profile1);
		
		RoleResponse roleResponseSaved = service.save(roleRequest);
		
		RoleResponse roleResponseGet = service.getById(roleResponseSaved.getId());
		
        assertNotNull(roleResponseGet);
        assertEquals(roleResponseSaved.getId(), roleResponseGet.getId());
        //assertArrayEquals(roleResponseSaved.getProfiles().toArray(), roleResponseGet.getProfiles().toArray());
    }


}
