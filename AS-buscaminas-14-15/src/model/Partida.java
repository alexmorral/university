/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.strategy.*;
import postgres.PostgresFactory;
import postgres.PostgresPartida;

/**
 *
 * @author alexmorral
 */

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="partida")

public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;
	
    @Id @Column private int idPartida;
    @Column private boolean estaAcabada;
    @Column private boolean estaGuanyada;
    @Column private int nombreTirades;
    @Column private int temps = 0;
    @Column private String estrategia;
    
   @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idjugador")
    private Jugador jugador;
   
   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name="nivell") private Nivell nivell;
   @Transient Casella[][] caselles;
   
   
   	public Partida(){}
   	
   	
    public Partida(int idPartida, Jugador ur) {
        this.idPartida=idPartida;
        this.estaAcabada=false;
        this.estaGuanyada=false;
        this.nombreTirades=0;
        jugador = ur;
        
        
    }
    
    
    /**
     * @return ArrayList<String> amb posicio de les mines
     */
    private ArrayList<String> generarMinas() {
        int nombreMines = nivell.getNombreMines();
        ArrayList<Integer> f = new ArrayList<>();
        for(int i = 0; i < nivell.getNombreCasellesxFila(); ++i) f.add(i);
        ArrayList<Integer> c = new ArrayList<>();
        for(int i = 0; i < nivell.getNombreCasellesxColumna(); ++i) c.add(i);
        int count = 0;
        ArrayList<String> minas = new ArrayList<>();
        while(count < nombreMines) {
            Collections.shuffle(f);
            Collections.shuffle(c);
            if(!minas.contains(f.get(0)+" "+c.get(0))) {
                minas.add(f.get(0)+" "+c.get(0));
                ++count;
            }
        }
        return minas;
    }
    
    
    public void initPartida() {
        int nF = nivell.getNombreCasellesxFila();
        int nC = nivell.getNombreCasellesxColumna();
        this.caselles = new Casella[nF][nC];
        ArrayList<String> minas = generarMinas();
        for(int i = 0; i < nF; ++i) {
            for(int j = 0; j < nC; ++j) {
                boolean mina = false;
                for(String st : minas) {
                    String[] parts = st.split(" ");
                    if(Integer.parseInt(parts[0])==i && Integer.parseInt(parts[1])==j) mina = true;
                }
                caselles[i][j] = new Casella(idPartida, i, j, false, false, mina);
            }
        }
        
        for(int i = 0; i < nF; ++i) {
            for(int j = 0; j < nC; ++j) {
                int numMines=0;                
                if(i<nF-1 && j<nC-1 && caselles[i+1][j+1].getTeMina()) ++numMines;
                if(i<nF-1 && caselles[i+1][j].getTeMina()) ++numMines;
                if(i<nF-1 && j>0 && caselles[i+1][j-1].getTeMina()) ++numMines;
                if(j<nC-1 && caselles[i][j+1].getTeMina()) ++numMines;
                if(j>0 && caselles[i][j-1].getTeMina()) ++numMines;
                if(i>0 && j<nC-1 && caselles[i-1][j+1].getTeMina()) ++numMines;
                if(i>0 && caselles[i-1][j].getTeMina()) ++numMines;
                if(i>0 && j>0 && caselles[i-1][j-1].getTeMina()) ++numMines;
                caselles[i][j].setNumMines(numMines);
            }
        }
        PostgresFactory pFact = PostgresFactory.getInstance();
        PostgresPartida pPartida = pFact.getPostgresPartida();
        
        try {
        	pPartida.store(this);
        } catch (Exception e) {System.out.println(e);}
    }
    
    /**
     * @return the idPartida
     */
    public int getIdPartida() {
        return idPartida;
    }

    /**
     * @param idPartida the idPartida to set
     */
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    /**
     * @return the estaAcabada
     */
    public boolean isEstaAcabada() {
        return estaAcabada;
    }

    /**
     * @param estaAcabada the estaAcabada to set
     */
    public void setEstaAcabada(boolean estaAcabada) {
        this.estaAcabada = estaAcabada;
    }

    /**
     * @return the estaGuanyada
     */
    public boolean isEstaGuanyada() {
        return estaGuanyada;
    }

    /**
     * @param estaGuanyada the estaGuanyada to set
     */
    public void setEstaGuanyada(boolean estaGuanyada) {
        this.estaGuanyada = estaGuanyada;
    }

    /**
     * @return the nombreTirades
     */
    public int getNombreTirades() {
        return nombreTirades;
    }

    /**
     * @param nombreTirades the nombreTirades to set
     */
    public void setNombreTirades(int nombreTirades) {
        this.nombreTirades = nombreTirades;
    }
    
    /**
     * @param nivell the nivell to set
     */
    public void setNivell(Nivell nivell) {
        this.nivell = nivell;
    }
    
    
    public void marcarCasella(int i, int j) {
        caselles[i][j].setEstaMarcada(true);
    }
    
    public void desmarcarCasella(int i, int j) {
        caselles[i][j].setEstaMarcada(false);
    }
    
    public void descobrirCasella(int i, int j) {
        caselles[i][j].setEstaDescoberta(true);
    }
    
    public Casella getCasella(int i, int j) {
    	return caselles[i][j];
    }
    
    public Casella[][] getCaselles() {
    	return caselles;
    }
    
    public boolean getMarcada(int i, int j) {
    	return caselles[i][j].getEstaMarcada();
    }
    
    public void setEstrategia(String estr) {
    	this.estrategia = estr;
    }
    
    public void setTemps(int t) {
    	this.temps = t;
    }
    
    public int getTemps() {
    	return this.temps;
    }
    
    public Nivell getNivell() {
    	return nivell;
    }
    
    public int getCasellesDescobertes() {
    	 PostgresFactory pFact = PostgresFactory.getInstance();
         PostgresPartida pPartida = pFact.getPostgresPartida();
         caselles = pPartida.getCaselles(idPartida, nivell.getNombreCasellesxFila(), nivell.getNombreCasellesxColumna());
    	int count = 0;
    	for(Casella[] casell : caselles)
    		for(Casella c : casell) if(c.getEstaDescoberta()) ++count;
    	return count;
    }
    
    public int getPuntuacio() {
    	
    	EstrategiaFactory e = new EstrategiaFactory();
    	Estrategia estr = e.getEstrategia(estrategia);
    	int puntuacio;
		if(nivell.getNom().equals("Principiant")) puntuacio = estr.getPuntuacioPrincipiant();
		else if (nivell.getNom().equals("Intermedi")) puntuacio = estr.getPuntuacioIntermedi();
		else puntuacio = estr.getPuntuacioExpert();
    			
    	return puntuacio;
    }
    
    public void mostrarPartida() {
        for(int i = 0; i < nivell.getNombreCasellesxFila(); ++i) {
            for(int j = 0; j < nivell.getNombreCasellesxColumna(); ++j) {
                if(caselles[i][j].getTeMina()) System.out.printf("* ");
                else {
                    System.out.printf("%d", caselles[i][j].getNumMines());
                    System.out.printf(" ");
                }
            }
            System.out.println("");
        }
    }
    
}
