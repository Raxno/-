package pwa.Forms.Validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pwa.Entities.Persons.PersonsService;
import pwa.Forms.ProfileRegistrationForm;

@Component
public class ProfileRegistrationFormValidator implements Validator {
	
	@Autowired
	private PersonsService personsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfileRegistrationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ProfileRegistrationForm form = (ProfileRegistrationForm)target;
		
		if(personsService.isUserWithEmailExist(form.getEmail())) {
			errors.rejectValue("email", "form.user.email.exist", "User with email already exists");
		}
		
	}
	
}
