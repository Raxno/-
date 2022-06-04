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
import pwa.Forms.CodeForm;
import pwa.Forms.EmailForm;
import pwa.Forms.Validators.EmailFormValidator;

@Controller
public class PasswordController {

    @Autowired
    PersonsService personsService;

    @Autowired
    private EmailFormValidator emailFormValidator;

    @InitBinder("emailForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(emailFormValidator);
    }

    @GetMapping("/recovery")
    public String recovery(Model model, EmailForm emailForm) {

        model.addAttribute("emailForm", emailForm);

        return "passwordRecovery";
    }

    @PostMapping("/recovery")
    public String recoveryPost(Model model, @Valid @ModelAttribute("emailForm") EmailForm emailForm,
                               BindingResult binding) {

        if (binding.hasErrors()) {
            model.addAttribute("emailForm", emailForm);
            return "passwordRecovery";
        }

        personsService.passwordRecovery(emailForm);

        return "redirect:/change_password";
    }

    @GetMapping("/change_password")
    public String changePassword(Model model, CodeForm codeForm) {

        model.addAttribute("codeForm", codeForm);

        return "changePass";
    }

    @PostMapping("/change_password")
    public String changePasswordPost(Model model, @ModelAttribute("codeForm") CodeForm codeForm,
                                     BindingResult binding) {

        if (binding.hasErrors()) {
            model.addAttribute("codeForm", codeForm);
            return "changePass";
        }

        personsService.saveCodeForCodeForm(codeForm);

        return "redirect:/login";
    }

}
