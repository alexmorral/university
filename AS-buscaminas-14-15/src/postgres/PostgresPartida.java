package postgres;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Casella;
import model.Partida;
import model.MyHibernateUtil;

public class PostgresPartida extends PostgresGeneral<Partida> {
	
	
	public ArrayList<Partida> getAll() {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		
		ArrayList<Partida> partides = (ArrayList<Partida>) session.createQuery("from " + Partida.class.getSimpleName()).list();
		
		session.close();
		
		return partides;
	}
	
	public Partida getPartida(int idPartida) {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		
		Partida partida = (Partida) session.get(Partida.class, idPartida);
		
		session.close();
		return partida;
	}
	
	
	
	public Casella[][] getCaselles(int idPartida, int nF, int nC) {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		//select * from caselles where idpartida=18 order by numfila, numcolumna;
		String query = "from "+Casella.class.getSimpleName()+" where idpartida= "+idPartida+" order by numfila, numcolumna";
		List<Casella> casellesList = (List<Casella>) session.createQuery(query).list();
		Casella[][] caselles = new Casella[nF][nC];
		int k = 0;
		for(int i = 0; i < nF; ++i)
			for(int j = 0; j < nC; ++j) {
				caselles[i][j] = casellesList.get(k); 
				++k;
			}
		
		session.close();
		
		return caselles;
	}
	
	@Override
	public Partida store(Partida p) throws Exception {
		SessionFactory sessionFactory = MyHibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(p);
		Casella[][] caselles = p.getCaselles();
		for(Casella[] casella : caselles)
			for(Casella c : casella) session.save(c);
		session.getTransaction().commit();
		
		session.close();
		
		return p;
	}
	
	public void update(Casella[][] caselles) throws Exception{
			
			SessionFactory sessionFactory = MyHibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			
			session.beginTransaction();
			for(Casella[] casella : caselles)
				for(Casella c : casella) session.merge(c);
			session.getTransaction().commit();
			
			session.close();
			
		}
	}
	

