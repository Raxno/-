package pwa.Repositories.photos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pwa.Entities.Models.Photo;

public class PhotoRowMapper  implements RowMapper<Photo>{
	@Override
	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo photo = new Photo();
		
		photo.setId(rs.getLong("id"));
		photo.setProfile_id(rs.getLong("profile_id"));
		photo.setAlbum_id(rs.getLong("album_id"));
		photo.setDescription(rs.getString("description"));
		photo.setDate(rs.getDate("date"));
		photo.setLink_photo(rs.getString("link_photo"));

		return photo;
	}
}