package pwa.Entities.Albums;

import java.util.List;

import pwa.Entities.dto.AlbumJsonDTO;
import pwa.Entities.Models.AccesLevel;
import pwa.Entities.Models.Album;

public interface AlbumService {

	void updateAlbum(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel);

	void deleteAlbum(Long profileId, Long id);

	public List<AlbumJsonDTO> albumsByUserAsJson(List<Album> albums);

}
