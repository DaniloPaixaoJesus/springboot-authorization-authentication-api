package br.com.danilopaixao.core.profile;

import br.com.danilopaixao.api.request.ProfileRequest;
import br.com.danilopaixao.api.response.ProfileResponse;

import java.util.List;

public interface ProfileService {

	ProfileResponse save(ProfileRequest profileEntity);
	ProfileResponse save(Long id, ProfileRequest profileEntity);
	ProfileResponse getById(Long id);
	List<ProfileResponse> getByAllProfiles();
	ProfileResponse inativeProfile(Long id);
	ProfileEntity getUserById(Long id);
}
