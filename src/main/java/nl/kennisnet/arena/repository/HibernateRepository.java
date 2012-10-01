package nl.kennisnet.arena.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

abstract class HibernateRepository<T> {

	@Autowired
    private SessionFactory sessionFactory;
	private final Class<T> clazz;
	
    protected HibernateRepository(Class<T> clazz){
    	this.clazz=clazz;
    }
    
    @SuppressWarnings("unchecked")
    public T get(Serializable id) {
        final Session session = getSession();
        T t = (T) session.get(clazz, id);

        return t;
    }

    @SuppressWarnings("unchecked")
    public T merge(T obj) {
        final Session session = getSession();
        T t = (T) session.merge(obj);
        return t;
    }
    
    @SuppressWarnings("unchecked")
    public List<T> getAll(){
    	return getSession().createCriteria(clazz).list();
    }
    public void delete(List<T> obj) {
    	final Session session = getSession();
    	for(T t: obj){
    		session.delete(t);  
    	}    	   
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
