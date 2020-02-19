/*package model;
import model.Restriccion; */


/**
* Representa una Restriccion 
* @author Axel Pelaez
*/
public class  MAX_Turnos_Consecutivos extends Restriccion {

	/* Atributos */
	private int numTurnos;


	/* Constructora */
	
	/**
	* Crea una Restriccion del tipo MAX_Turnos_Consecutivos
	*  @param id: identificador de la restriccion, 
	* 		  numT: numero maximo de turnos consecutivos que se desa trabajar
	* @override Restriccion
	*/
	public MAX_Turnos_Consecutivos (int id, int numT) {
		super(id, "MAX_Turnos_Consecutivos");
		numTurnos = numT;
	}

	/* Metodos públicos */

	//Consultoras

	/**
	*Consultora del identificador de la restriccion
	* @return La id de la restriccion
	* @override Restriccion
	*/
	public int getId() {
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
	*Consultora numero maximo de turnos consecutivos que se desa trabajar
	* @return numero maximo de turnos consecutivos
	*/
	public int getDia_Num_Turnos(){
		return numTurnos;
	}
	
	
}