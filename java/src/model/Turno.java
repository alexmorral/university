package model;

import java.util.GregorianCalendar;

/**
 * Representa un turno de guardia
 * @author Felix Fernando Ramos Velázquez
 */
public class Turno {
	/* Atributos privados */
	private GregorianCalendar date;
	private String shiftType;
	private String specialDate;
	private int numberOfDoctors;

	/* Constructoras y metodos publicos */
	
	//-- Constructoras --//
	/**
	 * Constructora por defecto
	 */
	public Turno() {}	

	/**
	 * Constructora con fecha y tipo
	 * @param date Fecha del turno
	 * @param shiftType Tipo del turno
	 */
	public Turno(GregorianCalendar date, String shiftType) {
		this.date = date;
		this.shiftType = shiftType;
		specialDate = "";
		numberOfDoctors = 0;
	}

	/**
	 * Constructora completa
	 * @param date Fecha del turno
	 * @param shiftType Tipo del turno
	 * @param specialDate Fecha especial
	 * @param numberOfDoctors Numero de doctores del turno
	 */
	public Turno (GregorianCalendar date, String shiftType, String specialDate, int numberOfDoctors) {
		this.date = date;
		this.shiftType = shiftType;
		this.specialDate = specialDate;
		this.numberOfDoctors = numberOfDoctors;
	}

	/**
	 * Constructora copia
	 * @param T Turno que se copiara
	 */
	public Turno(Turno T) {
		date = T.date;
		shiftType = T.shiftType;
		specialDate = T.specialDate;
		numberOfDoctors = T.numberOfDoctors;
	}

	//-- Modificadoras --//
	/**
	 * Modifica el tipo de fecha especial del turno
	 * @param specialDate Tipo de fecha especial
	 */
	public void setSpecialDate(String specialDate) {
		this.specialDate = specialDate;
	}

	/**
	 * Modifica el numero de doctores del turno
	 * @param numberOfDoctors Numero de doctores
	 */
	public void setNumberOfDoctors(int numberOfDoctors) {
		this.numberOfDoctors = numberOfDoctors;
	}

	//-- Consultoras --//
	/**
	 * Consultora de la fecha del turno
	 * @return La fecha del turno
	 */
	public GregorianCalendar getDate() {
		return date;
	}

	/**
	 * Consultora del tipo del turno
	 * @return El tipo del turno
	 */
	public String getShiftType() {
		return shiftType;
	}

	/**
	 * Consultora del tipo de fecha especial del turno
	 * @return El tipo de fecha especial
	 */
	public String getSpecialDate() {
		return specialDate;
	}

	/**
	 * Consultora del numero de doctores del turno
	 * @return El numero de doctores
	 */
	public int getNumberOfDoctors() {
		return numberOfDoctors;
	}
}