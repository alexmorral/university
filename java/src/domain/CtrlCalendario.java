package domain;

import model.Calendario;
import model.Turno;
import data.CtrlDatosFichero;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controladora de la clase Calendario
 * @author Felix Fernando Ramos Velazquez
 */
public class CtrlCalendario {
	private Calendario calendar;
	private static final String[] shiftTypes = {"manana","tarde","noche"};
	private CtrlDatosFichero ctrlDatosFichero = new CtrlDatosFichero();

	/* Constructors & public methods */
	//-- Constructor --//
	/**
	 * Constructora por defecto
	 */
	public CtrlCalendario(Calendario C) {
		calendar = C;
	}
	
	//-- Modificadoras --//
	/**
	 * Modificadora del anio del calendario
	 * @param year Anio del calendario vacacional
	 */
	public void setCalendarYear(int year) {
		calendar.setCalendarYear(year);
	}

	/**
	 * Modificadora que anade un dia vacacional al calendario
	 * @param date Fecha del dia vacacional
	 * @param morningDrs Numero de Drs. del turno de manana
	 * @param eveningDrs Numero de Drs. del turno de tarde
	 * @param nightDrs Numero de Drs. del turno de noche
	 * @param especialDate Fecha especial
	 */
	public boolean addVacationDay(GregorianCalendar date, int morningDrs, int eveningDrs, int nightDrs, String especialDate) throws IOException {
		// checking input data
		if (calendar.existsVacationDay(date)) throw new IOException("El dia vacacional ya existe ");
		else if (morningDrs < 0) throw new IOException("El numero de doctores del turno de manana no es correcto ");
		else if (eveningDrs < 0) throw new IOException("El numero de doctores del turno de tarde no es correcto ");
		else if (nightDrs < 0) throw new IOException("El numero de doctores del turno de noche no es correcto ");
		// making changes
		else {
			calendar.addVacationDay(date);
			calendar.getShift(date, shiftTypes[0]).setNumberOfDoctors(morningDrs);
			calendar.getShift(date, shiftTypes[1]).setNumberOfDoctors(eveningDrs);
			calendar.getShift(date, shiftTypes[2]).setNumberOfDoctors(nightDrs);
			calendar.getShift(date, shiftTypes[0]).setSpecialDate(especialDate);
			calendar.getShift(date, shiftTypes[1]).setSpecialDate(especialDate);
			calendar.getShift(date, shiftTypes[2]).setSpecialDate(especialDate);
			return true;
		}
	}
	
	/**
	 * Modificadora de un dia vacacional del calendario
	 * @param date Fecha del dia vacacional
	 * @param morningDrs Numero de Drs. del turno de manana
	 * @param eveningDrs Numero de Drs. del turno de tarde
	 * @param nightDrs Numero de Drs. del turno de noche
	 * @param especialDate Fecha especial
	 */
	public boolean modifyVacationDay(GregorianCalendar date, int morningDrs, int eveningDrs, int nightDrs, String especialDate) throws IOException{
		// checking input data
		if (!calendar.existsVacationDay(date)) throw new IOException("La fecha no corresponde a ningun dia vacacional ");
		else if (morningDrs < 0) throw new IOException("El numero de doctores del turno de manana no es correcto ");
		else if (eveningDrs < 0) throw new IOException("El numero de doctores del turno de tarde no es correcto ");
		else if (nightDrs < 0) throw new IOException("El numero de doctores del turno de noche no es correcto ");
		// making changes
		else {
			calendar.getShift(date, shiftTypes[0]).setNumberOfDoctors(morningDrs);
			calendar.getShift(date, shiftTypes[1]).setNumberOfDoctors(eveningDrs);
			calendar.getShift(date, shiftTypes[2]).setNumberOfDoctors(nightDrs);
			calendar.getShift(date, shiftTypes[0]).setSpecialDate(especialDate);
			calendar.getShift(date, shiftTypes[1]).setSpecialDate(especialDate);
			calendar.getShift(date, shiftTypes[2]).setSpecialDate(especialDate);
		}
		return true;
	}

	/**
	 * Modificadora que elimina un dia vacacional del calendario
	 * @param date Fecha del dia vacacional
	 */
	public boolean deleteVacationDay(GregorianCalendar date) throws IOException {
		// checking input data
		if (!calendar.existsVacationDay(date))
			throw new IOException("La fecha no corresponde a ningun dia vacacional ");
		// making changes
		else {
			calendar.deleteVacationDay(date);
			return true;
		}
	}

	//-- Consultoras --//
	/**
	 * Consultora del calendario
	 * @return el calendario
	 */
	public Calendario getCalendar(){
		return calendar;
	}
	
	/**
	 * Consultora del anio del calendario
	 * @return El anio del calendario vacacional
	 */
	public int getCalendarYear() {
		return calendar.getCalendarYear();
	}

	/**
	 * Consultora de un dia vacacional
	 * @param date Fecha del dia vacacional
	 * @return ArrayList<String> con la info de un dia vacacional del calendario
	 */
	public ArrayList<String> getVacationDay(GregorianCalendar date) throws IOException {
		ArrayList<String> vacation = new ArrayList<String>();
		if (calendar.existsVacationDay(date)) {
			// getting shifts
			ArrayList<Turno> shifts = calendar.getShiftsOfADay(date);
			for (Turno t : shifts) {
				// adding number of doctors
				vacation.add(Integer.toString(t.getNumberOfDoctors()));
			}
			// adding special date
			vacation.add(shifts.get(0).getSpecialDate());
			return vacation;
		}
		else throw new IOException("La fecha no corresponde a ningun dia vacacional ");	
	}

