package pwa.Entities.Comments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pwa.Repositories.CommentStorage;
import pwa.Repositories.PhotoStorage;
import pwa.Repositories.ProfileStorage;
import pwa.Entities.dto.CommentJsonDTO;
import pwa.Entities.Mail.MailService;
import pwa.Entities.Models.Comment;

@Service
public class CommentServiceDomain implements CommentService {

    @Autowired
    CommentStorage commentStorage;

    @Autowired
    ProfileStorage profileStorage;

    @Autowired
    PhotoStorage photoStorage;

    @Autowired
    MailService mailService;

    @Override
    public List<Comment> getListComments(Long photoId) {
        return commentStorage.findAllComments(photoId);
    }

    @Override
    public List<CommentJsonDTO> commentsByPhotoAsJson(List<Comment> comments1) {
        List<Comment> comments = comments1;
        List<CommentJsonDTO> commentsJson = null;

        if (comments != null && comments.size() > 0) {
            commentsJson = new ArrayList<>(comments.size());
            for (Comment commet : comments) {
                CommentJsonDTO commetnDTO = new CommentJsonDTO();

                commetnDTO.setAuthorNickname(profileStorage.getNicknameById(commet.getAuthorId()));
                commetnDTO.setText(commet.getText());
                commetnDTO.setDate(commet.getDate());

                commentsJson.add(commetnDTO);
            }
        }
        return commentsJson;
    }

    @Override
    public void addComment(Long photoId, Long authorId, String text) {
        commentStorage.add(photoId, authorId, text);
        String emailProfile = profileStorage.findEmailById(photoStorage.getProfileIdByPhoto(photoId));
        String nicknameAuthor = profileStorage.getNicknameById(authorId);
        String nicknameUser = profileStorage.getNicknameById(photoStorage.getProfileIdByPhoto(photoId));

        if (!StringUtils.isEmpty(emailProfile)) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "Пользователь %s оставил комментарий под одной из ваших фото: '%s'",
                    nicknameUser,
                    nicknameAuthor,
                    text
            );
            mailService.send(emailProfile, "Adding a comment", message);
        }

    }

}
