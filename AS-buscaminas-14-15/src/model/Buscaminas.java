/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 *
 * @author alexmorral
 */

@Entity
@Table(name="buscaminas")
public class Buscaminas {

    /**
     * @param args the command line arguments
     */
	@Transient private static Buscaminas instance=null;
	@Id
	@Column
	private int id = 1;

	@Column
    private int idProperaPartida;

	
    private Buscaminas() {
    }
    
    public Buscaminas getInstance(){
        if(instance==null){
            instance = new Buscaminas();
        }
        return instance;
    }
    

    /**
     * @return the idPartida
     */
    public int getIdPartida() {
        return idProperaPartida;
    }

    /**
     * @param idPartida the idPartida to set
     */
    public void setIdPartida(int idPartida) {
        this.idProperaPartida = idPartida;
    }
    
}
