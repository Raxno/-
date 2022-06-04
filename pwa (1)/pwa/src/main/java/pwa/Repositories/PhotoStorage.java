package pwa.Repositories;

import java.util.List;

import pwa.Entities.Models.Photo;

public interface PhotoStorage {
	
	Photo getPhotoById(Long id);
	
	List<Photo> getPhotosByUser(Long profileId, int acces);
	
	List<Photo> getPhotosByAlbum(Long albumId);
	
	List<Photo> getPhotosByRating(float rating);
	
	void upload(Long profileId, Long albumId, String description, String link);
	
	void delete(Long id);
	
	int countPublicationsByUser(Long profileId);
	
	Long getProfileIdByPhoto(Long photoId);
	
	Photo getPhotoByLink(String link);
	
	int getAccesLevel(Long photoId);
	
	List<Photo> getPhotosByParametrs(String query, String date);
	
	List<Photo> getPhotosByTag(String tag);
	
}
