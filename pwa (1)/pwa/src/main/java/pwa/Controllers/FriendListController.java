package pwa.Controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pwa.Configurations.ProfileDetailsImpl;

@Controller
public class FriendListController {

    @GetMapping("/friend_list")
    public String friendList(Model model) {
        return "friend_list";
    }

    @GetMapping("/user/{nickname}/friend_list")
    public String friendList(Model model, @PathVariable String nickname) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("nickname", profileDetails.getNickname());
        model.addAttribute("nicknameView", nickname);
        return "friendList";
    }

}
