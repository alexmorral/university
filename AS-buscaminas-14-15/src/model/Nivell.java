/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alexmorral
 */

@Entity
@Table(name="nivell")

public class Nivell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id private String nom;
    @Column private int nombreCasellesxFila;
    @Column private int nombreCasellesxColumna;
    @Column private int nombreMines;
    
    
    public Nivell(){}
    
    public Nivell(Nivell niv) {
    	this.nom = niv.getNom();
    	this.nombreCasellesxColumna = niv.getNombreCasellesxColumna();
    	this.nombreCasellesxFila = niv.getNombreCasellesxFila();
    	this.nombreMines = niv.getNombreMines();
    }
    
    public Nivell(String nom, int nombreCasellesxFila, int nombreCasellesxColumna, int nombreMines) {
        this.nom = nom;
        this.nombreCasellesxFila = nombreCasellesxFila;
        this.nombreCasellesxColumna = nombreCasellesxColumna;
        this.nombreMines = nombreMines;
    }
    
    
    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Asigna nom a this.nom
     * @param nom 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the nombreCasellesxFila
     */
    public int getNombreCasellesxFila() {
        return nombreCasellesxFila;
    }

    /**
     * @param nombreCasellesxFila the nombreCasellesxFila to set
     */
    public void setNombreCasellesxFila(int nombreCasellesxFila) {
        this.nombreCasellesxFila = nombreCasellesxFila;
    }

    /**
     * @return the nombreCasellesxColumna
     */
    public int getNombreCasellesxColumna() {
        return nombreCasellesxColumna;
    }

    /**
     * @param nombreCasellesxColumna the nombreCasellesxColumna to set
     */
    public void setNombreCasellesxColumna(int nombreCasellesxColumna) {
        this.nombreCasellesxColumna = nombreCasellesxColumna;
    }

    /**
     * @return the nombreMines
     */
    public int getNombreMines() {
        return nombreMines;
    }
    
    
    /**
     * @param nombreMines the nombreMines to set
     */
    public void setNombreMines(int nombreMines) {
        this.nombreMines = nombreMines;
    }
}
