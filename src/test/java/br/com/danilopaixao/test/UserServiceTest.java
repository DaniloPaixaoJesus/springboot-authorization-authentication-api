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
import br.com.danilopaixao.ws.user.UserRequest;
import br.com.danilopaixao.ws.user.UserResponse;
import br.com.danilopaixao.ws.user.UserService;
import br.com.danilopaixao.ws.user.UserStatusEnum;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
@Transactional(propagation=Propagation.REQUIRED)//default
public class UserServiceTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
    private UserService service;
	
	@Autowired
    private ProfileService profileService;
	
	private final String PROFILE_NAME = "init UserServiceTest: profile created";
	
	private ProfileRequest profileRequest;
	private UserRequest userRequest;
	
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
		if (userRequest == null) {
			userRequest = UserRequest.builder().build();
			userRequest.setLogin("init User - login - junit running");
			userRequest.setName("init User - name - junit running");
			userRequest.setPassword("sadasdq423krjc32ipod4u0u3reuwopuntperg");
			userRequest.setStatus(UserStatusEnum.ATIVO);
			userRequest.setProfiles(new ArrayList<ProfileRequest>());
			
			UserResponse userResponse = service.save(userRequest);
			userRequest.setId(userResponse.getId());
		}
    }
	
	//@After
	public void finishTest() {
		//em.getTransaction().rollback();
		//it is not needed because of @Transactional
	}
	
	@Test
    public void testFindById() {
		UserResponse user = service.getById(userRequest.getId());
		assertNotNull(user);
        assertEquals(user.getId(), userRequest.getId());
        assertEquals(user.getLogin(), userRequest.getLogin());
        assertEquals(user.getName(), userRequest.getName());
        assertEquals(user.getStatus(), userRequest.getStatus());
    }
    
    @Test
    public void testInsert() {
		UserRequest newUserRequest = UserRequest.builder().build();
		newUserRequest.setLogin("login NEW user - junit running");
		newUserRequest.setName("name NEW user - junit running");
		newUserRequest.setPassword("ngpovw3pntu3w984y59324ndry2348tv-3498yt-t432");
		newUserRequest.setStatus(UserStatusEnum.ATIVO);
		newUserRequest.setProfiles(new ArrayList<ProfileRequest>());
		
		UserResponse userResponse = service.save(newUserRequest);
		
		UserResponse user = service.getById(userResponse.getId());
		
        assertNotNull(user);
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getLogin(), newUserRequest.getLogin());
        assertEquals(user.getName(), newUserRequest.getName());
        assertEquals(user.getStatus(), newUserRequest.getStatus());
    }
	
	@Test
    public void testInsertWithProfile() {
		UserRequest newUserRequest = UserRequest.builder().build();
		newUserRequest.setLogin("login NEW user w/ Profile - junit running");
		newUserRequest.setName("name NEW user w/ Profile - junit running");
		newUserRequest.setPassword("95t34y5y24087f5byoq43ybfiouwehrfjawhirudqwhiuroq2f");
		newUserRequest.setStatus(UserStatusEnum.ATIVO);
		newUserRequest.setProfiles(new ArrayList<ProfileRequest>());
		newUserRequest.getProfiles().add(profileRequest);
		
		UserResponse userResponse = service.save(newUserRequest);
		
		UserResponse user = service.getById(userResponse.getId());
		
		assertNotNull(user);
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getLogin(), newUserRequest.getLogin());
        assertEquals(user.getName(), newUserRequest.getName());
        assertEquals(user.getStatus(), newUserRequest.getStatus());
        assertTrue(user.getProfiles().size() == userResponse.getProfiles().size());
    }
	
	@Test
    public void testUpdate() {
		Long idUser = userRequest.getId();
		UserRequest newUserRequest = UserRequest.builder().build();
		newUserRequest.setId(idUser);
		newUserRequest.setLogin("init User - UPDATED - login - junit running");
		newUserRequest.setName("init User - UPDATED - name - junit running");
		newUserRequest.setPassword("34urq4jrdq489dhq2ur982qu5hphftiruhregg");
		newUserRequest.setStatus(UserStatusEnum.ATIVO);
		newUserRequest.setProfiles(new ArrayList<ProfileRequest>());
		
		UserResponse userResponseSaved = service.save(idUser, newUserRequest);
		
		UserResponse user = service.getById(idUser);
		
		assertNotNull(user);
        assertEquals(user.getId(), newUserRequest.getId());
        assertEquals(user.getLogin(), newUserRequest.getLogin());
        assertEquals(user.getName(), newUserRequest.getName());
        assertEquals(user.getStatus(), newUserRequest.getStatus());
        assertTrue(userResponseSaved.getProfiles().size() == user.getProfiles().size());
    }
	
	@Test
    public void testUpdateWithProfile() {
		Long idProfile = profileRequest.getId();
		ProfileRequest profile1 = ProfileRequest.builder().build();
		profile1.setId(idProfile); 
		
		Long idUser = userRequest.getId();;
		UserRequest newUserRequest = UserRequest.builder().build();
		newUserRequest.setId(idUser);
		newUserRequest.setLogin("init User - UPDATED - login - junit running");
		newUserRequest.setName("init User - UPDATED - name - junit running");
		newUserRequest.setPassword("348ruq824udr8q2j4923d9n52390f5023q4ud09-q23dj4-0923d402");
		newUserRequest.setStatus(UserStatusEnum.ATIVO);
		newUserRequest.setProfiles(new ArrayList<ProfileRequest>());
		newUserRequest.getProfiles().add(profile1);
		
		UserResponse userResponseSaved = service.save(idUser, newUserRequest);
		
		UserResponse user = service.getById(idUser);
		
		assertNotNull(user);
        assertEquals(user.getId(), newUserRequest.getId());
        assertEquals(user.getLogin(), newUserRequest.getLogin());
        assertEquals(user.getName(), newUserRequest.getName());
        assertEquals(user.getStatus(), newUserRequest.getStatus());
        assertTrue(userResponseSaved.getProfiles().size() == user.getProfiles().size());
    }


}
