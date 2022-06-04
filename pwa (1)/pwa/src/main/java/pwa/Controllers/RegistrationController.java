package pwa.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pwa.Entities.Persons.PersonsService;
import pwa.Forms.ProfileRegistrationForm;
import pwa.Forms.Validators.ProfileRegistrationFormValidator;

@Controller
public class RegistrationController {

    @Autowired
    PersonsService personsService;

    @Autowired
    private ProfileRegistrationFormValidator profileValidator;


    @InitBinder("profileForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(profileValidator);
    }

    @GetMapping("/profile/registration")
    public String registration(Model model, ProfileRegistrationForm userForm) {

        model.addAttribute("profileForm", userForm);

        return "profile/registration";
    }

    @PostMapping("/profile/registration")
    public String registrationPost(Model model, @Valid @ModelAttribute("profileForm") ProfileRegistrationForm profileForm,
                                   BindingResult binding) {
        System.out.println(profileForm.toString());

        personsService.createUserFromRegistrationForm(profileForm);
        return "redirect:/login";  // registration_notification РАНЬШЕ)

    }

    @GetMapping("/registration_notification")
    public String regNot() {
        return "registrationNotification";
    }


}
