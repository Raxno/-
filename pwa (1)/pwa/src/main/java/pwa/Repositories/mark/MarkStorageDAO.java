package pwa.Repositories.mark;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pwa.Repositories.MarkStorage;
import pwa.Entities.Models.Mark;

@Repository
public class MarkStorageDAO implements MarkStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Mark> getMarksByPhoto(Long photoId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM marks WHERE photo_id = ?");
		List<Mark> marks = jdbcTemplate.query(sql.toString(), new Object[] {photoId}, new MarkRowMapper());
		return marks;
	}

	@Override
	public void add(Long photoId, Long authorId, int value) {
		String insertQuery = "INSERT INTO marks (photo_id, author_id, value) VALUES (?, ?, ?)";
		Object[] data = new Object[] {photoId, authorId, value};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Marks");
		}
	}

	@Override
	public Mark getMarkByPhotoAndUser(Long photoId, Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM marks WHERE photo_id = ? AND author_id = ?");
		Mark mark = jdbcTemplate.queryForObject(sql.toString(), new Object[] {photoId, profileId}, new MarkRowMapper());
		return mark;
	}

	@Override
	public void change(Long photoId, Long authorId, int value) {
		String updateQuery = "UPDATE marks SET value = ? WHERE photo_id = ? AND author_id = ?";
		Object[] data = new Object[] {value, photoId, authorId};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Marks");
		}
	}

	@Override
	public float getRatingByPhoto(Long photoId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM marks WHERE photo_id = ?");
		List<Mark> marks = jdbcTemplate.query(sql.toString(), new Object[] {photoId}, new MarkRowMapper());
		Float rating = (float) 0;
		for(int i = 0; i < marks.size(); i++) {
			rating += marks.get(i).getValue();
		}
		rating = rating/marks.size();
		if(rating.isNaN())
			return -1;
		return rating;
	}

	@Override
	public void deleteByPhoto(Long photoId) {
		String updateQuery = "DELETE FROM marks WHERE photo_id = ?";
		Object[] data = new Object[] {photoId};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Marks");
		}
	}
	
}
