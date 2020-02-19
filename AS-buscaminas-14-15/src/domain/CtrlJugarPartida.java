/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Arrays;

import model.strategy.EstrategiaFactory;
import model.exception.NoHiHaNivellsException;
import model.exception.PwdIncorrecteException;
import model.exception.UsernameNoExisteixException;
import model.*;
import postgres.*;
import service.*;
/**
 *
 * @author alexmorral
 */

public class CtrlJugarPartida {
    private UsuariRegistrat usuari;
    private Jugador jugador;
    private Partida p;
    private Nivell n;
    private boolean[][] mark;
    private int casellesDescobertes;
    private int casellesADescobrir;
    private boolean isJugador;
    
    public CtrlJugarPartida() {
        p = null;
        n = null;
    }
    
    /**
	 * Donat el nom del nivell, crea una partida amb el jugador, el nivell, i les caselles
	 * Asigna la estrategia, el jugador i el nivell despres de crear la partida.
	 * El getId de la partida es fa al crear la partida.
	 * 
	 * @param nivell
	 * @return Nivell n
	 * @throws Exception si falla la conexió a BD
	 */
    public Nivell createPartida(String nivell) throws Exception{
    	PostgresFactory pFact = PostgresFactory.getInstance();
    	PostgresNivell pn  = pFact.getPostgresNivell();
    	PostgresBuscaminas pb = pFact.getPostgresBuscaminas();
    	PostgresJugador pj = pFact.getPostgresJugador();
    	Buscaminas b = pb.getBuscaminas();
    	int idPartida = b.getIdPartida();
    	n = new Nivell(pn.getNivell(nivell));
        p = new Partida(idPartida, jugador);
        
        b.setIdPartida(idPartida+1);
        jugador.assignarPartida(p);
        pb.update(b);
       
        p.setNivell(n);
        
        String estrategia = EstrategiaFactory.getEstrategiaRandom().getNom();
        p.setEstrategia(estrategia);
        p.initPartida();
        pj.update(jugador);
        casellesDescobertes = 0;
        casellesADescobrir = (n.getNombreCasellesxFila()*n.getNombreCasellesxColumna())-n.getNombreMines();
        return n;
    }
    
    
    /**
	 * Funcio que carrega una partida ja existent
	 * No existeix al diagrama ja que no s'havia de fer
	 * 
	 */
    public void loadPartida() {
    	p = jugador.getPartida();
    	n = p.getNivell();
    	casellesADescobrir = (n.getNombreCasellesxFila()*n.getNombreCasellesxColumna())-n.getNombreMines();
    	casellesDescobertes = p.getCasellesDescobertes();
    }
    
    
    /**
	 * Autentica l'usuari amb Username username i Contrasenya password
	 * 
	 * A diferencia del diagrama, aquesta funcio no activa usuariNoJugador, ja que si
	 * que es pot autentificar sense jugar una partida. Hem tret la excepció i desactivat el botó
	 * "Jugar" quan l'usuari no es jugador.
	 * Tampoc retorna el jugador, ja que no el necessitem a la capa de presentacio
	 * 
	 * @param username
	 * @param pass
	 * @return Cert si s'ha autenticat al sistema, fals en cas contrari
	 * @throws UsernameNoExisteixException
	 * @throws PwdIncorrecteException
	 */
    public boolean ferAutentificacio(String username, String pass) throws UsernameNoExisteixException, PwdIncorrecteException {
    	boolean login = true;
		PostgresFactory pFactory = PostgresFactory.getInstance();
    	PostgresUsuariRegistrat pur = pFactory.getPostgresUsuariRegistrat();
		UsuariRegistrat ur = pur.getUsuari(username);
		PostgresJugador pj = pFactory.getPostgresJugador();
		isJugador = pj.exists(username);
		if(isJugador) {
			jugador = pj.getJugador(username);
			if(jugador.tePartida()) System.out.println("SI TE PARTIDA");
			else System.out.println("NO TE PARTIDA");
		}
		usuari = ur;
		if(!ur.getPassword().equals(pass)) {
			login = false;
			throw new PwdIncorrecteException("Contrasenya no valida");
		}
		return login;
	}
    
    
    /**
	 * Retorna un ArrayList<String> amb els noms dels nivells.
	 * @return el nom de tots els nivells
	 */
    
