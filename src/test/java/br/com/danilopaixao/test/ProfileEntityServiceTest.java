package br.com.danilopaixao.test;

import br.com.danilopaixao.APIRestApplication;
import br.com.danilopaixao.api.request.ProfileRequest;
import br.com.danilopaixao.api.request.RoleRequest;
import br.com.danilopaixao.api.response.ProfileResponse;
import br.com.danilopaixao.api.response.RoleResponse;
import br.com.danilopaixao.core.profile.ProfileService;
import br.com.danilopaixao.core.profile.ProfileStatusEnum;
import br.com.danilopaixao.core.role.RoleService;
import br.com.danilopaixao.core.role.RoleStatusEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
//@Transactional(propagation=Propagation.REQUIRED)//default
public class ProfileEntityServiceTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
    private ProfileService service;
	
	@Autowired
    private RoleService roleService;
	
	private ProfileRequest profileRequest;
	
	private RoleRequest roleRequest; 
	
	private final String PROFILE_NAME = "init ProfileServiceTest: profileEntity created";
	private final String ROLE_NAME = "init ProfileServiceTest: role created";
	
	@Before
    public void initTest() {
		//em.getTransaction().begin();
		if(profileRequest == null) {
			profileRequest = ProfileRequest.builder().build();
			profileRequest.setName(PROFILE_NAME);
			profileRequest.setDescription("");
			profileRequest.setFlAdmin("S");
			profileRequest.setStatus(ProfileStatusEnum.ATIVO.getValue());
			profileRequest.setRoles(new ArrayList<RoleRequest>());
			
			ProfileResponse profileResponse = service.save(profileRequest);
			profileRequest.setId(profileResponse.getId());
		}
		
		if(roleRequest == null) {
			roleRequest = RoleRequest.builder().build();
			roleRequest.setName(ROLE_NAME);
			roleRequest.setDescription("");
			roleRequest.setStatus(RoleStatusEnum.ATIVO);
			RoleResponse roleResponse = roleService.save(roleRequest);
			roleRequest.setId(roleResponse.getId());
		}
		
    }
	
	@After
	public void finishTest() {
		//em.getTransaction().rollback();
		//it is not needed because of @Transactional
	}
	
	@Test
    public void testFindById() {
		Long idProfile = profileRequest.getId();
		ProfileResponse profileEntity = service.getById(idProfile);
		assertNotNull(profileEntity);
        assertEquals(profileEntity.getId(), profileRequest.getId());
        assertEquals(profileEntity.getDescription(), profileRequest.getDescription());
        assertEquals(profileEntity.getFlAdmin(), profileRequest.getFlAdmin());
        assertEquals(profileEntity.getName(), profileRequest.getName());
        assertEquals(profileEntity.getStatus(), profileRequest.getStatus());
    }
	
	@Test
    public void testInsert(){
		
		ProfileRequest newProfileRequest = ProfileRequest.builder().build();
		newProfileRequest.setName("name NEW profileEntity - junit running");
		newProfileRequest.setDescription("description NEW profileEntity - junit running");
		newProfileRequest.setStatus(ProfileStatusEnum.ATIVO.getValue());
		newProfileRequest.setFlAdmin("S");
		newProfileRequest.setRoles(new ArrayList<RoleRequest>());
		
		ProfileResponse newProfile = service.save(newProfileRequest);
		
		ProfileResponse profileEntity = service.getById(newProfile.getId());
		
        assertNotNull(profileEntity);
        assertEquals(newProfile.getId(), profileEntity.getId());
        assertEquals(newProfile.getDescription(), profileEntity.getDescription());
        assertEquals(newProfile.getFlAdmin(), profileEntity.getFlAdmin());
        assertEquals(newProfile.getName(), profileEntity.getName());
        assertEquals(newProfile.getStatus(), profileEntity.getStatus());
        assertTrue(newProfile.getRoles().size() == profileEntity.getRoles().size());
        
    }
	
	@Test
    public void testInsertWithRole() {
		
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setName("name NEW profileEntity - junit running");
		profileRequest.setDescription("description NEW profileEntity - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO.getValue());
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		profileRequest.getRoles().add(roleRequest);
		
		ProfileResponse newProfile = service.save(profileRequest);
		
		ProfileResponse profileEntity = service.getById(newProfile.getId());
		
		assertNotNull(profileEntity);
        assertEquals(newProfile.getId(), profileEntity.getId());
        assertEquals(newProfile.getDescription(), profileEntity.getDescription());
        assertEquals(newProfile.getFlAdmin(), profileEntity.getFlAdmin());
        assertEquals(newProfile.getName(), profileEntity.getName());
        assertEquals(newProfile.getStatus(), profileEntity.getStatus());
        assertTrue(newProfile.getRoles().size() == profileEntity.getRoles().size());
    }
	
	@Test
    public void testUpdate() {
		
		Long idProfile = profileRequest.getId();
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setId(idProfile);
		profileRequest.setName("name profileEntity - UPDATED - junit running");
		profileRequest.setDescription("description profileEntity - UPDATED - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO.getValue());
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		
		ProfileResponse profileResponseSaved = service.save(idProfile, profileRequest);
		
		ProfileResponse profileResponseGet = service.getById(profileResponseSaved.getId());
		
		assertNotNull(profileResponseGet);
        assertEquals(profileResponseSaved.getId(), profileResponseGet.getId());
        assertEquals(profileResponseSaved.getDescription(), profileResponseGet.getDescription());
        assertEquals(profileResponseSaved.getFlAdmin(), profileResponseGet.getFlAdmin());
        assertEquals(profileResponseSaved.getName(), profileResponseGet.getName());
        assertEquals(profileResponseSaved.getStatus(), profileResponseGet.getStatus());
        assertTrue(profileResponseSaved.getRoles().size() == profileResponseGet.getRoles().size());
    }
	
	@Test
    public void testUpdateWithRole() {
		Long idProfile = profileRequest.getId();
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setId(idProfile);
		profileRequest.setName("name profileEntity- junit running");
		profileRequest.setDescription("description profileEntity - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO.getValue());
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		
		profileRequest.getRoles().add(roleRequest);
		
		ProfileResponse profileResponseSaved = service.save(profileRequest);
		
		ProfileResponse profileResponseGet = service.getById(profileResponseSaved.getId());
		
		assertNotNull(profileResponseGet);
        assertEquals(profileResponseSaved.getId(), profileResponseGet.getId());
        assertEquals(profileResponseSaved.getDescription(), profileResponseGet.getDescription());
        assertEquals(profileResponseSaved.getFlAdmin(), profileResponseGet.getFlAdmin());
        assertEquals(profileResponseSaved.getName(), profileResponseGet.getName());
        assertEquals(profileResponseSaved.getStatus(), profileResponseGet.getStatus());
        assertTrue(profileResponseSaved.getRoles().size() == profileResponseGet.getRoles().size());
    }


}
