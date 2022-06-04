package pwa.Entities.Models;

import java.io.Serializable;
import java.sql.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = 6276218461611873512L;
	
	private Long id;
	private Long photoId;
	private Long authorId;
	private String text;
	private Date date;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	
	public Long getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", photoId=" + photoId + ", authorId=" + authorId + ", text=" + text + ", date="
				+ date + "]";
	}

	
}
