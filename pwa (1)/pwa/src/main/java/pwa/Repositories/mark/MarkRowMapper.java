package pwa.Repositories.mark;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pwa.Entities.Models.Mark;

public class MarkRowMapper implements RowMapper<Mark>{
	@Override
	public Mark mapRow(ResultSet rs, int rowNum) throws SQLException {
		Mark mark = new Mark();
		
		mark.setId(rs.getLong("id"));
		mark.setPhoto_id(rs.getLong("photo_id"));
		mark.setAuthor_id(rs.getLong("author_id"));
		mark.setValue(rs.getInt("value"));

		return mark;
	}
}
