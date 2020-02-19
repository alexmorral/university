package postgres;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.MyHibernateUtil;

public class PostgresGeneral<T> {
	
	public T store(T object) throws Exception {
		SessionFactory sessionFactory = MyHibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(object);
		session.getTransaction().commit();
		
		session.close();
		
		return object;
	}
	
	public T update(T object) throws Exception {
		
		SessionFactory sessionFactory = MyHibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.merge(object);
		session.getTransaction().commit();
		
		session.close();
		
		return object;
	}
	
	public T delete(T object) throws Exception {
		SessionFactory sessionFactory = MyHibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.delete(object);
		session.getTransaction().commit();
		
		session.close();
		
		return object;
	}
	
}
