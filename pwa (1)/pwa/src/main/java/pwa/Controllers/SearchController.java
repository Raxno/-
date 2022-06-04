package pwa.Controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pwa.Configurations.ProfileDetailsImpl;


@Controller
public class SearchController {

    @GetMapping("/search")
    public String search(Model model) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("nickname", profileDetails.getNickname());
        return "resultSearch";
    }
}
