package pwa.Forms.Validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pwa.Entities.Persons.PersonsService;
import pwa.Forms.EmailForm;

@Component
public class EmailFormValidator implements Validator {
	
	@Autowired
	private PersonsService personsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return EmailForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		EmailForm form = (EmailForm)target;
		
		if(!personsService.isUserWithEmailExist(form.getEmail())) {
			errors.rejectValue("email", "form.changePass.email.notexist", "User with email already not exists");
		}
		
	}

}
