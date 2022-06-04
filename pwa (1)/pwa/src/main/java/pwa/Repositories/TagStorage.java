package pwa.Repositories;

import java.util.List;

import pwa.Entities.Models.Tag;

public interface TagStorage {

	void add(Long photoId, String value);

	void delete(Long photoId);

	List<Tag> getTagsByPhoto(Long photoId);

	void deleteByPhoto(Long photoId);

}
