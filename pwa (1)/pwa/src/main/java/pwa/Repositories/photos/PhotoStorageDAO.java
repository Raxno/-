package pwa.Repositories.photos;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pwa.Repositories.PhotoStorage;
import pwa.Entities.Models.Photo;

@Repository
public class PhotoStorageDAO implements PhotoStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Photo getPhotoById(Long id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE id = ?");
		Photo photo = jdbcTemplate.queryForObject(sql.toString(), new Object[] {id}, new PhotoRowMapper());
		return photo;
	}
	
	@Override
	public List<Photo> getPhotosByUser(Long profileId, int accesLevel) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos INNER JOIN albums ON photos.album_id=albums.id "
				+ "WHERE photos.profile_id = ? AND albums.acces_level <= ? ORDER BY photos.id  DESC");
		List<Photo> photos = jdbcTemplate.query(sql.toString(), new Object[] {profileId, accesLevel}, new PhotoRowMapper());
		return photos;
	}

	@Override
	public List<Photo> getPhotosByAlbum(Long albumId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE album_id = ? ORDER BY photos.id  DESC");
		List<Photo> photos = jdbcTemplate.query(sql.toString(), new Object[] {albumId}, new PhotoRowMapper());
		return photos;
	}

	@Override
	public List<Photo> getPhotosByRating(float rating) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE rating = ? ORDER BY photos.id  DESC");
		List<Photo> photos = (List<Photo>) jdbcTemplate.query(sql.toString(), new Object[] {rating}, new PhotoRowMapper());
		return photos;
	}

	@Override
	public void upload(Long profileId, Long albumId, String description, String link) {
		String insertQuery = "INSERT INTO photos (profile_id, album_id, description, date, link_photo) VALUES (?, ?, ?, now(), ?)";
		Object[] data = new Object[] {profileId, albumId, description, link};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Photos");
		}
	}

	@Override
	public void delete(Long id) {
		String updateQuery = "DELETE FROM photos WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Photos");
		}
	}
	
	@Override
	public int countPublicationsByUser(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM photos WHERE profile_id = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), new Object[] {profileId}, Integer.class);
	}

	@Override
	public Long getProfileIdByPhoto(Long photoId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE id = ?");
		Photo photo = jdbcTemplate.queryForObject(sql.toString(), new Object[] {photoId}, new PhotoRowMapper());
		return photo.getProfile_id();
	}

	@Override
	public Photo getPhotoByLink(String link) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE link_photo = ?");
		Photo photo = jdbcTemplate.queryForObject(sql.toString(), new Object[] {link}, new PhotoRowMapper());
		return photo;
	}

	@Override
	public int getAccesLevel(Long photoId) {
		StringBuilder sql = new StringBuilder("SELECT albums.acces_level FROM photos INNER JOIN albums ON photos.album_id=albums.id WHERE photos.id = ?");
		int accesLevel = jdbcTemplate.queryForObject(sql.toString(), new Object[] {photoId}, int.class);
		return accesLevel;
	}

	@Override
	public List<Photo> getPhotosByParametrs(String query, String date) {
		StringBuilder sql = new StringBuilder("SELECT photos.id, photos.profile_id, photos.album_id, description, date, link_photo "
				+ "FROM photos LEFT JOIN tags ON photos.id = tags.photo_id INNER JOIN profile ON photos.profile_id=profile.id "
				+ "WHERE (tags.value LIKE ? OR profile.nickname LIKE ?)");
		String sqlDate;
		query = "%" + query + "%";
		if(date != "") {
			String[] dateArr = date.split("-");
			sqlDate = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
			sql.append(" AND photos.date = ?");
			sql.append(" ORDER BY photos.id  DESC");
			return jdbcTemplate.query(sql.toString(), new Object[] {query, query, sqlDate}, new PhotoRowMapper());
		} else {
		sql.append(" ORDER BY photos.id  DESC");
			return jdbcTemplate.query(sql.toString(), new Object[] {query, query}, new PhotoRowMapper());
		}
	}

	@Override
	public List<Photo> getPhotosByTag(String tag) {
		StringBuilder sql = new StringBuilder("SELECT photos.id, photos.profile_id, photos.album_id, description, date, link_photo "
				+ "FROM photos INNER JOIN tags ON photos.id = tags.photo_id WHERE tags.value = ? ORDER BY photos.id  DESC");
		return jdbcTemplate.query(sql.toString(), new Object[] {tag}, new PhotoRowMapper());
		
	}
}
