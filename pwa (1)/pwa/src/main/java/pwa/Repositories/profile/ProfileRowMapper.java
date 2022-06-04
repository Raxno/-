package pwa.Repositories.profile;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pwa.Entities.Models.Profile;
import pwa.Entities.Models.Role;


public class ProfileRowMapper implements RowMapper<Profile> {
	
	@Override
	public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
		Profile profile = new Profile();
		
		profile.setId(rs.getLong("id"));
		profile.setEmail(rs.getString("email"));
		profile.setFirstName(rs.getString("firstName"));
		profile.setLastName(rs.getString("lastName"));
		profile.setNickname(rs.getString("nickname"));
		profile.setPassword(rs.getString("password"));
		profile.setEnabled(rs.getBoolean("enabled"));
		profile.setToken(rs.getString("token"));

        String roles = rs.getString("roles");
        if (roles.contains(",")) {
                for (String role : roles.split(",")) {
                	profile.getProfileRoles().add(Role.valueOf(role));
                }
        } else {
        	profile.getProfileRoles().add(Role.valueOf(roles));
        }

		return profile;

	}
}