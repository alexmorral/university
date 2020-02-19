 package model;
//import java.util.*;

	/**
	* 
	* @param 
	* @return 
	* @throws 
	*/
 
 
/**
* Representa una Restriccion generica
* @author Axel Pelaez
*/
public class Restriccion {

	protected int idRestriccion;
	protected String tipo;
	
	/**
	* Crea una Restriccion generica sin atributos
	*/
	public Restriccion() {
	}
	
	/**
	* Crea una Restriccion generica con atributos
	* @param id: identificador de la restriccion,
	* 		 t: tipo de restriccion.
	*/
	public Restriccion(int id, String t) {
		idRestriccion = id;
		tipo = t; 
	}
	
	
	/* Metodos públicos */
	
	// Consultoras 
	
	/**
	*Consultora del identificador de la restriccion
	* @return La id de la restriccion
	*/
	public int getIdRestriccion() {
	    return idRestriccion;
	}
	
	/**
	*Consultora del tipo de restriccion
	* @return El tipo de restriccion
	*/
	public String getTipo() {
	    return tipo;
	}
		
}










