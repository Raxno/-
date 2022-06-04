package pwa.Repositories;

import java.util.List;

import pwa.Entities.Models.AccesLevel;
import pwa.Entities.Models.Album;

public interface AlbumStorage {
	
	List<Album> findAlbumsByUser(Long profile_id, int accesLevel);
	
	void insert(Long profileId, String albumName, int accesLevel);
	
	void update(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel);
	
	void delete(Long id);
	
	Album getAlbumByNameAndUser(String albumName, Long profileId);
	
	void setAccesLevel(Long albumId, int level);
	
	String getAlbumNameById(Long albumId);
	
}