package pwa.Forms;

import javax.validation.constraints.NotNull;

public class UploadForm {
    @NotNull
    private String tags;

	@NotNull
    private String description;

    @NotNull
    private String albumName;

    public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
}
