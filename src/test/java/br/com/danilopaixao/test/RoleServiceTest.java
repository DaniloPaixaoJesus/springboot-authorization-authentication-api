package br.com.danilopaixao.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import br.com.danilopaixao.ws.role.RoleResponse;
import br.com.danilopaixao.ws.role.RoleService;
import br.com.danilopaixao.ws.role.RoleStatusEnum;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
@Transactional(propagation=Propagation.REQUIRED)//default
public class RoleServiceTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
    private RoleService service;
	
	@Autowired
    private ProfileService profileService;
	
	private ProfileRequest profileRequest;
	
	private RoleRequest roleRequest; 
	
	private final String PROFILE_NAME = "init RoleServiceTest: profile created";
	private final String ROLE_NAME = "init RoleServiceTest: role created";
	
	@Before
    public void initTest() {
		//it is not needed because of @Transactional
		//em.getTransaction().begin();
		if(profileRequest == null) {
			profileRequest = ProfileRequest.builder().build();
			profileRequest.setName(PROFILE_NAME);
			profileRequest.setDescription("");
			profileRequest.setFlAdmin("S");
			profileRequest.setStatus(ProfileStatusEnum.ATIVO);
			profileRequest.setRoles(new ArrayList<RoleRequest>());
			
			ProfileResponse profileResponse = profileService.save(profileRequest);
			profileRequest.setId(profileResponse.getId());
		}
		
		if(roleRequest == null) {
			roleRequest = RoleRequest.builder().build();
			roleRequest.setName(ROLE_NAME);
			roleRequest.setDescription("");
			roleRequest.setStatus(RoleStatusEnum.ATIVO);
			roleRequest.setProfiles(new ArrayList<ProfileRequest>());
			RoleResponse roleResponse = service.save(roleRequest);
			roleRequest.setId(roleResponse.getId());
		}
		
    }
	
	//@After
	public void finishTest() {
		//em.getTransaction().rollback();
		//it is not needed because of @Transactional
	}
	
	@Test
    public void testFindById() {
		RoleResponse role = service.getById(roleRequest.getId());
		assertNotNull(role);
        assertEquals(role.getId(), roleRequest.getId());
        assertEquals(role.getName(), roleRequest.getName());
        assertEquals(role.getDescription(), roleRequest.getDescription());
        assertEquals(role.getStatus(), roleRequest.getStatus());
    }
	
	@Test
    public void testInsert() {
		
		RoleRequest roleRequest = RoleRequest.builder().build();
		roleRequest.setName("name NEW role - junit running");
		roleRequest.setDescription("description NEW role - junit running");
		roleRequest.setStatus(RoleStatusEnum.ATIVO);
		roleRequest.setProfiles(new ArrayList<ProfileRequest>());
		
		RoleResponse roleResponse = service.save(roleRequest);
		
		RoleResponse role = service.getById(roleResponse.getId());
		
        assertNotNull(role);
        assertEquals(role.getId(), roleResponse.getId());
        assertEquals(role.getName(), roleResponse.getName());
        assertEquals(role.getDescription(), roleResponse.getDescription());
        assertEquals(role.getStatus(), roleResponse.getStatus());
        assertTrue(role.getProfiles().size() == roleResponse.getProfiles().size());
        
    }
	
	@Test
    public void testInsertWithProfile() {
		
		RoleRequest roleRequest = RoleRequest.builder().build();
		roleRequest.setName("name NEW role - junit running");
		roleRequest.setDescription("description NEW role - junit running");
		roleRequest.setStatus(RoleStatusEnum.ATIVO);
		roleRequest.setProfiles(new ArrayList<ProfileRequest>());
		roleRequest.getProfiles().add(profileRequest);
		
		RoleResponse roleResponse = service.save(roleRequest);
		
		RoleResponse role = service.getById(roleResponse.getId());
		
		assertNotNull(role);
        assertEquals(role.getId(), roleResponse.getId());
        assertEquals(role.getName(), roleResponse.getName());
        assertEquals(role.getDescription(), roleResponse.getDescription());
        assertEquals(role.getStatus(), roleResponse.getStatus());
        assertTrue(role.getProfiles().size() == roleResponse.getProfiles().size());
    }
	
	@Test
    public void testUpdate() {
		RoleRequest newRoleRequest = RoleRequest.builder().build();
		newRoleRequest.setId(this.roleRequest.getId());
		newRoleRequest.setName("name role - UPDATED - junit running");
		newRoleRequest.setDescription("description role - UPDATED - junit running");
		newRoleRequest.setStatus(RoleStatusEnum.ATIVO);
		newRoleRequest.setProfiles(new ArrayList<ProfileRequest>());
		
		RoleResponse roleResponse = service.save(newRoleRequest);
		
		RoleResponse role = service.getById(roleResponse.getId());
		
		assertNotNull(role);
        assertEquals(role.getId(), roleResponse.getId());
        assertEquals(role.getName(), roleResponse.getName());
        assertEquals(role.getStatus(), roleResponse.getStatus());
        assertNotEquals(role.getName(), this.roleRequest.getName());
        assertNotEquals(role.getDescription(), this.roleRequest.getDescription());
        assertEquals(role.getDescription(), roleResponse.getDescription());
        assertTrue(role.getProfiles().size() == roleResponse.getProfiles().size());
    }
	
	@Test
    public void testUpdateWithProfile() {
		RoleRequest newRoleRequest = RoleRequest.builder().build();
		newRoleRequest.setId(this.roleRequest.getId());
		newRoleRequest.setName("name role - UPDATED 2 - junit running");
		newRoleRequest.setDescription("description role - UPDATED 2 - junit running");
		newRoleRequest.setStatus(RoleStatusEnum.ATIVO);
		newRoleRequest.setProfiles(new ArrayList<ProfileRequest>());
		
		newRoleRequest.getProfiles().add(this.profileRequest);
		
		RoleResponse roleResponse = service.save(newRoleRequest);
		
		RoleResponse role = service.getById(roleResponse.getId());
		
		assertNotNull(role);
        assertEquals(role.getId(), roleResponse.getId());
        assertEquals(role.getName(), roleResponse.getName());
        assertEquals(role.getStatus(), roleResponse.getStatus());
        assertNotEquals(role.getName(), this.roleRequest.getName());
        assertNotEquals(role.getDescription(), this.roleRequest.getDescription());
        assertEquals(role.getDescription(), roleResponse.getDescription());
        assertTrue(role.getProfiles().size() == roleResponse.getProfiles().size());
        assertFalse(this.roleRequest.getProfiles().size() == roleResponse.getProfiles().size());
    }


}
