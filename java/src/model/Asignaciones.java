package model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import domain.nodoTurno;

/**
* Representa las asignaciones de doctores a turnos
* de una solucion posible
* 
* @author Axel Pelaez
*/


/**
 * Estructura para guardar las asignaciones de UN solo doctor
 * 
 */
class listAndSalary{
    public ArrayList<nodoTurno> listaTurnos;
    public double sueldoTotal = 0;
}



public class Asignaciones {
	
	private HashMap<Integer,listAndSalary> mapSol;
	
	/**
	 * Creadora por defecto
	* 	Crea un conjunto de asignaciones vacia
	* 
	*/
	public Asignaciones(){
		mapSol = new HashMap<Integer,listAndSalary>();
	}

	//Consultoras
	public HashMap<Integer,listAndSalary> getAsignaciones(){
		return mapSol;
	}
	
	
	/**
	*Consultora de los turnos Asignados a un doctor
	* @param: idDoctor: id del Doctor
	* @return una lista con los turnos asignados al Doctor de id idDoctor
	*/
	public ArrayList<nodoTurno> getTurnosAsigando(int idDoctor){
		listAndSalary ls = mapSol.get(idDoctor);
		return  ls.listaTurnos;
		
	}
	
	/**
	*Consultora del sueldo total de un Doctor
	* @param: idDoctor: id del Doctor
	* @return el sueldo total que cobrara el Doctor con idDoctor 
	* 		  por todos los turnos trabajados
	*/
	public double getSueldoTotal(int idDoctor){
		double s = 0.0;
		if (mapSol.containsKey(idDoctor)) {
			s = mapSol.get(idDoctor).sueldoTotal;
		}
		return  s;
	}
	
	/**
	*Consultora de las fechas que un doctor trabaja 
	* @param: idDoctor: id del Doctor
	* @return listado de fechas en las que el Doctor con idDoctor trabaja 
	*/
	public ArrayList<String> getFechasAsignaciones(int idDoctor){
		ArrayList<String> fechasAsignaciones = new ArrayList<String>();
		if (mapSol.containsKey(idDoctor)) {
			listAndSalary ls = mapSol.get(idDoctor);

			for(nodoTurno turno : ls.listaTurnos){
				//PARSEO
				GregorianCalendar c1 = turno.getFecha();
				String fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(c1.getTime());
				
				fechasAsignaciones.add(fecha);
			}
		
		}
		return  fechasAsignaciones; 
		
	}
	
	/**
	*Consultora de los tipos de turnos en que un doctor trabaja 
	* @param: idDoctor: id del Doctor
	* @return listado de tipos de turno en las que el Doctor con idDoctor trabaja 
	*/
	public ArrayList<String> getTipoTurnoAsignaciones(int idDoctor){
		ArrayList<String> tipoTurnoAsignaciones = new ArrayList<String>();
		if (mapSol.containsKey(idDoctor)) {
			listAndSalary ls = mapSol.get(idDoctor);
	
			for(nodoTurno turno : ls.listaTurnos){
				tipoTurnoAsignaciones.add(turno.getTipoTurno());
				
			}
		}
			
		return  tipoTurnoAsignaciones; 
		
	}
	
	
	
	//Setter 
	
	/**
	*Setter de sueldo 
	* Suma el sueldo de una asignacion
	* @param: idDoctor: id del Doctor
	* 		  sueldo: sueldo que cobrara el Doctor con idDoctor por trabajar un turnos
	*/
	
	public void SumaSueldo(int idDoctor, double newSueldo){
		
		if(mapSol.containsKey(idDoctor)){
			listAndSalary ls = mapSol.get(idDoctor);
			ls.sueldoTotal += newSueldo;
			mapSol.put(idDoctor,ls);
		}
		
		else{
			listAndSalary ls = new listAndSalary(); 
			mapSol.put(idDoctor, ls);
			ls.sueldoTotal += newSueldo;
			mapSol.put(idDoctor,ls);
		}
			
			
	}
	
	public void addTurnos(int idDoctor,ArrayList<nodoTurno> lt){
		listAndSalary ls = mapSol.get(idDoctor);
		ls.listaTurnos = lt;
		mapSol.put(idDoctor,ls);
	}
	
	/**
	*Setter de Asignacion 
	* Se añade las asignaciones y el sueldo total al Doctor con idDoctor;
	* @param: idDoctor: id del Doctor
	* 		  turnos: lista con los turnos asignados al Doctor con idDoctor
	* 		  sueldo: sueldo total que cobrara el Doctor con idDoctor por trabajar todos los turnos
	* 
	*/
	
	/*public void setAsignacion(int idDoctor, ArrayList<nodoTurno> turnos){
		listAndSalary ls = new listAndSalary(); 
		ls.listaTurnos = turnos;
		mapSol.put(idDoctor, ls);
	}*/

	
}