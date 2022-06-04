package pwa.Repositories.comment;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pwa.Entities.Models.Comment;

public class CommentRowMapper implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comment comment = new Comment();
		
		comment.setId(rs.getLong("id"));
		comment.setPhotoId(rs.getLong("photo_id"));
		comment.setAuthorId(rs.getLong("author_id"));
		comment.setText(rs.getString("text"));
		comment.setDate(rs.getDate("date"));
		
		return comment;
	}

}
