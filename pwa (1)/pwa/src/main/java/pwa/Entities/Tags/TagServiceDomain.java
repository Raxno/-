package pwa.Entities.Tags;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwa.Repositories.TagStorage;
import pwa.Entities.dto.TagJsonDTO;
import pwa.Entities.Models.Tag;

@Service
public class TagServiceDomain implements TagService{
	
	@Autowired
    TagStorage tagStorage;
	
	@Override
	public void addTags(Long photoId, String value) {
		value.replace(",", "");
		value.replace(".", "");
		value.replace(";", "");
		value.replace("#", "");
		String[] tags = value.split(" ");
		for(int i = 0; i < tags.length; i++) {
			if(!tags[i].isEmpty())
				tagStorage.add(photoId, tags[i]);
		}
	}

	@Override
	public void deleteTagsByPhoto(Long photoId) {
		tagStorage.delete(photoId);
	}

	@Override
	//public List<TagJsonDTO> tagsByPhotoAsJson(Long photoId) {
	public List<TagJsonDTO> tagsByPhotoAsJson(List<Tag> tags1) {
		//List<Tag> tags = tagStorage.getTagsByPhoto(photoId);
		List<Tag> tags = tags1;
		List<TagJsonDTO> tagsJson = null;
		
		if(tags != null && tags.size() > 0) {
			tagsJson = new ArrayList<>(tags.size());
			for(Tag tag : tags) {
				TagJsonDTO tagDTO = new TagJsonDTO();
				
				tagDTO.setTag(tag.getValue());
				
				tagsJson.add(tagDTO);
			}
		}
		return tagsJson;
	}
	
}
