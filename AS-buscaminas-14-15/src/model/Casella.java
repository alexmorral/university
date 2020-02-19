/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alexmorral
 */

@Entity
@Table(name="caselles")
public class Casella implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue
	@Column private int id;
	
	//@ManyToOne
	//@JoinColumn(name="idpartida")
	//private Partida partida;
	@Column private int idpartida;
    @Column private int numFila;
    @Column private int numColumna;
    @Column private boolean estaDescoberta;
    @Column private boolean estaMarcada;
    @Column private boolean teMina;
    @Column private int numMines;
    
    public Casella(){}
    
    public Casella(int idPartida, int nF, int nC, boolean desc, boolean marc, boolean mina) {
        this.idpartida = idPartida;
    	numFila = nF;
        numColumna = nC;
        estaDescoberta = desc;
        estaMarcada = marc;
        teMina = mina;
        numMines = -1;
    }
    
    /**
     * @return the estaDescoberta
     */
    public boolean getEstaDescoberta() {
        return estaDescoberta;
    }
    
    /**
     * @return the estaMarcada
     */
    public boolean getEstaMarcada() {
        return estaMarcada;
    }
    
    /**
     * @param b the estaMarcada to set
     */
    public void setEstaMarcada(boolean b) {
        estaMarcada = b;
    }
    
    /**
     * @param b the estaDescoberta to set
     */
    public void setEstaDescoberta(boolean b) {
        estaDescoberta = b;
    }
    
    /**
     * @return the teMina
     */
    public boolean getTeMina() {
        return teMina;
    }
    
    /**
     * @param numMines the numMines
     */
    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }
    
    /**
     * @return the numMines
     */
    public int getNumMines() {
        return numMines;
    }
}
