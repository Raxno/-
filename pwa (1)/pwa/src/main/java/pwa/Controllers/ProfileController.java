package pwa.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pwa.Repositories.PhotoStorage;
import pwa.Repositories.ProfileStorage;
import pwa.Repositories.RelationshipsStorage;
import pwa.Entities.Persons.PersonsService;
import pwa.Entities.Friends.FriendsService;
import pwa.Configurations.ProfileDetailsImpl;

@Controller
public class ProfileController {

    @Autowired
    PersonsService personsService;

    @Autowired
    ProfileStorage profileStorage;

    @Autowired
    PhotoStorage photoStorage;

    @Autowired
    RelationshipsStorage relationshipsStorage;

    @Autowired
    FriendsService relationshipsServise;

    @GetMapping("/user/{nickname}")
    public String profile(Model model, @PathVariable String nickname) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = profileStorage.getIdByNickname(nickname);
        model.addAttribute("nickname", profileDetails.getNickname());
        model.addAttribute("nicknameView", nickname);
        model.addAttribute("publications", photoStorage.countPublicationsByUser(profileId));
        model.addAttribute("friends", relationshipsStorage.findFriends(profileId).size());
        model.addAttribute("followers", relationshipsStorage.findFollowers(profileId).size());
        model.addAttribute("subscribes", relationshipsStorage.findSubscriptions(profileId).size());
        model.addAttribute("profileId", profileId);
        model.addAttribute("deleteFriend", "Delete friend");

        return "profile/profile";
    }

}
