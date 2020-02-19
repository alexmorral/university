/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author alexmorral
 */

@Entity
@Table(name="admin")
public class Administrador extends UsuariRegistrat implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String telefon;
    
	public Administrador() {}
	
    public Administrador(String n, String c, String u, String p) {
    	super(n,c,u,p);
    }
    
    @Override
    public boolean isUsuariJugador() {
        return false;
    }
}
