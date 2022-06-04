package pwa.Repositories;

import java.util.List;

import pwa.Entities.Models.Mark;

public interface MarkStorage {

	List<Mark> getMarksByPhoto(Long photoId);

	void add(Long photoId, Long authorId, int value);

	Mark getMarkByPhotoAndUser(Long photoId, Long profileId);

	void change(Long photoId, Long authorId, int value);

	float getRatingByPhoto(Long photoId);

	void deleteByPhoto(Long photoId);
}