	/**
	 * Consultora de todos los dias vacacionales
	 * @return ArrayList<ArrayList<String>> con la info de todos los dias vacacionales del calendario
	 */
	public ArrayList<ArrayList<String>> getALLVacations() throws IOException {
		ArrayList<GregorianCalendar> vacations = calendar.getALLVacations();
		ArrayList<ArrayList<String>> listVacations = new ArrayList<ArrayList<String>>();
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMM", new Locale("es","ES"));
		for (GregorianCalendar date : vacations) {
			ArrayList<String> vacationDay = new ArrayList<String>();
			// adding date
			vacationDay.add(sdf.format(date.getTime()));
			// adding number of doctors and special date
			vacationDay.addAll(getVacationDay(date));
			// adding vacation day
			listVacations.add(vacationDay);
		}
		return listVacations;
	}

	// Lectura y escritura
	/**
	 * Lee un calendario contenido en un fichero de texto
	 * @param idHospital Identificador del hospital
	 * @param path Ruta del fichero de texto
	 */
	public void readCalendar(int idHospital, String path) throws IOException, NumberFormatException, IndexOutOfBoundsException {
		// llamada a datos
		if(ctrlDatosFichero.existsCalendar(idHospital)){
			ArrayList<String> calendarData = ctrlDatosFichero.getDataCale(idHospital, path);
			int calendarYear = Integer.parseInt(calendarData.get(0));
			if (calendarYear != -1) {
				calendar.setCalendarYear(calendarYear);
				int numberOfVacations = Integer.parseInt(calendarData.get(1));
				for (int i=0,j=2; i<numberOfVacations; ++i) {
					// getting date
					String strDate = calendarData.get(j++);
					int d = Integer.parseInt(strDate.substring(0, 2));
					int M = Integer.parseInt(strDate.substring(2));
					GregorianCalendar date = new GregorianCalendar(calendarYear,M-1,d,0,0,0);
					// getting number of doctors
					int morningDrs = Integer.parseInt(calendarData.get(j++));
					int eveningDrs = Integer.parseInt(calendarData.get(j++));
					int nightDrs = Integer.parseInt(calendarData.get(j++));
					// getting special date
					String specialDate = calendarData.get(j++);
					// adding vacation day
					addVacationDay(date, morningDrs, eveningDrs, nightDrs, specialDate);
				}
			}
		}
	}

	/**
	 * Escribe el contenido del calendario actual en el fichero de texto del hospital correspondiente
	 * @param idHospital Identificador del Hospital
	 */
	public void writeCalendar(int idHospital) throws IOException {
		ArrayList<String> calendarData = new ArrayList<String>();
		calendarData.add(Integer.toString(calendar.getCalendarYear()));
		calendarData.add(Integer.toString(calendar.getNumberOfVacations()));	
		if(!calendar.isEmpty()) {
			ArrayList<GregorianCalendar> vacations = calendar.getALLVacations();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMM");
			for (GregorianCalendar date : vacations) {
				calendarData.add(sdf.format(date.getTime()));
				calendarData.addAll(getVacationDay(date));
			}
		}
		// llamada a datos
		ctrlDatosFichero.saveDataCale(calendarData, idHospital);
	}
	
	/** Importa un calendario desde un fichero de texto externo
	 * @param idHospital Identificador del hospital
	 * @param path Ruta del fichero
	 */
	public void importCalendar(int idHospital, String path) throws IOException, ParseException {
		Calendario c = new Calendario();
		CtrlCalendario cc = new CtrlCalendario(c);
		cc.readCalendar(idHospital, path);
		cc.writeCalendar(idHospital);
	}
		
		//ArrayList<String> listVacations = new ArrayList<String>();
		//listVacations = ctrlDatosFichero.getDataCale(idHospital, path);
		//ctrlDatosFichero.saveDataCale(listVacations, idHospital);
	//}
	
	//
	@Deprecated
	public void addVacationDayDeprecated(int dia, int mes, int any, int numDrsManana, int numDrsTarde, int numDrsNoche, String especialManana, String especialTarde, String especialNoche) throws IOException{
		GregorianCalendar date = new GregorianCalendar(any,mes-1,dia);
		try {
			if(date.get(GregorianCalendar.YEAR) != calendar.getCalendarYear()) throw new IOException("Anyo incorrecto ");
			else if (calendar.existsVacationDay(date)) throw new IOException("El dia vacacional ya existe");
			else if (numDrsManana < 0) throw new IOException("El numero de doctores del turno de manana no es correcto ");
			else if (numDrsTarde < 0) throw new IOException("El numero de doctores del turno de tarde no es correcto ");
			else if (numDrsNoche < 0) throw new IOException("El numero de doctores del turno de noche no es correcto ");
			// making changes
			else {
				calendar.addVacationDay(date);
				calendar.getShift(date, shiftTypes[0]).setNumberOfDoctors(numDrsManana);
				calendar.getShift(date, shiftTypes[1]).setNumberOfDoctors(numDrsTarde);
				calendar.getShift(date, shiftTypes[2]).setNumberOfDoctors(numDrsNoche);
				calendar.getShift(date, shiftTypes[0]).setSpecialDate(especialManana);
				calendar.getShift(date, shiftTypes[1]).setSpecialDate(especialTarde);
				calendar.getShift(date, shiftTypes[2]).setSpecialDate(especialNoche);
			}
		}
		catch (IOException e) {
			throw new IOException(e);
		}
	}
}
