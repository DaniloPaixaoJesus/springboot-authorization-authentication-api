package br.com.danilopaixao.test;

import static org.junit.Assert.assertEquals;
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
import br.com.danilopaixao.ws.role.RoleService;
import br.com.danilopaixao.ws.role.RoleStatusEnum;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
@Transactional(propagation=Propagation.REQUIRED)//default
public class ProfileServiceTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
    private ProfileService service;
	
	@Autowired
    private RoleService roleService;
	
	private ProfileRequest profileRequest;
	
	private RoleRequest roleRequest; 
	
	private final String PROFILE_NAME = "init ProfileServiceTest: profile created";
	private final String ROLE_NAME = "init ProfileServiceTest: role created";
	
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
			
			ProfileResponse profileResponse = service.save(profileRequest);
			profileRequest.setId(profileResponse.getId());
		}
		
		if(roleRequest == null) {
			roleRequest = RoleRequest.builder().build();
			roleRequest.setName(ROLE_NAME);
			roleRequest.setDescription("");
			roleRequest.setStatus(RoleStatusEnum.ATIVO);
			roleRequest.setProfiles(new ArrayList<ProfileRequest>());
			roleService.save(roleRequest);
		}
		
    }
	
	//@After
	public void finishTest() {
		//em.getTransaction().rollback();
		//it is not needed because of @Transactional
	}
	
	@Test
    public void testFindById() {
		Long idProfile = profileRequest.getId();
		ProfileResponse profile = service.getById(idProfile);
		assertNotNull(profile);
        assertEquals(profile.getId(), profileRequest.getId());
        assertEquals(profile.getDescription(), profileRequest.getDescription());
        assertEquals(profile.getFlAdmin(), profileRequest.getFlAdmin());
        assertEquals(profile.getName(), profileRequest.getName());
        assertEquals(profile.getStatus(), profileRequest.getStatus());
    }
	
	@Test
    public void testInsert(){
		
		ProfileRequest newProfileRequest = ProfileRequest.builder().build();
		newProfileRequest.setName("name NEW profile - junit running");
		newProfileRequest.setDescription("description NEW profile - junit running");
		newProfileRequest.setStatus(ProfileStatusEnum.ATIVO);
		newProfileRequest.setFlAdmin("S");
		newProfileRequest.setRoles(new ArrayList<RoleRequest>());
		
		ProfileResponse newProfile = service.save(newProfileRequest);
		
		ProfileResponse profile = service.getById(newProfile.getId());
		
        assertNotNull(profile);
        assertEquals(newProfile.getId(), profile.getId());
        assertEquals(newProfile.getDescription(), profile.getDescription());
        assertEquals(newProfile.getFlAdmin(), profile.getFlAdmin());
        assertEquals(newProfile.getName(), profile.getName());
        assertEquals(newProfile.getStatus(), profile.getStatus());
        assertTrue(newProfile.getRoles().size() == profile.getRoles().size());
        
    }
	
	@Test
    public void testInsertWithRole() {
		
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setName("name NEW profile - junit running");
		profileRequest.setDescription("description NEW profile - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO);
		profileRequest.setFlAdmin("S");
		profileRequest.setRoles(new ArrayList<RoleRequest>());
		profileRequest.getRoles().add(roleRequest);
		
		ProfileResponse newProfile = service.save(profileRequest);
		
		ProfileResponse profile = service.getById(newProfile.getId());
		
		assertNotNull(profile);
        assertEquals(newProfile.getId(), profile.getId());
        assertEquals(newProfile.getDescription(), profile.getDescription());
        assertEquals(newProfile.getFlAdmin(), profile.getFlAdmin());
        assertEquals(newProfile.getName(), profile.getName());
        assertEquals(newProfile.getStatus(), profile.getStatus());
        assertTrue(newProfile.getRoles().size() == profile.getRoles().size());
    }
	
	@Test
    public void testUpdate() {
		
		Long idProfile = profileRequest.getId();
		ProfileRequest profileRequest = ProfileRequest.builder().build();
		profileRequest.setId(idProfile);
		profileRequest.setName("name profile - UPDATED - junit running");
		profileRequest.setDescription("description profile - UPDATED - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO);
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
		profileRequest.setName("name profile- junit running");
		profileRequest.setDescription("description profile - junit running");
		profileRequest.setStatus(ProfileStatusEnum.ATIVO);
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
