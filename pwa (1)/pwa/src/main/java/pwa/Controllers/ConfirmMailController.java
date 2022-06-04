package pwa.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pwa.Entities.Persons.PersonsService;

@Controller
public class ConfirmMailController {

    @Autowired
    PersonsService personsService;

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = personsService.activateProfile(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated"); // контакт с html
            return "confirm";
        } else {
            model.addAttribute("message", "Activation code is not found!");
            return "profile/registration";
        }

    }

    @GetMapping("/confirm")
    public String confirm() {
        return "confirm";
    }


}
