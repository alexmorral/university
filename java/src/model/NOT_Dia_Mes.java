package model;
//import java.util.*;
import model.Restriccion;


/**
* Representa una Restriccion del tipo NOT_Dia_Mes
* @author Axel Pelaez
*/
public class NOT_Dia_Mes extends Restriccion{

	private int diaMes;
	

	/**
	* Crea una Restriccion del tipo NOT_Dia_Semana
	*  @param id: identificador de la restriccion, 
	* 		  diaM: dia del mes el cual no se quiere trabajar (1-31)
	* @override Restriccion
	*/
	public NOT_Dia_Mes(int id, int diaM) {
		super(id,"NOT_Dia_Mes");
		diaMes= diaM;
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
	*Consultora del dia del mes el cual no se quiere trabajar (1-31)
	* @return El dia del mes
	*/
	public int getDiaMes(){
		return diaMes;
	}

	
}