    public ArrayList<String> obtenirNivells() {
    	PostgresFactory pFactory = PostgresFactory.getInstance();
    	PostgresNivell pn = pFactory.getPostgresNivell();
    	ArrayList<String> nomNivells = new ArrayList<String>();
    	try {
    		ArrayList<Nivell> nivs = pn.getAll();
	    	for(Nivell n : nivs) {
	    		nomNivells.add(n.getNom());
	    	}
    	} catch (NoHiHaNivellsException ex) {
    		nomNivells.add(ex.toString());
    	}
        return nomNivells;
    }
    
    
    /**
	 * Marca o desmarca una casella segons el seu estat actual
	 * 
	 * A diferencia del diagrama, hem transformat les dues funcions (marcarCasella i desmarcarCasella) a
	 * una sola funcio que es basa en el seu estat.
	 * 
	 * S'han eliminat les exceptions ja que la vista s'encarrega de no permetre fer algun
	 * moviment que les activi
	 * 
	 * @param i, j
	 * @return el nou estat de la casella
	 */
    public String marcarCasella(int i, int j) {
    	String ret;
    	if(p.getMarcada(i,j)) {
    		p.desmarcarCasella(i, j);
    		ret = "D";
    	}
    	else {
    		p.marcarCasella(i, j);
    		ret = "M";
    	}
    	return ret;
    }
    
    /**
	 * Descobreix les caselles recursivament en cas que s'hagi descobert una casella
	 * sense mina, i amb numMines==0 
	 * 
	 * @param i, j
	 */
    public void descobrirCasellaRec(int i, int j) {
    	Casella c = p.getCasella(i, j);
    	mark[i][j] = true;
    	c.setEstaDescoberta(true);
		++casellesDescobertes;
    	if(!c.getTeMina() && c.getNumMines()==0) {
    		int nF = n.getNombreCasellesxFila();
    		int nC = n.getNombreCasellesxColumna();
    		//++casellesDescobertes;
    		if(i+1<nF && j+1<nC && !mark[i+1][j+1]) descobrirCasellaRec(i+1,j+1);
            if(i+1<nF && !mark[i+1][j]) descobrirCasellaRec(i+1,j);
            if(i+1<nF && j-1>-1 && !mark[i+1][j-1]) descobrirCasellaRec(i+1,j-1);
            if(j+1<nC && !mark[i][j+1]) descobrirCasellaRec(i,j+1);
            if(j-1>-1 && !mark[i][j-1]) descobrirCasellaRec(i,j-1);
            if(i-1>-1 && j+1<nC && !mark[i-1][j+1]) descobrirCasellaRec(i-1,j+1);
            if(i-1>-1 && !mark[i-1][j]) descobrirCasellaRec(i-1,j);
            if(i-1>-1 && j-1>-1 && !mark[i-1][j-1]) descobrirCasellaRec(i-1,j-1);
    	}
    }
    
    
    /**
	 * Descobreix una casella no descoberta i crida a la funcio recursiva descobrirCasellaRec
	 * en cas que tingui numMines=0 i no tingui mina.
	 * Comprova si la partida s'ha acabat i actualitza la informació a BD.
	 * Si la partida es guanya, conecta amb el servei Email i sendMail
	 * S'han eliminat les exceptions ja que la vista s'encarrega de no permetre fer algun
	 * moviment que les activi
	 * 
	 * @param i, j
	 * @throws Exception si falla la conexió a BD
	 */
    public void descobrirCasella(int i, int j) throws Exception{
    	Casella c = p.getCasella(i, j);
//    	if(c.getEstaDescoberta()) throw new CasellaJaDescoberta("Casella ja descoberta");
//    	if(c.getEstaMarcada()) throw new CasellaJaMarcada("Casella ja marcada");
    	int numMines = c.getNumMines();
    	boolean teMina = c.getTeMina();
    	boolean sumat = false;
    	if(numMines==0 && !teMina) {
    		mark = new boolean[n.getNombreCasellesxFila()][n.getNombreCasellesxColumna()];
    		for(boolean[] b : mark) Arrays.fill(b, false);
    		descobrirCasellaRec(i,j);
    		sumat = true;
    	}
    	else {
    		c.setEstaDescoberta(true);
    	}
    	if(teMina) p.setEstaAcabada(true);
    	else {
    		System.out.println("Caselles Descobertes: "+casellesDescobertes + " / Caselles a descobrir: "+casellesADescobrir);
    		if(!sumat) ++casellesDescobertes;
    		if(casellesDescobertes >= casellesADescobrir) {
    			p.setEstaAcabada(true);
    			p.setEstaGuanyada(true);
    		}
    	}
    	p.setNombreTirades(p.getNombreTirades()+1);
    	PostgresFactory pFact = PostgresFactory.getInstance();
    	PostgresPartida pp = pFact.getPostgresPartida();
    	pp.update(p);
    	if(p.isEstaAcabada()) {
    		PostgresJugador pj = pFact.getPostgresJugador();
    		jugador.assignarPartida(null);
    		pj.update(jugador);
    		if(p.isEstaGuanyada()) {
    			EmailService emailService = null;
    			emailService = (EmailService) ServiceLocator.getInstance().find("EmailService");
    			if(emailService != null) {
    				String messageBody = "Enhorabona, has guanyat la partida amb puntuacio: " + p.getPuntuacio();
    				String subject = "Informacio Partida Guanyada Buscamines";
    				emailService.sendMail(jugador.getNom(), jugador.getEmail(), subject, messageBody);
    			}
    		}
    	}
    }
    
    
    /**
	 * Retorna l'estat de la casella (i, j) a la partida
	 * 
	 * @param i, j
	 */
    public int checkCasella(int i, int j) {
    	Casella[][] cas = p.getCaselles();
    	if(cas[i][j].getEstaMarcada()) return -2;
    	else {
    		if(cas[i][j].getEstaDescoberta() && !cas[i][j].getTeMina()) return cas[i][j].getNumMines();
    		else if(cas[i][j].getEstaDescoberta() && cas[i][j].getTeMina()) return -1;
    		else return -3;
    	}
    	
    }
    
