package pwa.Repositories.relationships;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pwa.Entities.Models.Relationships;
import pwa.Entities.Models.Status;

public class RelationshipsRowMapper implements RowMapper<Relationships> {
	@Override
	public Relationships mapRow(ResultSet rs, int rowNum) throws SQLException {
		Relationships relationships = new Relationships();
		
		relationships.setId(rs.getLong("id"));
		relationships.setProfile_id(rs.getLong("profile_id"));
		relationships.setTarget_id(rs.getLong("target_id"));
		Status status = null;
		if(rs.getInt("status") == 1)
			status = Status.SUBSCRIBER;
		if(rs.getInt("status") == 2)
			status = Status.FRIEND;
		relationships.setStatus(status);

		return relationships;
	}
}
