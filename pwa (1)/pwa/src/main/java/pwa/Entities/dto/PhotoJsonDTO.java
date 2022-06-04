package pwa.Entities.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhotoJsonDTO {
	
	private Long id;
	private Long album_id;
	private String description;
	private Date date;
	private String link;
	private int accesLevel;
	
	public int getAccesLevel() {
		return accesLevel;
	}

	public void setAccesLevel(int accesLevel) {
		this.accesLevel = accesLevel;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
