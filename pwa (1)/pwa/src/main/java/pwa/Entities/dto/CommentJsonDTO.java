package pwa.Entities.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommentJsonDTO {

	private String authorNickname;
	private String text;
	private Date date;
	
	public String getAuthorNickname() {
		return authorNickname;
	}

	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
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

}
