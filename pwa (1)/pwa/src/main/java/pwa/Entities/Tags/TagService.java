package pwa.Entities.Tags;

import java.util.List;

import pwa.Entities.dto.TagJsonDTO;
import pwa.Entities.Models.Tag;

public interface TagService {

	void addTags(Long photoId, String tags);

	void deleteTagsByPhoto(Long photoId);

	public List<TagJsonDTO> tagsByPhotoAsJson(List<Tag> tags);

}
