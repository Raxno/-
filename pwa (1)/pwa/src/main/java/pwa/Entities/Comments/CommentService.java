package pwa.Entities.Comments;

import java.util.List;

import pwa.Entities.dto.CommentJsonDTO;
import pwa.Entities.Models.Comment;

public interface CommentService {
	
	List<Comment> getListComments(Long photoId);
	
	public List<CommentJsonDTO> commentsByPhotoAsJson(List<Comment> comments);
	
	public void addComment(Long photoId, Long authorId, String text);
}
