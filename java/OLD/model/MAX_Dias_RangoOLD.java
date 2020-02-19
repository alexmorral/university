package model;

import java.util.GregorianCalendar;
import model.Restriccion;



/**
* Representa una Restriccion tipo MAX_Turnos_Rango
* @author Axel Pelaez
*/
public class  MAX_Turnos_Rango extends Restriccion {
	
	private GregorianCalendar fechaIni = new GregorianCalendar();
	private GregorianCalendar fechaFin = new GregorianCalendar();
	private int numTurnos;

	/**
	* Crea una Restriccion del tipo MAX_Dias_Rango con entrada de las fechas en enteros
	*  @param id: identificador de la restriccion, 
	* 		  d1, m1, a1, d2, m2, a2: fecha inicial y fecha final del rango de dias
	* 		  numD: numero de dias que se quiere trabajar como maximo
	* @override Restriccion
	*/
	public MAX_Turnos_Rango (int id, int d1,int m1, int a1, int d2,int m2, int a2, int numT){
		super(id,"MAX_Dias_Rango");
		fechaIni = new GregorianCalendar(a1,m1-1,d1);
		fechaFin = new GregorianCalendar(a2,m2-1,d2);
		numTurnos = numT;
	}

	/**
	* Crea una Restriccion del tipo MAX_Dias_Rango con entrada de las fechas en GregorianCalendar
	*  @param id: identificador de la restriccion, 
	* 		  fechaI,fechaF: fecha inicial y fecha final del rango de dias
	* 		  numD: numero de dias que se quiere trabajar como maximo
	* @override Restriccion
	*/
	public MAX_Turnos_Rango (int id, GregorianCalendar fechaI, GregorianCalendar fechaF, int numT) {
		super(id,"MAX_Dias_Rango");
		fechaIni = fechaI;
		fechaFin = fechaF;
		numTurnos = numT;
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
	*Consultora de fecha inicial
	* @return La fecha inicial del rango de dias
	*/
	public GregorianCalendar getFechaIni(){
		return fechaIni;
	}
	
	/**
	*Consultora de fecha final
	* @return La fecha final del rango de dias
	*/
	public GregorianCalendar getFechaFin(){
		return fechaFin;
	}
	
	/**
	*Consultora del numero de dias que se quiere trabajar como maximo
	* @return El numero de dias que se quiere trabajar como maximo
	*/
	public int getNumDias(){
		return numTurnos;
	}
	
	
}