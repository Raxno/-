package pwa.Entities.Models;

import java.io.Serializable;

public class Album implements Serializable {

	private static final long serialVersionUID = -6935037869317618871L;
	
	private Long id;
	private Long profileId;
	private String albumName;
	private AccesLevel accesLevel;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProfileId() {
		return profileId;
	}
	
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public AccesLevel getAccesLevel() {
		return accesLevel;
	}

	public void setAccesLevel(AccesLevel accesLevel) {
		this.accesLevel = accesLevel;	
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", profileId=" + profileId + ", albumName=" + albumName + ", accesLevel=" + accesLevel + "]";
	}
	

}
