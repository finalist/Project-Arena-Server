package nl.kennisnet.arena.services.support;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateAwareService {
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, Serializable id) {
        final Session session = getSession();
        T t = (T) session.get(clazz, id);

        return t;
    }

    @SuppressWarnings("unchecked")
    public <T> T merge(T obj) {
        final Session session = getSession();
        T t = (T) session.merge(obj);
        return t;
    }
    
    public <T> void delete(List<T> obj) {
    	final Session session = getSession();
    	for(T t: obj){
    		session.delete(t);  
    	}    	   
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
