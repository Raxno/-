package pwa.Repositories.comment;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pwa.Repositories.CommentStorage;
import pwa.Entities.Models.Comment;

@Repository
public class CommentStorageDAO implements CommentStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Comment> findAllComments(Long profileId) {//удалить
		StringBuilder sql = new StringBuilder("SELECT * FROM comments WHERE photo_id = ? ORDER BY date");
		List<Comment> comments = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new CommentRowMapper());
		
		return comments;
	}

	@Override
	public void add(Long photoId, Long authorId, String text) {
		String insertQuery = "INSERT INTO comments (photo_id, author_id, text, date) VALUES (?, ?, ?, now())";
		Object[] data = new Object[] {photoId, authorId, text};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Comments");
		}
	}

	@Override
	public List<Comment> getCommentsByPhoto(Long photoId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM comments WHERE photo_id = ? ORDER BY date");
		List<Comment> comments = jdbcTemplate.query(sql.toString(), new Object[] {photoId}, new CommentRowMapper());
		
		return comments;
	}

	@Override
	public void deleteByPhoto(Long photoId) {
		String updateQuery = "DELETE FROM comments WHERE photo_id = ?";
		Object[] data = new Object[] {photoId};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Comments");
		}
	}

}
