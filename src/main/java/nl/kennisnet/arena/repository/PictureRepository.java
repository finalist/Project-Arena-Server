package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Picture;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PictureRepository extends HibernateRepository<Picture>{

	public PictureRepository() {
		super(Picture.class);
	}

}
