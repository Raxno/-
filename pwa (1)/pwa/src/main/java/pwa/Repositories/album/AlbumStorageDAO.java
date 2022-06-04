package pwa.Repositories.album;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pwa.Repositories.AlbumStorage;
import pwa.Entities.Models.AccesLevel;
import pwa.Entities.Models.Album;

@Repository
public class AlbumStorageDAO implements AlbumStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Album> findAlbumsByUser(Long profile_id, int accesLevel) {
		StringBuilder sql = new StringBuilder("SELECT * FROM albums WHERE profile_id = ? AND acces_level <= ?");
		List<Album> album = jdbcTemplate.query(sql.toString(), new Object[] {profile_id, accesLevel}, new AlbumRowMapper());
		return album;
	}
	
	@Override
	public void insert(Long profileId, String albumName, int accesLevel) {
		String insertQuery = "INSERT INTO albums (profile_id, album_name, acces_level) VALUES (?, ?, ?)";
		Object[] data = new Object[] {profileId, albumName, accesLevel};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		if (rowAffected == 0) {
			logger.error("Error during update record for Album");
		}
	}
	
	@Override
	public void update(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
		String updateQuery = "UPDATE albums SET profile_id = ?, album_name = ?, number_of_photos = ?, acces_level = ? WHERE id = ?";
		Object[] data = new Object[] {profileId, albumName, numberOfPhotos, accesLevel, id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during update record for Album");
		}
	}

	@Override
	public void delete(Long id) {
		String updateQuery = "DELETE FROM albums WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Album");
		}
	}

	@Override
	public Album getAlbumByNameAndUser(String albumName, Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM albums WHERE profile_id = ? AND album_name = ?");
		Album album = jdbcTemplate.queryForObject(sql.toString(), new Object[] {profileId, albumName}, new AlbumRowMapper());
		return album;
	}

	@Override
	public void setAccesLevel(Long albumId, int level) {
		String updateQuery = "UPDATE albums SET acces_level = ? WHERE id = ?";
		Object[] data = new Object[] {level, albumId};
		int rowAffected = jdbcTemplate.update(updateQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during update record for Album");
		}
	}

	@Override
	public String getAlbumNameById(Long albumId) {

		StringBuilder sql = new StringBuilder("SELECT album_name FROM albums WHERE id = ?");
		
		String albumName = jdbcTemplate.queryForObject(sql.toString(), new Object[] {albumId}, String.class);

		return albumName;
	}
	
	
}
