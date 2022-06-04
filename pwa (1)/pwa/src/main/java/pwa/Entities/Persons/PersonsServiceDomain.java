package pwa.Entities.Persons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;
import pwa.Repositories.AlbumStorage;
import pwa.Repositories.ProfileStorage;
import pwa.Entities.dto.ProfileJsonDTO;
import pwa.Entities.dto.UserAttrsJsonDTO;
import pwa.Entities.Mail.MailService;
import pwa.Entities.Models.Album;
import pwa.Entities.Models.Profile;
import pwa.Entities.Models.Role;
import pwa.Forms.CodeForm;
import pwa.Forms.EmailForm;
import pwa.Forms.ProfileRegistrationForm;

@Service
public class PersonsServiceDomain implements PersonsService {

    @Autowired
    ProfileStorage profileStorage;

    @Autowired
    AlbumStorage albumStorage;

    @Autowired
    MailService mailService;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Override
    public boolean isUserWithEmailExist(String email) {
        return profileStorage.countByEmail(email) != 0 ? true : false;
    }

    @Override
    public boolean createUserFromRegistrationForm(ProfileRegistrationForm userForm) {

        System.out.println(userForm.toString());

        Profile u = new Profile();

        BeanUtils.copyProperties(userForm, u);
        HtmlUtils.htmlEscape(userForm.toString());
        u.setPassword(bCrypt.encode(userForm.getPassword()));
        HtmlUtils.htmlEscape(userForm.toString());
        u.getProfileRoles().add(Role.USER);
        HtmlUtils.htmlEscape(userForm.toString());
        //u.setActivationCode(UUID.randomUUID().toString());
        u.setActivationCode(null);
        u.setEnabled(true);
        HtmlUtils.htmlEscape(userForm.toString());

        profileStorage.save(u);
/*
        if (!StringUtils.isEmpty(u.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Добро пожаловать!. Пройдите по ссылке для активации: http://localhost:8080/activate/%s",
                    u.getNickname(),
                    u.getActivationCode()
            );
            // mailService.send(userForm.getEmail(), "Registration", message);
        }
*/
        return true;
    }

    @Override
    public boolean activateProfile(String code) {

        Profile user = profileStorage.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setEnabled(true);

        profileStorage.save(user);

        return true;
    }

    public Profile getProfileByEmail(String email) {
        return profileStorage.findByEmailAndEnabledTrue(email);
    }

    @Override
    public List<ProfileJsonDTO> usersByUserAsJson(Long profileId, String divId) {
        List<Profile> profiles = null;
        List<ProfileJsonDTO> profilesJson = null;

        switch (divId) {
            case "friends":
                profiles = profileStorage.findFriends(profileId);
                break;
            case "followers":
                profiles = profileStorage.findFollowers(profileId);
                break;
            case "subscriptions":
                profiles = profileStorage.findSubscriptions(profileId);
                break;
        }

        if (profiles != null && profiles.size() > 0) {
            profilesJson = new ArrayList<>(profiles.size());
            for (Profile profile : profiles) {
                ProfileJsonDTO profileDTO = new ProfileJsonDTO();

                profileDTO.setNickname(profile.getNickname());
                profileDTO.setFullName(profile.getFullName());

                profilesJson.add(profileDTO);
            }
        }

        return profilesJson;
    }

    @Override
    public Profile findById(Long profileId) {
        return (profileId != null) ? profileStorage.loadById(profileId) : null;
    }

    @Override
    public boolean passwordRecovery(EmailForm emailForm) {

        Profile profile = profileStorage.findByEmail(emailForm.getEmail());
        HtmlUtils.htmlEscape(emailForm.toString());
        Random random = new Random();
        Integer rage = 9999;
        Integer generator = 1000 + random.nextInt(rage - 1000);
        profile.setActivationCode(generator.toString());
        HtmlUtils.htmlEscape(emailForm.toString());

        profileStorage.save(profile);

        if (!StringUtils.isEmpty(profile.getEmail())) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "Добро пожаловать!. Код для восстановления пароля: %s",
                    profile.getEmail(),
                    profile.getActivationCode()
            );
            mailService.send(emailForm.getEmail(), "Password recovery", message);
        }

        return true;
    }

    @Override
    public boolean saveCodeForCodeForm(CodeForm codeForm) {

        Profile user = profileStorage.findByActivationCode(codeForm.getCode());
        HtmlUtils.htmlEscape(codeForm.toString());

        if (user == null) {
            return false;
        }

        user.setPassword(bCrypt.encode(codeForm.getPassword()));
        HtmlUtils.htmlEscape(codeForm.toString());
        user.setActivationCode(null);

        profileStorage.save(user);

        return true;

    }

    @Override
    public List<UserAttrsJsonDTO> attrsByUserAsJson(Long profileId, boolean owner) {
        List<UserAttrsJsonDTO> attrJson = null;
        attrJson = new ArrayList<>(1);
        UserAttrsJsonDTO attrDTO = new UserAttrsJsonDTO();
        attrDTO.setOwner(owner);
        attrDTO.setRole(profileStorage.getRole(profileId));
        attrJson.add(attrDTO);
        return attrJson;
    }

    @Secured("ADMIN")
    @Override
    public void banUser(Long profileId) {
        String emailProfile = profileStorage.findEmailById(profileId);
        String nickname = profileStorage.getNicknameById(profileId);

        profileStorage.ban(profileId);
        List<Album> albums = albumStorage.findAlbumsByUser(profileId, 10);
        for (int i = 0; i < albums.size(); i++) {
            albumStorage.setAccesLevel(albums.get(i).getId(), 10);
        }

        if (!StringUtils.isEmpty(emailProfile)) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Вас заблокировали за нарушение условий сайта.",
                    nickname
            );
            mailService.send(emailProfile, "Account lockout", message);
        }
    }
}
