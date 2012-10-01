package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Image;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageRepository extends HibernateRepository<Image>{

	public ImageRepository() {
		super(Image.class);
	}

}
