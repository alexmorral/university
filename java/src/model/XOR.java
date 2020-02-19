package model;

import java.util.GregorianCalendar;
import java.util.ArrayList;
import model.Turno;
import model.Restriccion;


/**
* Representa una restriccion de tipo XOR
* @author Axel Pelaez
*/
public class XOR extends Restriccion {

	
	private ArrayList<Turno> listXOR = new ArrayList<Turno>();
	
	/* Constructora */
	
	/**
	* Crea una Restriccion del tipo XOR
	*  @param id: identificador de la restriccion, 
	* 		  setX: Set de turnos(puede ser vacio), que cumpliran la condicion XOR
	* @override Restriccion
	*/
	public XOR(int id, ArrayList<Turno> setX) {
		super(id,"XOR");
		 listXOR = setX;
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
	*Consultora del set de turnos
	* @return El set de turno
	* @override Restriccion
	*/
	public ArrayList<Turno> getListTurnos(){
		return listXOR; 
	}
	
	//Modificadoras
	
	/**
	*Modificadora que añade un turno a el setXOR 
	* @override Restriccion
	*/
	public void AddTurno(GregorianCalendar newFecha, String t) {
		Turno T = new Turno(newFecha,t);
		listXOR.add(T);
	}
	
	/**
	*Modificadora que añade un turno a el setXOR 
	* @override Restriccion
	*/
	public void AddTurno(int d, int m, int a, String t) {
		GregorianCalendar newFecha = new GregorianCalendar(a,m-1,d);
		Turno T = new Turno(newFecha,t);
		listXOR.add(T);
	}

}
