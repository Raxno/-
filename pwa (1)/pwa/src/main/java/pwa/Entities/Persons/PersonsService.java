package pwa.Entities.Persons;

import java.util.List;

import pwa.Entities.dto.ProfileJsonDTO;
import pwa.Entities.dto.UserAttrsJsonDTO;
import pwa.Entities.Models.Profile;
import pwa.Forms.CodeForm;
import pwa.Forms.EmailForm;
import pwa.Forms.ProfileRegistrationForm;

public interface PersonsService {

	boolean isUserWithEmailExist(String email);

	boolean createUserFromRegistrationForm(ProfileRegistrationForm userForm);
	
	Profile getProfileByEmail(String email);
	
	boolean activateProfile(String code);
	
	public List<ProfileJsonDTO> usersByUserAsJson(Long profileId, String divId);
	
	Profile findById(Long profileId);
	
	public boolean passwordRecovery(EmailForm emailForm);
		
	public boolean saveCodeForCodeForm(CodeForm codeForm);
	
	public List<UserAttrsJsonDTO> attrsByUserAsJson(Long profileId, boolean owner);

	void banUser(Long profileId);
}
