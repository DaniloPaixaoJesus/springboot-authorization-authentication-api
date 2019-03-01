package br.com.danilopaixao.ws.profile;

import java.util.List;

public interface ProfileService {

	ProfileResponse save(ProfileRequest profile);
	ProfileResponse save(Long id, ProfileRequest profile);
	ProfileResponse getById(Long id);
	List<ProfileResponse> getByAllProfiles();
	ProfileResponse inativeProfile(Long id);
	Profile getUserById(Long id);

}
