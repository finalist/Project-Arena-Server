package nl.kennisnet.arena.services;

import nl.kennisnet.arena.model.Picture;
import nl.kennisnet.arena.services.support.HibernateAwareService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PictureService extends HibernateAwareService {

	public Long addPicture(byte[] content,String name,String contentType) {
	    Picture picture = new Picture();
	    picture.setContent(content);
	    picture.setName(name);
	    picture.setContentType(contentType);
	    Picture storedPicture = merge(picture);
	    return storedPicture.getId();
	}
	
	public byte[] getPictureContent(Long id) {
	    Picture picture = get(Picture.class, id);
	    return picture.getContent();
	}

   public String getPictureName(Long id) {
      Picture picture = get(Picture.class, id);
      return picture.getName();
  }

   
   public String getPictureContentType(Long id) {
      Picture picture = get(Picture.class, id);
      return picture.getContentType();
  }

}