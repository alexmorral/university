//Autor: Axel's Copyright 
package model;
import java.util.GregorianCalendar;
import model.Restriccion;


/**
* Representa una restriccion de tipo XOR
* @author Axel Pelaez
*/
public class  NOT_Fecha extends Restriccion{

	
	private GregorianCalendar fecha;

	
	/**
	* Crea una Restriccion del tipo NOT_Fecha con entrada de la fecha en enteros
	*  @param id: identificador de la restriccion, 
	* 		  d,m,a: dia, mes y año de la fecha que no se desea trabajar
	* @override Restriccion
	*/
	public NOT_Fecha(int id, int d, int m, int a) {
		super(id,"NOT_Fecha");
		fecha = new GregorianCalendar(a,m-1,d);
	}
	
	/**
	* Crea una Restriccion del tipo NOT_Fecha con entrada de la fecha en GregorianCalendar
	*  @param id: identificador de la restriccion, 
	* 		  gc: fecha en forma generica de la clase GregorianCalendar que no se desea trabajar
	* @override Restriccion
	*/
	public NOT_Fecha(int id, GregorianCalendar gc) {
		super(id,"NOT_Fecha");
		fecha = gc;
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
	*Consultora de fecha
	* @return La fecha que no se desa trabajar
	*/
	public GregorianCalendar getFecha(){
		return fecha;
		
	}	
	
	
	
}
