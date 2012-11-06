package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Object3D;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Object3DRepository extends HibernateRepository<Object3D>{

	public Object3DRepository() {
		super(Object3D.class);
	}

}
