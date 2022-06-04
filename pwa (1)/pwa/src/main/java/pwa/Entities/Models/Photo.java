package pwa.Entities.Models;

import java.sql.Date;

public class Photo {
	
	private Long id;
	private Long profile_id;
	private Long album_id;
	private String description;
	private Date date;
	private String link_photo;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}
	public Long getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(Long album_id) {
		this.album_id = album_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getLink_photo() {
		return link_photo;
	}
	public void setLink_photo(String link_photo) {
		this.link_photo = link_photo;
	}
	
	@Override
	public String toString() {
		return "Profile [id=" + id + ", profile_id=" + album_id + ", description=" + description + 
				", date" + date + ", link_photo" + link_photo + "]";
	}
}