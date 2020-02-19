package postgres;


import org.hibernate.Session;

import model.exception.UsernameNoExisteixException;
import model.Jugador;
import model.MyHibernateUtil;
import model.UsuariRegistrat;

public class PostgresJugador extends PostgresGeneral<Jugador> {
	
	public Jugador getJugador(String user) {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		
		Jugador usuari = (Jugador) session.get(Jugador.class, user);
		
		session.close();
		return usuari;
	}
	
	public boolean exists(String username) {
		Session session = MyHibernateUtil.getSessionFactory().openSession();
		
		Jugador j = (Jugador) session.get(Jugador.class, username);
		
		session.close();
		if (j != null) return true;
		else return false;
		
	}
}