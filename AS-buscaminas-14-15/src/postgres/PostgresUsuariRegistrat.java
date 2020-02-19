package postgres;


import org.hibernate.Session;

import model.exception.UsernameNoExisteixException;
import model.UsuariRegistrat;
import model.MyHibernateUtil;

public class PostgresUsuariRegistrat extends PostgresGeneral<UsuariRegistrat> {
	
	public UsuariRegistrat getUsuari(String user) throws UsernameNoExisteixException {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		
		UsuariRegistrat usuari = (UsuariRegistrat) session.get(UsuariRegistrat.class, user);
		
		if (usuari == null) {
			session.close();
			throw new UsernameNoExisteixException("Usuari no existeix");
		}
		
		session.close();
		return usuari;
	}
	
}
