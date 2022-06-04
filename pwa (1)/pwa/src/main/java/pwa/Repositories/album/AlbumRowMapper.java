package pwa.Repositories.album;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pwa.Entities.Models.AccesLevel;
import pwa.Entities.Models.Album;

public class AlbumRowMapper implements RowMapper<Album> {
	

	@Override
	public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
		Album album = new Album();
		
		album.setId(rs.getLong("id"));
		album.setProfileId(rs.getLong("profile_id"));
		album.setAlbumName(rs.getString("album_name"));
		int accesLevel = rs.getInt("acces_level");
		if(accesLevel == 0)
			album.setAccesLevel(AccesLevel.PUBLIC);
		if(accesLevel == 1)
			album.setAccesLevel(AccesLevel.FRIENDS);
		if(accesLevel == 2)
			album.setAccesLevel(AccesLevel.CLOSE);

        return album;

	}

}
