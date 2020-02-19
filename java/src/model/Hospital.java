package model;
import java.io.IOException;
import java.util.*;

import model.Calendario;
import model.Doctor;

/**
* Representa un Hospital
* @author oscar.urgelles
*/
public class Hospital {
	
	private int idHospital;
	private String name;
	private double factorM;
	private double factorT;
	private double factorN;
	private ArrayList<Doctor> doctors;
	private Calendario calendari;
	
	
	/**
	 * Creadora Por defecto
	 */
	public Hospital() {
		idHospital=0;
		name=null;
		factorM=0.0;
		factorT=0.0;
		factorN=0.0;
		doctors = new ArrayList<Doctor>();
		calendari = new Calendario();
	}
	
	/**
	 *Creadora de Hospital con algunos de los parametros de la clase
	 * @param id 	Un identificador unico de hospital
	 * @param nom	Nombre del Hospital
	 * @param fm 	FactorManana Se multiplicara al sueldoxTurno del doctor Para calcular el coste
	 * @param ft	FactorTarde Se multiplicara al sueldoxTurno del doctor Para calcular el coste
	 * @param fn	FactorNoche Se multiplicara al sueldoxTurno del doctor Para calcular el coste
	 
	 */
	public Hospital (int id, String nom, double fm, double ft, double fn){
		idHospital=id;
		name=nom;
		factorM=fm;
		factorT=ft;
		factorN=fn;
		doctors = new ArrayList<Doctor>();
		calendari = new Calendario();
	}
	
	/**
	 *  Creadora de Hospital con todos los parametros de la clase
	 * @param id 	Un identificador unico de hospital
	 * @param nom	Nombre del Hospital
	 * @param fm 	FactorMorning Se multiplicara al sueldoxTurno del doctor Para calcular el coste
	 * @param ft	FactorTarde Se multiplicara al sueldoxTurno del doctor Para calcular el coste
	 * @param fn	Factorevening Se multiplicara al sueldoxTurno del doctor Para calcular el coste
	 * @param aldoc	ArrayList de Doctores con los que se inicializara el Hospital
	 * @param cal	Representa el Calendario de turnos de guardia
	 */
	public Hospital (int id, String nom, double fm, double ft, double fn,
		ArrayList<Doctor> aldoc, Calendario cal){
		idHospital=id;
		name=nom;
		factorM=fm;
		factorT=ft;
		factorN=fn;
		doctors = new ArrayList<Doctor>();
		for(int i = 0; i < aldoc.size(); ++i){
			if(doctors.contains(aldoc.get(i))==false){
				doctors.add(aldoc.get(i));
			}
		}
		calendari = new Calendario(cal);
	}
			
	/**
	 *  Consultora de la id del Hospital
	 *  @return la id del Hospital
	 */
	public int getId(){
		return idHospital;
	}
	
	/** 
	 * Consultora del Nombre del Hospital
	 * @return el Nombre del Hospital
	 */
	public String getNombre(){
		return name;
	}
	
	/** 
	 * Consultora del factor manana
	 * @return el factor manana
	 */
	public double getFactorM(){
		return factorM;
	}
	
	/** 
	 * Consultora del factor tarde
	 * @return el factor tarde
	 */
	public double getFactorT(){
		return factorT;
	}
	
	/** 
	 * Consultora del factor noche
	 * @return el factor noche
	 */
	public double getFactorN(){
		return factorN;
	}
	
	/** 
	 * Consultora de un Doctor del Hospital con su Id.
	 * pre: el Hospital contiene ese doctor.
	 * @param id 	es la id del doctor
	 * @return el doctor con identificador = id.
	 */
	
	public Doctor getDoctor(int id){
		
		Iterator<Doctor> itr = doctors.iterator();
		int n=0;
		boolean trobat=false;
		while(!trobat){
			Doctor d1=itr.next();
			if(d1.getId()==id){
				trobat=true;
			}
			else ++n;
		}
		return doctors.get(n);
	}
	
	/** 
	 * Consultora de todos los doctores del Hospital
	 * @return el Arraylist<Doctor> con todos los doctores del Hospital
	 */
	public ArrayList<Doctor> getDoctors(){
	
		return doctors;
	}
	
	/**
	 * Devuelve el calendario
	 */
	
	public Calendario getCalendario(){
		
		return calendari;
	}
	
	/** 
	 * Indica si hay algun Doctor en el Hospital
	 * @return un boolean que indica si esta vacio.
	 */
	public boolean isDocEmpty(){
		return doctors.isEmpty();
	}
	
	/** 
	 * Consultora del numero de Doctores del Hospital
	 * @return el numero de Doctores del Hospital
	 */
	public int docSize(){
		return doctors.size();
	}
	
