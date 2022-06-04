package pwa.Entities.Albums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pwa.Repositories.AlbumStorage;
import pwa.Repositories.PhotoStorage;
import pwa.Repositories.ProfileStorage;
import pwa.Entities.dto.AlbumJsonDTO;
import pwa.Entities.Mail.MailService;
import pwa.Entities.Models.AccesLevel;
import pwa.Entities.Models.Album;
import pwa.Entities.Models.Photo;
import pwa.Entities.Photos.PhotoService;

@Service
public class AlbumServiceDomain implements AlbumService {

    @Autowired
    AlbumStorage albumStorage;

    @Autowired
    PhotoStorage photoStorage;

    @Autowired
    PhotoService photoService;

    @Autowired
    MailService mailService;

    @Autowired
    ProfileStorage profileStorage;

    @Override
    public void updateAlbum(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
        albumStorage.update(id, profileId, albumName, numberOfPhotos, accesLevel);
    }

    @Override
    public void deleteAlbum(Long profileId, Long id) {
        String emailProfile = profileStorage.findEmailById(profileId);
        String nicknameAuthor = profileStorage.findNicknameByEmail(emailProfile);
        String nameAlbum = albumStorage.getAlbumNameById(id);

        List<Photo> photos = photoStorage.getPhotosByAlbum(id);
        for (int i = 0; i < photos.size(); i++) {
            photoService.deletePhoto(photos.get(i).getId());
        }
        albumStorage.delete(id);

        if (!StringUtils.isEmpty(emailProfile)) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "Альбом удален: %s.",
                    nicknameAuthor,
                    nameAlbum
            );
            mailService.send(emailProfile, "Delete album", message);
        }

    }

    @Override
    public List<AlbumJsonDTO> albumsByUserAsJson(List<Album> albums) {
        List<AlbumJsonDTO> albumsJson = null;

        if (albums != null && albums.size() > 0) {
            albumsJson = new ArrayList<>(albums.size());
            for (Album album : albums) {
                AlbumJsonDTO albumDTO = new AlbumJsonDTO();

                albumDTO.setId(album.getId());
                albumDTO.setName(album.getAlbumName());

                albumsJson.add(albumDTO);
            }
        }
        return albumsJson;
    }
}
