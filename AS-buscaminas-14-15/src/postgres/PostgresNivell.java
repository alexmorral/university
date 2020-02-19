package postgres;

import java.util.ArrayList;
import org.hibernate.Session;

import model.exception.NoHiHaNivellsException;

import model.Nivell;
import model.MyHibernateUtil;

public class PostgresNivell extends PostgresGeneral<Nivell>{
	
	
	public ArrayList<Nivell> getAll() throws NoHiHaNivellsException{
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		
		ArrayList<Nivell> nivells = (ArrayList<Nivell>) session.createQuery("from " + Nivell.class.getSimpleName()).list();
		
		if(nivells == null){
			session.close();
			throw new NoHiHaNivellsException("No hi ha nivells disponibles");
		}
		session.close();
		
		return nivells;
	}
	
	public Nivell getNivell(String niv) {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		
		Nivell nivell = (Nivell) session.get(Nivell.class, niv);
		
		session.close();
		return nivell;
	}
	
}
