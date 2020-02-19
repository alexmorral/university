package views;

import java.io.IOException;

import domain.*;
import model.exception.PwdIncorrecteException;
import model.exception.UsernameNoExisteixException;

import java.util.ArrayList;

import model.Nivell;

/**
*
* @author alexmorral
*/

public class CtrlJugarPartidaPresentacio {
	
	private JugarPartidaView partidaView;
	private CtrlJugarPartida ctrlJugarPartida;
	
	/**
	 * Constructora per defecte
	 */
	public CtrlJugarPartidaPresentacio () {
		ctrlJugarPartida = new CtrlJugarPartida();
		partidaView = new JugarPartidaView(this);		
	}
	
	/**
	 * Arranca la interficie grafica 
	 */
	public void initialize() {
		partidaView.setVisible(true);	
	}
				
	/**
	 * Autentica l'usuari amb Username username i Contrasenya password
	 * 
	 * Mostra els avisos en cas que rebi les Exceptions
	 * 
	 * @param username
	 * @param pass
	 * @return Cert si s'ha autenticat al sistema, fals en cas contrari
	 */
	public boolean prLogin(String username,String pass){
		boolean logged = false;
		try {
			logged = ctrlJugarPartida.ferAutentificacio(username, pass);
		} catch (UsernameNoExisteixException e) {
			partidaView.mostrarAvis("Username no existeix", 0);
		} catch (PwdIncorrecteException e) {
			partidaView.mostrarAvis("Password incorrecte", 0);
		} 
		
		ArrayList<String> nivells = ctrlJugarPartida.obtenirNivells();
		partidaView.mostraNivells(nivells);
		if (nivells.isEmpty()) {
			partidaView.mostrarAvis("No hi ha Nivells", 1);
		}
		if(!ctrlJugarPartida.isJugador()) partidaView.mostrarAvis("Usuari no jugador", 1);
		else partidaView.nivellPanelMessages.setText("");
		return logged;
	}
	
	
	/**
	 * Crea la partida amb el Nivell amb nom nivell
	 * 
	 * A diferencia del diagrama, passem per paràmetre només el nivell seleccionat, 
	 * sense el jugador ja que no el necessitem.
	 * A més, no es pot crear una partida si no s'ha seleccionat un nivell
	 * 
	 * @param nivell
	 */
	public void prCrearPartida(String nivell) throws Exception{
	
		Nivell n = ctrlJugarPartida.createPartida(nivell);
		partidaView.buildBoard(ctrlJugarPartida.getCaselles(), n);
		
		System.out.println("Partida creada!");
		
		ctrlJugarPartida.mostrarPartida();
		
	}
	
	/**
	 * Carrega una partida ja existent.
	 * No està al diagrama.
	 * 
	 */
	public void PrLoadPartida() throws Exception{
		ctrlJugarPartida.loadPartida();
		partidaView.buildBoard(ctrlJugarPartida.getCaselles(), ctrlJugarPartida.getNivell());
		partidaView.updateBoard(ctrlJugarPartida.getCaselles());
		partidaView.temps.setText(String.valueOf(ctrlJugarPartida.getTemps()));
		ctrlJugarPartida.mostrarPartida();
	}
	
	public boolean jugadorTePartida() {
		return ctrlJugarPartida.isJugador() && ctrlJugarPartida.tePartida();
	}
	
	/**
	 * Atura la partida i surt. Esta feta per a que quan es surti, es pugui
	 * guardar la partida actual a BD. En cas que es vulgui tancar l'aplicació,
	 * veure prSortir
	 * 
	 */
	public void prTancar() throws Exception{
		ctrlJugarPartida.updatePartida();
		System.out.println("Partida Aturada");
	}
	
	/**
	 * Descobreix, marca o desmarca la casella (i,j)
	 * 
	 * Hem passat les funcions prDescobrirCasella, prMarcarCasella i prDesmarcarCasella
	 * en una, amb un parámetre opt, que determina quina opcio es vol.
	 * 
	 * @param i, j, opt
	 */
	public void PrCheckCasella(int i, int j, int opt) throws IOException, Exception {
		
		if(opt == 1) ctrlJugarPartida.descobrirCasella(i, j);
		else  ctrlJugarPartida.marcarCasella(i, j);
		if(ctrlJugarPartida.getIsPartidaAcabada()){
			int punt = ctrlJugarPartida.getPuntuacio();
			partidaView.finalitzaPartida(ctrlJugarPartida.getIsPartidaGuanyada(), punt);
			
		} 
		
		partidaView.updateBoard(ctrlJugarPartida.getCaselles());
		ctrlJugarPartida.updateCaselles();
		partidaView.actualitzaTirades(ctrlJugarPartida.getNombreTirades());
		
		
	}
	
	/**
	 * @param i, j
	 * @return el l'estat de la casella (i,j)
	 */
	public int checkCasella(int i, int j) {
		return ctrlJugarPartida.checkCasella(i,j);
	}
    
	/**
	 * Actualitza el temps de la partida a t
	 * @param t
	 */
	public void setTemps(int t) {
		ctrlJugarPartida.setTemps(t);
	}
	
	/**
	 * Tanca l'aplicació
	 * 
	 */
	public void prSortir(){
		System.exit(-1);
	}

}
