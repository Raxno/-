package pwa.Repositories;

import java.util.List;

import pwa.Entities.Models.Comment;

public interface CommentStorage {

	List<Comment> findAllComments(Long profileId);

	void add(Long photoId, Long authorId, String text);

	List<Comment> getCommentsByPhoto(Long photoId);

	void deleteByPhoto(Long photoId);
}
