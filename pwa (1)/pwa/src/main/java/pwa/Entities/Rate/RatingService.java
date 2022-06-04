package pwa.Entities.Rate;

import java.util.List;

import pwa.Entities.dto.RatingJsonDTO;
import pwa.Entities.Models.Mark;

public interface RatingService {
	
	List<Mark> getMarkListByPhoto(Long photoId);
	
	void addMark(Long photoId, Long authorId, int value);
	
	public List<RatingJsonDTO> marksByPhotoAsJson(List<Mark> marks);
	
	void changeMark(Long photoId, Long authorId, int value);
	
	Mark findMarkByUserAndPhoto(Long photoId, Long profileId);
}

