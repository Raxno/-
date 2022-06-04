package pwa.Repositories.tag;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pwa.Entities.Models.Tag;

public class TagRowMapper implements RowMapper<Tag> {
	@Override
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tag tag = new Tag();
		
		tag.setId(rs.getLong("id"));
		tag.setPhoto_id(rs.getLong("photo_id"));
		tag.setValue(rs.getString("value"));

		return tag;
	}

}
