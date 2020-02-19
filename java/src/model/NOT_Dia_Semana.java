package model;
//import java.util.*;
import model.Restriccion;

/**
* Representa una Restriccion 
* @author Axel Pelaez
*/
public class NOT_Dia_Semana extends Restriccion{
	
	private String diaSemana;


	
	/**
	* Crea una Restriccion del tipo NOT_Dia_Semana
	*  @param id: identificador de la restriccion, 
	* 		  diaSe: dia de la semana el cual no se quiere trabajar (Lunes-Domingo)
	* @override Restriccion
	*/
	public NOT_Dia_Semana(int id, String diaSe) {
		super(id,"NOT_Dia_Semana");
		diaSemana = diaSe;
	}


	/* Metodos públicos */
	
	//Consultoras
	
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
	*Consultora del dia de la semana el cual no se quiere trabajar (Lunes-Domingo)
	* @return El dia de la semana
	*/
	public String getDiaSemana(){
		return diaSemana;
	}
	
}