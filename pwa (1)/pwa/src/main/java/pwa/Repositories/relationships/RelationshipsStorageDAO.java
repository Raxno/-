package pwa.Repositories.relationships;
import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pwa.Repositories.RelationshipsStorage;
import pwa.Entities.Models.Relationships;

@Repository
public class RelationshipsStorageDAO implements RelationshipsStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Relationships> findFollowers(Long targetId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE target_id = ? AND status = 1"); // subscriber = 1
		List<Relationships> relationships = jdbcTemplate.query(sql.toString(), new Object[] {targetId}, new RelationshipsRowMapper());
		return relationships;
	}
	
	public List<Relationships> findSubscriptions(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE profile_id = ? AND status = 1");
		List<Relationships> relationships = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new RelationshipsRowMapper());
		return relationships;
	}
	
	public List<Relationships> findFriends(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE target_id = ? AND status = 2");// friend = 2
        List<Relationships> relationships = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new RelationshipsRowMapper());
        sql = new StringBuilder("SELECT * FROM relationships WHERE profile_id = ? AND status = 2");
        List<Relationships> relationships1 = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new RelationshipsRowMapper());
        relationships.addAll(relationships1);
        return relationships;
	}
	
	public Relationships findRelationships(Long id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE id = ?");
		Relationships relationships = jdbcTemplate.queryForObject(sql.toString(), new Object[] {id}, new RelationshipsRowMapper());
		return relationships;
	}
	
	public void sendInvite(Long profileId, Long targetId) {
		String insertQuery = "INSERT INTO relationships (profile_id, target_id, status) VALUES (?, ?, 1)";
		Object[] data = new Object[] {profileId, targetId};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Relationships");
		}
	}
	
	public void acceptInvite(Long id) {
		String updateQuery = "UPDATE relationships SET status = 2 WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Relationships");
		}
	}
	
	public void deleteFriend(Long profileId, Long targetId, Long id) {
		String updateQuery = "UPDATE relationships SET profile_id = ?, target_id = ?, status = 1 WHERE id = ?";
		Object[] data = new Object[] {profileId, targetId, id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Relationships");
		}
	}

	public void unsubscribe(Long id) {
		String updateQuery = "DELETE FROM relationships WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Relationships");
		}
	}
	
	public Relationships findRelationshipsByUsers(Long profileId, Long targetId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE profile_id = ? AND target_id = ?");
		Relationships relationships = jdbcTemplate.queryForObject(sql.toString(), new Object[] {profileId, targetId}, new RelationshipsRowMapper());
		return relationships;
	}
}
