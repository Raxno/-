package pwa.Entities.Photos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import pwa.Repositories.AlbumStorage;
import pwa.Repositories.CommentStorage;
import pwa.Repositories.MarkStorage;
import pwa.Repositories.PhotoStorage;
import pwa.Repositories.ProfileStorage;
import pwa.Entities.dto.PhotoJsonDTO;
import pwa.Entities.Mail.MailService;
import pwa.Entities.Models.Photo;
import pwa.Entities.Tags.TagService;
import pwa.Forms.UploadForm;

@Service
public class PhotoServiceDomain implements PhotoService{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
    PhotoStorage photoStorage;
	
	@Autowired
    AlbumStorage albumStorage;
	
	@Autowired
	TagService tagService;
	
	@Autowired
    MarkStorage markStorage;
	
	@Autowired
    CommentStorage commentStorage;
	
	@Autowired
	MailService mailService;
	
	@Autowired
    ProfileStorage profileStorage;

	@Override
	public void uploadPhoto(Long profileId, Long albumId, String description, String link) {
		photoStorage.upload(profileId, albumId, description, link);		
	}

	@Override
	public void deletePhoto(Long photoId) {
		
		String emailProfile = profileStorage.findEmailById(photoStorage.getProfileIdByPhoto(photoId));
		String nicknameAuthor = profileStorage.findNicknameByEmail(emailProfile);
		String description = photoStorage.getPhotoById(photoId).getDescription();
		
		photoStorage.delete(photoId);
		commentStorage.deleteByPhoto(photoId);
		markStorage.deleteByPhoto(photoId);
		tagService.deleteTagsByPhoto(photoId);
		
		if (!StringUtils.isEmpty(emailProfile)) {
        	String message = String.format(
                    "Hello, %s! \n" +
                            "Deleted photo with description: %s",
                    nicknameAuthor,
                    description
            );       
        	mailService.send(emailProfile, "Delete photo", message);
        }
	}

	@Override
	public List<PhotoJsonDTO> photosByUserAsJson(List<Photo> photos1) {
		List<Photo> photos = photos1;
		List<PhotoJsonDTO> photosJson = null;
		
		if(photos != null && photos.size() > 0) {
			photosJson = new ArrayList<>(photos.size());
			for(Photo photo : photos) {
				PhotoJsonDTO photoDTO = new PhotoJsonDTO();
				
				photoDTO.setId(photo.getId());
				photoDTO.setAlbum_id(photo.getAlbum_id());
				photoDTO.setDescription(photo.getDescription());
				photoDTO.setDate(photo.getDate());
				photoDTO.setLink(photo.getLink_photo());
				photoDTO.setAccesLevel(photoStorage.getAccesLevel(photo.getId()));
				
				photosJson.add(photoDTO);
			}
		}
		return photosJson;
	}
	
	@Value("${project.manager.photo.dir.path}")
	private String photoDirPath;

	public FileSystemResource getImage(Long photoId) {
		String photoPath = photoDirPath + File.separator + photoStorage.getProfileIdByPhoto(photoId) + File.separator + photoStorage.getPhotoById(photoId).getLink_photo();

		File f = new File(photoPath);
		return new FileSystemResource(f);

	}
	
	public boolean savePhoto(MultipartFile multipartFile, Long profileId, UploadForm uploadForm) {
		boolean result = true;
		String randomName = UUID.randomUUID().toString();
		String filePath = photoDirPath + File.separator + profileId + File.separator;

		if (!(new File(filePath).exists())) {
			new File(filePath).mkdirs();
		}
		
		if (!multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length() - 3).equals("jpg")) {
			return false;
		}

		try {
			String orgName = randomName + multipartFile.getOriginalFilename();
			String fullFilePath = filePath + orgName;

			File dest = new File(fullFilePath);
			multipartFile.transferTo(dest);
			uploadPhoto(profileId, albumStorage.getAlbumByNameAndUser(uploadForm.getAlbumName(), profileId).getId(), uploadForm.getDescription(), orgName);
			HtmlUtils.htmlEscape(uploadForm.toString());
			tagService.addTags(photoStorage.getPhotoByLink(orgName).getId(), uploadForm.getTags());
			HtmlUtils.htmlEscape(uploadForm.toString());

		} catch (IllegalStateException e) {
			logger.severe(e.getMessage());
			result = false;
		} catch (IOException e) {
			logger.severe(e.getMessage());
			result = false;
		}

		return result;
	}
	
	private static void copyFile(String src, String dest) {
		Path result = null;
		
		try {
			result =  Files.copy(Paths.get(src), Paths.get(dest));
		} catch (IOException e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}
		
		if(result != null) {
			System.out.println("File moved successfully.");
		} else {
			System.out.println("File movement failed.");
		} 
		
	}
	
	public boolean copyPhotoIn(Long profileId, Long photoId, Long albumId) throws IOException {
		boolean result = true;
		String randomName = UUID.randomUUID().toString();
		String filePath = photoDirPath + File.separator + profileId + File.separator;
		String photoPath = photoDirPath + File.separator + photoStorage.getProfileIdByPhoto(photoId) + File.separator + photoStorage.getPhotoById(photoId).getLink_photo();
		
		if (!(new File(filePath).exists())) {
			new File(filePath).mkdirs();
		}

		try {
			
			String orgName = randomName + photoStorage.getPhotoById(photoId).getLink_photo().substring(36);
			String fullFilePath = filePath + orgName;

			copyFile(photoPath, fullFilePath);
			
			uploadPhoto(profileId, albumId, photoStorage.getPhotoById(photoId).getDescription(), orgName);

		} catch (IllegalStateException e) {
			logger.severe(e.getMessage());
			result = false;
		}

		return result;
	}

	@Override
	public List<Photo> searchByParametrs(String query, int rating, String date) {
		List<Photo> photos = photoStorage.getPhotosByParametrs(query, date);
		if(rating > 0) {
			for(int i = 0; i < photos.size(); i++) {
				if(markStorage.getRatingByPhoto(photos.get(i).getId()) < rating) {
					photos.remove(i);
					i--;
				}
			}
		}
		for(int i = 0; i < photos.size(); i++) {
			 if(photoStorage.getAccesLevel(photos.get(i).getId()) != 0) {
				 photos.remove(i);
					i--;
			 }
		}
		return photos;
	}

	@Override
	public List<Photo> searchByTag(String tag) {
		List<Photo> photos = photoStorage.getPhotosByTag(tag);
		for(int i = 0; i < photos.size(); i++) {
			 if(photoStorage.getAccesLevel(photos.get(i).getId()) != 0) {
				 photos.remove(i);
					i--;
			 }
		}
		return photos;
	}
}
