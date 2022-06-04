package pwa.Entities.Rate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pwa.Repositories.MarkStorage;
import pwa.Repositories.PhotoStorage;
import pwa.Repositories.ProfileStorage;
import pwa.Entities.dto.RatingJsonDTO;
import pwa.Entities.Mail.MailService;
import pwa.Entities.Models.Mark;

@Service
public class RatingServiceDomain implements RatingService {

    @Autowired
    MarkStorage markStorage;

    @Autowired
    MailService mailService;

    @Autowired
    PhotoStorage photoStorage;

    @Autowired
    ProfileStorage profileStorage;

    @Override
    public List<Mark> getMarkListByPhoto(Long photoId) {
        return markStorage.getMarksByPhoto(photoId);
    }

    @Override
    public void addMark(Long photoId, Long authorId, int value) {
        markStorage.add(photoId, authorId, value);
        String emailProfile = profileStorage.findEmailById(photoStorage.getProfileIdByPhoto(photoId));
        String nicknameAuthor = profileStorage.getNicknameById(authorId);
        String nicknameUser = profileStorage.getNicknameById(photoStorage.getProfileIdByPhoto(photoId));

        if (!StringUtils.isEmpty(emailProfile)) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "Пользователь %s поставил вам рейтинг %s!",
                    nicknameUser,
                    nicknameAuthor,
                    value
            );
            mailService.send(emailProfile, "Adding grade", message);
        }
    }

    @Override
    public List<RatingJsonDTO> marksByPhotoAsJson(List<Mark> marks1) {
        List<Mark> marks = marks1;
        List<RatingJsonDTO> marksJson = null;

        if (marks != null && marks.size() > 0) {
            marksJson = new ArrayList<>(marks.size());
            for (Mark mark : marks) {
                RatingJsonDTO markDTO = new RatingJsonDTO();
                markDTO.setValue(mark.getValue());
                marksJson.add(markDTO);
            }
        }
        return marksJson;
    }

    @Override
    public void changeMark(Long photoId, Long authorId, int value) {
        markStorage.change(photoId, authorId, value);
        String emailProfile = profileStorage.findEmailById(photoStorage.getProfileIdByPhoto(photoId));
        String nicknameAuthor = profileStorage.getNicknameById(authorId);
        String nicknameUser = profileStorage.getNicknameById(photoStorage.getProfileIdByPhoto(photoId));

        if (!StringUtils.isEmpty(emailProfile)) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "%s изменил рейтинг на %s",
                    nicknameUser,
                    nicknameAuthor,
                    value
            );
            mailService.send(emailProfile, "Password recovery", message);
        }
    }

    @Override
    public Mark findMarkByUserAndPhoto(Long photoId, Long profileId) {
        return markStorage.getMarkByPhotoAndUser(photoId, profileId);
    }
}