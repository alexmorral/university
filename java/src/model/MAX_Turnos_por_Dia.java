package model;

import model.Restriccion;

/**
* Representa una Restriccion del tipo MAX_Turnos_por_Dia
* @author Axel Pelaez
*/
public class  MAX_Turnos_por_Dia extends Restriccion{
	
	private int numTurnos;
	
	
	/* Constructora */

	/**
	* Crea un restriccion de tipo MAX_Turnos_por_Dia
	* @param id: identificador de la restriccion, 
	* 		 numT: numero maximo de turnos que se trabaja por dia
	* @override Restriccion
	*/
	public MAX_Turnos_por_Dia(int id, int numT) {
		super(id,"MAX_Turnos_por_Dia");
		numTurnos = numT;
	}


	/* Metodos públicos */
	
	//Consultoras
	
	/**
	*Consultora de identificador de la restriccion
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
	* Consultora del numero maximo de turnos que se trabaja por dia
	* @return numero maximo de turnos
	*/
	public int getNumTurnos(){
		return numTurnos;
	}
	
	
}
