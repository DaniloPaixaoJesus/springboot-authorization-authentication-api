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
import br.com.danilopaixao.ws.user.UserRequest;
import br.com.danilopaixao.ws.user.UserResponse;
import br.com.danilopaixao.ws.user.UserService;
import br.com.danilopaixao.ws.user.UserStatusEnum;

@SpringBootTest(classes = APIRestApplication.class)
@RunWith(SpringRunner.class)
//@Transactional(propagation=Propagation.REQUIRED)//default
public class UserServiceTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
    private UserService service;
	
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
		Long idUser = 9999991L;
		UserResponse user = service.getById(idUser);
		assertNotNull(user);
        assertEquals(user.getId(), idUser);
    }
	
	@Test
    public void testInsertWithProfile() {
		
		ProfileRequest profile1 = ProfileRequest.builder().build();
		profile1.setId(9999991L); 
		
		UserRequest userRequest = UserRequest.builder().build();
		userRequest.setLogin("login NEW user - junit running");
		userRequest.setName("name NEW user - junit running");
		userRequest.setPassword("name NEW user - junit running");
		userRequest.setStatus(UserStatusEnum.ATIVO);
		userRequest.setProfiles(new ArrayList<ProfileRequest>());
		userRequest.getProfiles().add(profile1);
		
		UserResponse userResponse = service.save(userRequest);
		
		UserResponse user = service.getById(userResponse.getId());
		
        assertNotNull(user);
        assertEquals(userResponse.getId(), user.getId());
    }
	
	@Test
    public void testUpdateWithProfile() {
		ProfileRequest profile1 = ProfileRequest.builder().build();
		profile1.setId(9999991L);
		profile1.setName("name profile 9999991L - junit running");
		profile1.setDescription("description profile 9999991L - junit running");
		profile1.setStatus(ProfileStatusEnum.ATIVO);
		profile1.setFlAdmin("S");
		
		Long idUser = 1L;
		UserRequest userRequest = UserRequest.builder().build();
		userRequest.setId(idUser);
		userRequest.setLogin("login user - junit running");
		userRequest.setName("name user- junit running");
		userRequest.setPassword("name NEW user - junit running");
		userRequest.setStatus(UserStatusEnum.ATIVO);
		userRequest.setProfiles(new ArrayList<ProfileRequest>());
		
		userRequest.getProfiles().add(profile1);
		
		UserResponse userResponseSaved = service.save(userRequest);
		
		UserResponse userResponseGet = service.getById(userResponseSaved.getId());
		
        assertNotNull(userResponseGet);
        assertEquals(userResponseSaved.getId(), userResponseGet.getId());
        //assertArrayEquals(userResponseSaved.getProfiles().toArray(), userResponseGet.getProfiles().toArray());
    }


}
