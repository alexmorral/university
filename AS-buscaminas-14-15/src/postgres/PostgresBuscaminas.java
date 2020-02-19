package postgres;


import org.hibernate.Session;

import model.Buscaminas;
import model.MyHibernateUtil;

public class PostgresBuscaminas extends PostgresGeneral<Buscaminas> {
	
	public Buscaminas getBuscaminas() {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		Buscaminas bc = (Buscaminas) session.get(Buscaminas.class, 1);
		session.close();
		return bc;
	}
	
}
