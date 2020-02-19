//Autor: Axel's Copyright 
package model;
//import java.util.*;
import model.Restriccion;

public class NOT_Turno extends Restriccion {

	
	private String tipoTurno;

	
	/**
	* Crea una Restriccion del tipo NOT_Turno
	*  @param id: identificador de la restriccion, 
	* 		  tipoT: el tipo de turno que no se desea trabajar (mañana/tarde/noche)
	* @override Restriccion 
	*/
	public NOT_Turno(int id, String tipoT) {
		
		super(id,"NOT_Turno");
		tipoTurno = tipoT; 
		
	}

	/* Metodos públicos */
	
	// Consultoras
	
	/**
	*Consultora del identificador de la restriccion
	* @return La id de la restriccion
	* @override Restriccion
	*/
	public int getIdRestriccion() {
	    return idRestriccion ;
	}
	
	/**
	*Consultora del tipo de restriccion
	* @return El tipo de restriccion
	* @override Restriccion
	*/
	public String getTipo(){
		return tipo;
	}
	
	/**
	*Consultora del tipo de turno que no se desea trabajar (mañana/tarde/noche)
	* @return El tipo de turno
	*/
	public String getTipoTurno(){
		return tipoTurno;
	}

	
	
}