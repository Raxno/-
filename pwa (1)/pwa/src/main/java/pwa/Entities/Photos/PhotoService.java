package pwa.Entities.Photos;

import java.io.IOException;
import java.util.List;

import pwa.Entities.dto.PhotoJsonDTO;
import pwa.Entities.Models.Photo;

public interface PhotoService {
	
	void uploadPhoto(Long profileId, Long albumId, String description, String link);
	
	void deletePhoto(Long id);
	
	boolean copyPhotoIn(Long profileId, Long photoId, Long albumId)  throws IOException;
	
	List<PhotoJsonDTO> photosByUserAsJson(List<Photo> photos);
	
	List<Photo> searchByParametrs(String query, int rating, String date);
	
	List<Photo> searchByTag(String tag);
}