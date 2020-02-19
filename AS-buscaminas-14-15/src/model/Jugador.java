/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author alexmorral
 */

@Entity
@Table(name="jugador")
public class Jugador extends UsuariRegistrat implements Serializable{
    private static final long serialVersionUID = 1L;
	
    @Column(nullable=false)
    private String email;
    
    @OneToOne
    private Partida partida;
    
    public Jugador() {
    	partida = null;
    }
    public Jugador(String n, String c, String u, String p) {
    	super(n,c,u,p);
    }
    
    
    @Override
    /**
     * @return true si l'usuari es jugador, false en cas contrari
     */
    public boolean isUsuariJugador() {
       return true;
    }
    
    /**
     * Asigna la partida p al jugador
     */
    public void assignarPartida(Partida p){
        partida = p;
    }
    
    /**
     * @return true si el jugador te una partida, false en cas contrari
     */
    public boolean tePartida() {
    	return (partida!=null);
    }
    
    /**
     * @return partida
     */
    public Partida getPartida() {
    	return partida;
    }
    
    /**
     * @return email
     */
    public String getEmail() {
    	return email;
    }
}
