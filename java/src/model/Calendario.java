package model;

import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * Representa un calendario vacacional
 * @author Felix Fernando Ramos Velazquez
 */
public class Calendario {
	/* Atributos privados */
	private int calendarYear; // anio al que corresponde el calendario
	private TreeMap<Integer,Turno[]> vacationDates;
	private static final int shiftsPerDay = 3;
	private static final String[] shiftTypes = {"manana","tarde","noche"};

	/* Constructoras y metodos publicos */
	
	//-- Constructoras --//
	/**
	 * Constructora por defecto
	 */
	public Calendario() {
		calendarYear=-1;
		vacationDates = new TreeMap<Integer,Turno[]>();
	}

	/**
	 * Constructora con anio
	 * @param year Anio del calendario
	 */
	public Calendario(int year) {
		calendarYear = year;
		vacationDates = new TreeMap<Integer,Turno[]>();
	}
	
	
	/**
	 * Constructora copia 
	 * @param calendar Calendario que se copiara
	 */
	public Calendario(Calendario calendar) {
		calendarYear = calendar.getCalendarYear();		
		vacationDates = new TreeMap<Integer,Turno[]>();		
		Iterator<Integer> it = calendar.vacationDates.keySet().iterator();		
		while (it.hasNext()) {
			Integer key = it.next();
			Turno[] T = new Turno[shiftsPerDay];
			T = calendar.vacationDates.get(key);
			vacationDates.put(key,T);
		}
	}

	//-- Modificadoras --//
	/**
	 * Modificadora del anio del calendario vacacional
	 * @param year Valor que se asignara al anio del calendario
	 */
	public void setCalendarYear(int year) {
		vacationDates.clear(); //cabrones
		calendarYear = year;
	}
	
	/**
	 * Modificadora que anade un nuevo dia vacacional al calendario
	 * pre: El calendario ya tiene el anio definido
	 * @param date Fecha del dia vacacional que se va a anadir
	 */
	public void addVacationDay(GregorianCalendar date) {
		int key = date.get(GregorianCalendar.DAY_OF_YEAR) - 1;
		Turno[] t = new Turno[shiftsPerDay];		
		for (int i = 0; i < shiftsPerDay; ++i)
			t[i] = new Turno(date,shiftTypes[i]);
		vacationDates.put(key,t);
	}

	/**
	 * Modificadora que elimina un dia vacacional del calendario
	 * @param date Fecha del dia vacacional que se va a eliminar
	 */
	public void deleteVacationDay(GregorianCalendar date) {
		int key = date.get(GregorianCalendar.DAY_OF_YEAR) - 1;
		vacationDates.remove(key);
	}
	
	/**
	 * Modificadora de la fecha especial de los turnos de un dia vacacional del calendario
	 * @param date Fecha del dia vacacional
	 * @param specialDate Fecha especial
	 */
	public void setSpecialDate(GregorianCalendar date, String specialDate) {
		int key = date.get(GregorianCalendar.DAY_OF_YEAR) - 1;
		for (int i = 0; i < shiftsPerDay; ++i)
			vacationDates.get(key)[i].setSpecialDate(specialDate);
	}

	/**
	 * Modificadora del numero de doctores de un turno de un dia vacacional del calendario
	 * @param date Fecha del dia vacacional
	 * @param shiftType Tipo de turno del dia vacacional
	 * @param numberOfDoctors Numero de doctores del turno
	 */
	public void setNumberOfDoctors(GregorianCalendar date, String shiftType, int numberOfDoctors) {
		int key = date.get(GregorianCalendar.DAY_OF_YEAR) - 1;
		if (shiftType.equals(shiftTypes[0])) vacationDates.get(key)[0].setNumberOfDoctors(numberOfDoctors);
		else if (shiftType.equals(shiftTypes[1])) vacationDates.get(key)[1].setNumberOfDoctors(numberOfDoctors);
		else vacationDates.get(key)[2].setNumberOfDoctors(numberOfDoctors);
	}

	//-- Consultoras --//
	/**
	 * Consultora del anio al que corresponde el calendario
	 * @return Anio del calendario si se ha difinido, -1 en caso contrario
	 */
	public int getCalendarYear() {
		return calendarYear;
	}
	
	/**
	 * Consultora del estado actual del calendario
	 * @return True si el calendario esta vacio, False en caso contrario
	 */
	public boolean isEmpty(){
		return vacationDates.isEmpty();
}

	/**
	 * Consultora de un turno 
	 * @param date Fecha del turno
	 * @param shiftType Tipo del turno {"manana" | "tarde" | "noche"}
	 * @return Turno correspondiente a la fecha y tipo indicados
	 */
	public Turno getShift(GregorianCalendar date, String shiftType) {
		int key = date.get(GregorianCalendar.DAY_OF_YEAR) - 1;
		if (shiftType.equals(shiftTypes[0])) return vacationDates.get(key)[0];
		if (shiftType.equals(shiftTypes[1])) return vacationDates.get(key)[1];
		return vacationDates.get(key)[2];
	}

	/**
	 * Consultora de los tres turnos de un dia vacacional
	 * @param date Fecha del dia vacacional
	 * @return Lista con los tres turnos
	 */
	public ArrayList<Turno> getShiftsOfADay(GregorianCalendar date) {
		int key = date.get(GregorianCalendar.DAY_OF_YEAR) - 1;
		ArrayList<Turno> shiftsList = new ArrayList<Turno>(shiftsPerDay);
		for (int i = 0; i < shiftsPerDay; ++i)
			shiftsList.add(vacationDates.get(key)[i]);

		return shiftsList;
	}

	/**
	 * Consultora de todos los turnos del calendario vacacional
	 * @return Lista con todos los turnos
	 */
	public ArrayList<Turno> getALLShifts() {
		ArrayList<Turno> shiftsList = new ArrayList<Turno>();
		Iterator<Integer> it = vacationDates.keySet().iterator();		
		while (it.hasNext()) {
			int key = it.next();
			for (int i = 0; i < shiftsPerDay; ++i) {
				shiftsList.add(vacationDates.get(key)[i]);
			}
		}
		return shiftsList;
	}

	/**
	 * Comprueba si existe un dia vacacional en una fecha dada
	 * @param date Fecha del dia vacacional
	 * @return True si el dia vacacional existe, False en caso contrario
	 */
	public boolean existsVacationDay(GregorianCalendar date) {
		return !vacationDates.isEmpty() && vacationDates.containsKey(date.get(GregorianCalendar.DAY_OF_YEAR) - 1);
	}

	/**
	 * Consultora de todas las fechas del calendario vacacional
	 * @return Lista con todas las fechas
	 */
	public ArrayList<GregorianCalendar> getALLVacations() {
		ArrayList<GregorianCalendar> datesList = new ArrayList<GregorianCalendar>();
		Iterator<Integer> it = vacationDates.keySet().iterator();		
		while (it.hasNext()) 
			datesList.add(vacationDates.get(it.next())[0].getDate());
		return datesList;
	}

	/**
	 * Consultora del numero total de dias vacacionales
	 * @return El numero total de dias
	 */
	public int getNumberOfVacations() {
		return vacationDates.size();
	}

	/**
	 * Consultora del numero total de turnos de dias vacacionales
	 * @return El numero total de turnos
	 */
	public int getNumberOfShifts() {
		return 3*vacationDates.size();
	}
	
}