    /**
	 * @param i, j
	 * @return true si la partida esta acabada, false en cas contrari
	 */
    public boolean getIsPartidaAcabada(){
    	return p.isEstaAcabada();
    }
    
    /**
	 * @param i, j
	 * @return true si la partida esta guanyada, false en cas contrari
	 */
    public boolean getIsPartidaGuanyada() {
    	return p.isEstaGuanyada();
    }
    
    /**
	 * Mostra la informacio de la partida al Log
	 */
    public void mostrarPartida() {
        p.mostrarPartida();
    }
    
    /**
	 * @return totes les caselles de la partida
	 */
    public Casella[][] getCaselles() {
    	return p.getCaselles();
    }
    
    /**
	 * @return nombreTirades
	 */
    public int getNombreTirades() {
    	return p.getNombreTirades();
    }
    
    /**
	 * @return puntuacio
	 */
    public int getPuntuacio() {
    	return p.getPuntuacio();
    }
    
    /**
     * @param t
	 * Li asigna el temps t a la partida 
	 */
    public void setTemps(int t) {
    	p.setTemps(t);
    }
    
    /**
	 * @return true si l'usuari es jugador, false en cas contrari
	 */
    public boolean isJugador() {
    	return isJugador;
    }
    
    /**
	 * @return true si l'usuari te una partidaActual, false en cas contrari
	 */
    public boolean tePartida() {
    	return jugador.tePartida();
    }
    
    /**
	 * @return nivell n
	 */
    public Nivell getNivell() {
    	return n;
    }
    
    /**
	 * @return temps de la partida
	 */
    public int getTemps() {
    	return p.getTemps();
    }
    
    /**
	 * Actualitza la partida a BD
	 */
    public void updatePartida() throws Exception{
    	PostgresFactory pFact = PostgresFactory.getInstance();
    	PostgresPartida pp = pFact.getPostgresPartida();
    	pp.update(p);
    }
    
    /**
	 * Actualitza les caselles a BD
	 */
    public void updateCaselles() throws Exception{
    	PostgresFactory pFact = PostgresFactory.getInstance();
    	PostgresPartida pp = pFact.getPostgresPartida();
    	pp.update(p.getCaselles());
    }
}
