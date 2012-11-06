package nl.kennisnet.arena.services;

import nl.kennisnet.arena.model.Object3D;
import nl.kennisnet.arena.model.Object3D;
import nl.kennisnet.arena.repository.Object3DRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class Object3DService {

	@Autowired
	Object3DRepository repository;
	
	public Long addObject3D(byte[] content,String name,String contentType) {
	    Object3D object3d = new Object3D();
	    object3d.setContent(content);
	    object3d.setName(name);
	    object3d.setContentType(contentType);
	    Object3D storedObject3D = repository.merge(object3d);
	    return storedObject3D.getId();
	}
	
	public byte[] getObject3DContent(Long id) {
	    Object3D object3d = repository.get(id);
	    return object3d.getContent();
	}

   public String getObject3DName(Long id) {
      Object3D object3d = repository.get(id);
      return object3d.getName();
  }

   
   public String getObject3DContentType(Long id) {
      Object3D object3d = repository.get(id);
      return object3d.getContentType();
  }

}