	/** 
	 * Consultora de la existencia de un doctor con identificador = id
	 * @param id	es el identificador del doctor.
	 * @return un boolean que indica si existe ese doctor
	 */
	public boolean existsDoctor(int id){
		for(int i=0;i<doctors.size();++i){
			if(doctors.get(i).getId()==id)return true;
		}
		return false;
	}
	
	/** 
	 * Modificadora del Nombre del Hospital
	 * @param nom	es el nuevo Nombre del Hospital
	 */
	public void setNombre(String nom){
		this.name=nom;
	}
	
		
	/** 
	 * Modificadora del factor Manana del Hospital
	 * @param factor	es el nuevo factorManana del Hospital
	 */
	public void setFactorM(double factor){
		this.factorM=factor;
	}
	
	/** 
	 * Modificadora del factor Tarde del Hospital
	 * @param factor	es el nuevo factorTarde del Hospital
	 */
	public void setFactorT(double factor){
		this.factorT=factor;
	}
	
	/** 
	 * Modificadora del factor Noche del Hospital
	 * @param factor	es el nuevo factorNoche del Hospital
	 */
	public void setFactorN(double factor){
		this.factorN=factor;
	}

	/** 
	 * Modificadora de un Doctor del Hospital
	 * 	pre - el id del Doctor d existe en el Hospital
	 *  post - se ha sobreescrito el Doctor d
	 * @param d		es el doctor ya modificado
	 */
	public void setDoctor(Doctor d){
		Iterator<Doctor> itr = doctors.iterator();
		int n=0;
		boolean trobat=false;
		while(!trobat){
			Doctor d1=itr.next();
			if(d1.getId()==d.getId()){
				itr.remove();
				doctors.add(n,d);
				trobat=true;
			}
			++n;
		}
		
	}
	
	/** Modifica el anyo del calendario
	 * 
	 * @param year anyo del calendario
	 */
	public void setCalendarYear(int year){
		calendari.setCalendarYear(year);
	}
	
	/** 
	 * Metodo que inserta un doctor nuevo en el Hospital
	 * 	pre - El Hospital no contiene el Doctor d. 
	 * 	post- Se ha anadido el Doctor d al hospital
	 * @param d		es el nuevo doctor del Hospital
	 */
	public void addDoctor(Doctor d){
			doctors.add(d);		
	}
		
	/** 
	 * Metodo que inserta un ArrayList de doctores en el Hospital
	 * 	post- se ha insertado el ArayList de doctores en el Hospital
	 * 		  Si algun doctor ia existia se ha remplazado por el nuevo.
	 * @param aldoc		es un ArrayList de Doctores
	 */
	public void addDoctors(ArrayList<Doctor> aldoc){

		for(int i=0; i < aldoc.size();++i){
			if(doctors.contains(aldoc.get(i))==false){
				doctors.add(aldoc.get(i));
			}
			else {
				doctors.remove(aldoc.get(i));
				doctors.add(aldoc.get(i));
			}
		}
	}
	
	/** 
	 * Metodo que elimina un doctor del Hospital
	 * pre - El id del doctor existe en el hospital
	 * @param id 	es el identificador del doctor a borrar
	 */
	public void deleteDoctor(int id){
		for(int i=0;i<doctors.size();++i){
			if(doctors.get(i).getId()==id) {
				doctors.remove(i);
				break;
			}
		}
	}
	public boolean existsCalendar(){
		return calendari.getCalendarYear() != -1;
	}
	
	public void deleteCalendar(){
		calendari.setCalendarYear(-1);
	}
	
	/** 
	 * Metodo que elimina todos los doctores del Hospital
	 */
	public void cleardoctors(){
		doctors.clear();
	}
	
	/** 
	 * Metodo que reseta todos los datos del Hospital
	 */
	public void resetHosp(){
		idHospital=0;
		name=null;
		factorM=0.0;
		factorT=0.0;
		factorN=0.0;
		doctors = new ArrayList<Doctor>();
		calendari = new Calendario(0);
	}
	
	public ArrayList<ArrayList<String>> verDoctores() throws IOException {
		ArrayList<ArrayList<String>> docInfo = new ArrayList<ArrayList<String>>();
			if(!doctors.isEmpty()){
				
				
				ArrayList<String> Doc = new ArrayList<String>();

				for(Doctor doc: doctors){
					
					Doc.add(Integer.toString(doc.getId()));
					 
					String name = doc.getName().replace("%", " ");
					Doc.add(name);
					
					Doc.add(Double.toString(doc.getSalaryTurn()));
					Doc.add(Integer.toString(doc.getNumMaxTurn()));
					
					Doc.add(Integer.toString(doc.Rsize()));
					
					docInfo.add(Doc);
					
					Doc = new ArrayList<String>();
					
				}
				
				
			}
			
		return docInfo;
	}
	
}