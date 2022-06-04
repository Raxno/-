package pwa.Repositories;

import java.util.List;

import pwa.Entities.Models.Profile;

public interface ProfileStorage {
	
	int countByEmail(String email);
	
	void save(Profile u);
	
	Profile findByEmailAndEnabledTrue(String email);
	
	Long getIdByNickname(String nickname);
	
	Profile findByActivationCode(String code);
		
	List<Profile> findFriends(Long profileId);
	
	List<Profile> findFollowers(Long profileId);
	
	List<Profile> findSubscriptions(Long profileId);
	
	String getNicknameById(Long profileId);
	
	Profile loadById(Long profileId);
	
	Profile findByEmail(String email);
	
	String findEmailById(Long id);
	
	String getRole(Long id);
	
	String findNicknameByEmail(String email);
	
	void ban(Long id);
}
