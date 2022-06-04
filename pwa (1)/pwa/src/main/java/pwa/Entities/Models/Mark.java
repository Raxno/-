package pwa.Entities.Models;

public class Mark {
	private Long id;
	private Long photo_id;
	private Long author_id;
	private int value;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(Long photo_id) {
		this.photo_id = photo_id;
	}
	public Long getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(Long author_id) {
		this.author_id = author_id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
