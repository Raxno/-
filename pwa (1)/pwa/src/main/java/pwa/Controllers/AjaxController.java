package pwa.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import pwa.Repositories.AlbumStorage;
import pwa.Repositories.CommentStorage;
import pwa.Repositories.MarkStorage;
import pwa.Repositories.PhotoStorage;
import pwa.Repositories.ProfileStorage;
import pwa.Repositories.RelationshipsStorage;
import pwa.Repositories.TagStorage;
import pwa.Entities.Albums.AlbumService;
import pwa.Entities.Comments.CommentService;
import pwa.Entities.dto.AlbumJsonDTO;
import pwa.Entities.dto.CommentJsonDTO;
import pwa.Entities.dto.RatingJsonDTO;
import pwa.Entities.dto.PhotoJsonDTO;
import pwa.Entities.dto.ProfileJsonDTO;
import pwa.Entities.dto.TagJsonDTO;
import pwa.Entities.dto.UserAttrsJsonDTO;
import pwa.Entities.Rate.RatingService;
import pwa.Entities.Photos.PhotoService;
import pwa.Entities.Photos.PhotoServiceDomain;
import pwa.Entities.Persons.PersonsService;
import pwa.Entities.Friends.FriendsService;
import pwa.Entities.Tags.TagService;
import pwa.Configurations.ProfileDetailsImpl;

@RestController
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    RelationshipsStorage relationshipsStorage;

    @Autowired
    FriendsService friendsService;

    @Autowired
    ProfileStorage profileStorage;

    @Autowired
    PersonsService personsService;

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoStorage photoStorage;

    @Autowired
    TagStorage tagStorage;

    @Autowired
    TagService tagService;

    @Autowired
    CommentStorage commentStorage;

    @Autowired
    CommentService commentService;

    @Autowired
    RatingService ratingService;

    @Autowired
    MarkStorage markStorage;

    @Autowired
    AlbumService albumService;

    @Autowired
    AlbumStorage albumStorage;

    @Autowired
    PhotoServiceDomain photoServiceDomain;

    @RequestMapping(value = "/add-friend")
    public void addFriend(@RequestParam("n") String nickname) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nickname));
        Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
        if (loginProfileId != profileId) {
            friendsService.changeRelationship(loginProfileId, profileId);
        }
    }

    @RequestMapping(value = "/add-friend/button-text")
    public String addFriendButtonText(@RequestParam("n") String nickname) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nickname));
        Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
        return friendsService.buttonText(loginProfileId, profileId);
    }

    @RequestMapping(value = "/my-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserAttrsJsonDTO> showAddFriendButton(@RequestParam("n") String nick) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean owner = false;
        String loginNick = profileDetails.getNickname();
        if (loginNick.equals(HtmlUtils.htmlEscape(nick)))
            owner = true;
        return personsService.attrsByUserAsJson(profileStorage.getIdByNickname(loginNick), owner);
    }

    @RequestMapping(value = "/friend-list/{divId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProfileJsonDTO> friendList(@RequestParam("n") String nick, @PathVariable String divId) {
        return personsService.usersByUserAsJson(profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick)), HtmlUtils.htmlEscape(divId));
    }

    @RequestMapping(value = "/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhotoJsonDTO> photoList(@RequestParam("n") String nick) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick));
        Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
        return photoService.photosByUserAsJson(photoStorage.getPhotosByUser(profileId, friendsService.getAccesLevel(profileId, loginProfileId)));
    }

    @RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagJsonDTO> tagList(@RequestParam("id") Long photoId) {
        //return tagService.tagsByUserAsJson(tagStorage.getTagsByPhoto(photoId));
        return tagService.tagsByPhotoAsJson(tagStorage.getTagsByPhoto(photoId));
    }

    @RequestMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentJsonDTO> commentList(@RequestParam("id") Long photoId) {
        return commentService.commentsByPhotoAsJson(commentStorage.getCommentsByPhoto(photoId));
    }

    @RequestMapping(value = "/marks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RatingJsonDTO> markList(@RequestParam("id") Long photoId) {
        return ratingService.marksByPhotoAsJson(markStorage.getMarksByPhoto(photoId));
    }

    @RequestMapping(value = "/marks/rate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RatingJsonDTO> rate(@RequestParam("m") int mark, @RequestParam("id") Long photoId) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long authorId = profileStorage.getIdByNickname(profileDetails.getNickname());

        try {
            ratingService.findMarkByUserAndPhoto(photoId, authorId);
            ratingService.changeMark(photoId, authorId, mark);
        } catch (EmptyResultDataAccessException e) {
            ratingService.addMark(photoId, authorId, mark);
        }

        return ratingService.marksByPhotoAsJson(markStorage.getMarksByPhoto(photoId));
    }

    @RequestMapping(value = "/comments/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentJsonDTO> addComment(@RequestParam("id") Long photoId, @RequestParam("t") String text) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentService.addComment(photoId, profileStorage.getIdByNickname(profileDetails.getNickname()), HtmlUtils.htmlEscape(text));
        return commentService.commentsByPhotoAsJson(commentStorage.getCommentsByPhoto(photoId));
    }

    @RequestMapping(value = "/photos/delete")
    public void deletePhoto(@RequestParam("id") Long photoId) throws IOException {
        photoServiceDomain.deletePhoto(photoId);
    }

    @RequestMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlbumJsonDTO> albumList(@RequestParam("n") String nick) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick));
        Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
        return albumService.albumsByUserAsJson(albumStorage.findAlbumsByUser(profileId, friendsService.getAccesLevel(profileId, loginProfileId)));
    }

    @RequestMapping(value = "/albums/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAlbum(@RequestParam("id") Long id) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = profileStorage.getIdByNickname(profileDetails.getNickname());
        albumService.deleteAlbum(profileId, id);
    }

    @RequestMapping(value = "/photos/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhotoJsonDTO> photoListByAlbum(@PathVariable Long albumId) {
        return photoService.photosByUserAsJson(photoStorage.getPhotosByAlbum(albumId));
    }

    @RequestMapping(value = "/albums/create")
    public void createAlbum(@RequestParam("n") String name, @RequestParam("l") int accesLevel) throws IOException {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        albumStorage.insert(profileStorage.getIdByNickname(profileDetails.getNickname()), name, accesLevel);
    }

    @RequestMapping(value = "/albums/copy", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlbumJsonDTO> albumListCopy() {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = profileStorage.getIdByNickname(profileDetails.getNickname());
        return albumService.albumsByUserAsJson(albumStorage.findAlbumsByUser(profileId, friendsService.getAccesLevel(profileId, profileId)));
    }

    @RequestMapping(value = "/albums/copy-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public void copyPhotoInAlbum(@RequestParam("id") Long photoId, @RequestParam("a") Long albumId) {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            photoService.copyPhotoIn(profileStorage.getIdByNickname(profileDetails.getNickname()), photoId, albumId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhotoJsonDTO> searchByParametrs(@RequestParam("q") String query, @RequestParam("r") int rating, @RequestParam("d") String date) {
        return photoService.photosByUserAsJson(photoService.searchByParametrs(query, rating, date));
    }

    @RequestMapping(value = "/search/tag", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhotoJsonDTO> searchByTag(@RequestParam("t") String tag) {
        return photoService.photosByUserAsJson(photoService.searchByTag(tag));
    }

    @RequestMapping(value = "/ban")
    public void ban(@RequestParam("n") String nick) {
        Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick));
        personsService.banUser(profileId);
    }
}
