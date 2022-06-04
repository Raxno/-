package pwa.Repositories.profile;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pwa.Repositories.ProfileStorage;
import pwa.Entities.Models.Profile;

@Repository
public class ProfileStorageDAO implements ProfileStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int countByEmail(String email) {
		
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM profile WHERE email = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), new Object[] {email}, Integer.class);
	}

	@Override
	public void save(Profile profile) {
		if (profile.getId() == null || profile.getId() == 0) {
			this.insert(profile);
		} else {
			this.update(profile);
		}
		
	}

	@Override
	public Profile findByEmailAndEnabledTrue(String email) {

		StringBuilder sql = new StringBuilder("SELECT * FROM profile WHERE email = ? and enabled = true");
		
		Profile profile = jdbcTemplate.queryForObject(sql.toString(), new Object[] {email}, new ProfileRowMapper());

		return profile;
	}
	
	private void update(Profile profile) {
		String updateQuery = "UPDATE profile SET email = ?, firstName = ?, lastName = ?, nickname = ?, password = ?, enabled = ?, token = ?, roles = ?, activation_code = ? WHERE id = ?";
		Object[] data = new Object[] {
			profile.getEmail(), profile.getFirstName(), profile.getLastName(), profile.getNickname(), profile.getPassword(), 
			profile.isEnabled(), profile.getToken(), String.join(",", profile.getRolesList()), profile.getActivationCode(), profile.getId()
		};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Profile");
		}
	}

	private void insert(Profile profile) {
		String insertQuery = "INSERT INTO profile (email, firstName, lastName, nickname, password, enabled, token, roles, activation_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] data = new Object[] {
			profile.getEmail(), profile.getFirstName(), profile.getLastName(), profile.getNickname(), profile.getPassword(), 
			profile.isEnabled(), profile.getToken(), String.join(",", profile.getRolesList()), profile.getActivationCode()
		};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Profile");
		}
	}
	
	@Override
	public Long getIdByNickname(String nickname) {
		StringBuilder sql = new StringBuilder("SELECT id FROM profile WHERE nickname = ?");
		try {
			Long id = jdbcTemplate.queryForObject(sql.toString(), new Object[] {nickname}, Long.class);
			return id;
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Profile findByActivationCode(String code) {
		
		StringBuilder sql = new StringBuilder("SELECT * FROM profile WHERE activation_code = ?");
		
		Profile profile = jdbcTemplate.queryForObject(sql.toString(), new Object[] {code}, new ProfileRowMapper());
		
		return profile;
	}
		
	public List<Profile> findFriends(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM profile INNER JOIN relationships ON profile.id = relationships.target_id WHERE profile_id = ? AND relationships.status = 2");
		List<Profile> profiles = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new ProfileRowMapper());
		sql = new StringBuilder("SELECT * FROM profile INNER JOIN relationships ON profile.id = relationships.profile_id WHERE target_id = ? AND relationships.status = 2");
		profiles.addAll(jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new ProfileRowMapper()));
		
		return profiles;
	}

	@Override
	public List<Profile> findFollowers(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM profile INNER JOIN relationships ON profile.id = relationships.profile_id WHERE target_id = ? AND relationships.status = 1");
		List<Profile> profiles = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new ProfileRowMapper());
		return profiles;
	}

	@Override
	public List<Profile> findSubscriptions(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM profile INNER JOIN relationships ON profile.id = relationships.target_id WHERE profile_id = ? AND relationships.status = 1");
		List<Profile> profiles = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new ProfileRowMapper());
		return profiles;
	}

	@Override
	public String getNicknameById(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT nickname FROM profile WHERE id = ?");
		String nickname = jdbcTemplate.queryForObject(sql.toString(), new Object[] {profileId}, String.class);
		return nickname;
	}	
	
	@Override
	public Profile loadById(Long profileId) {
		StringBuilder sqlQuery = new StringBuilder("SELECT * FROM profile WHERE id = ?");

		return jdbcTemplate.queryForObject(sqlQuery.toString(), new Object[] {profileId}, new ProfileRowMapper());
	}
	
	@Override
	public Profile findByEmail(String email) {

		StringBuilder sql = new StringBuilder("SELECT * FROM profile WHERE email = ?");
		
		Profile profile = jdbcTemplate.queryForObject(sql.toString(), new Object[] {email}, new ProfileRowMapper());

		return profile;
	}

	@Override
	public String findEmailById(Long id) {
		
		StringBuilder sql = new StringBuilder("SELECT email FROM profile where id = ?");
		
		String email = jdbcTemplate.queryForObject(sql.toString(), new Object[] {id}, String.class);
		
		return email;
	}

	@Override
	public String findNicknameByEmail(String email) {
		
		StringBuilder sql = new StringBuilder("SELECT nickname FROM profile WHERE email = ?");
		
		String nickname = jdbcTemplate.queryForObject(sql.toString(), new Object[] {email}, String.class);

		return nickname;
	}

	@Override
	public String getRole(Long id) {
		
		StringBuilder sql = new StringBuilder("SELECT roles FROM profile where id = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), new Object[] {id}, String.class);
	}

	@Override
	public void ban(Long id) {
		String updateQuery = "UPDATE profile SET enabled = ? WHERE id = ?";
		Object[] data = new Object[] {0, id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Profile");
		}
	}
}
