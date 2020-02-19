package drivers;

import model.Turno;
import model.Calendario;


import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Driver de la clase Calendario
 * @author Felix Fernando Ramos Velazquez
 */
public class DriverCalendario {

	private static int shiftsPerDay = 3;
	private static final String[] shiftTypes = {"manana","tarde","noche"};

	private static void readDate(Scanner sc, GregorianCalendar date) throws ParseException {
		System.out.println("Introducir 'fecha' { dd-mm-aaaa }:");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(false);
		String s = sc.next();
		date.setTime(sdf.parse(s));
	}

	private static void printDate(GregorianCalendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");
		System.out.print(sdf.format(date.getTime()));
	}

	/**
	 * Muestra la informacion de un turno del calendario vacacional
	 * @param turno Turno de un dia vacacional
	 */
	public static void printShift(Turno turno) {
		System.out.print("Dia vacacional:\t");
		printDate(turno.getDate());
		System.out.println();
		System.out.println("Tipo de turno:\t" + turno.getShiftType());
		System.out.println("Fecha especial:\t" + turno.getSpecialDate());
		System.out.println("Num. doctores:\t" + turno.getNumberOfDoctors() + "\n");
	}

	/**
	 * Muestra la informacion de los 3 turnos de un dia vacacional
	 * @param shiftList Lista de turnos de un dia vacacional
	 */
	private static void printShiftsOfADay(ArrayList<Turno> shiftList) {
		System.out.println("-- Dia vacacional  ");
		printDate(shiftList.get(0).getDate());
		System.out.println(" --");
		for (int i = 0; i < shiftsPerDay; ++i) {
			Turno t = shiftList.get(i);
			System.out.println("Turno de turno:\t" + t.getShiftType());
			System.out.println("Fecha especial:\t" + t.getSpecialDate());
			System.out.println("Num. de doctores: " + t.getNumberOfDoctors() + "\n");
		}
	}

	/**
	 * Muestra la informacion de todos los turnos del calendario vacacional
	 * @param shiftList Lista de los turnos del calendario vacacional
	 */
	private static void printALLShifts(ArrayList<Turno> shiftList) {
		if (shiftList.isEmpty()) System.out.println("No hay turnos vacacionales!\n");
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Iterator<Turno> it = shiftList.iterator();		
			while (it.hasNext()) {
				Turno t = it.next();
				System.out.println("Dia vacacional:\t" + sdf.format(t.getDate().getTime()));
				System.out.println("Tipo de turno:\t" + t.getShiftType());
				System.out.println("Fecha especial:\t" + t.getSpecialDate());
				System.out.println("Num. doctores:\t" + t.getNumberOfDoctors() + "\n");
			}
		}
	}

	/**
	 * Muestra una lista de las fechas correspondientes a los dias vacacionales
	 * @param vacationDaylist Lista de las fechas de dias vacacionales
	 */
	private static void printVacationDayList(ArrayList<GregorianCalendar> vacationDaylist) {
		if (vacationDaylist.isEmpty()) System.out.println("No hay dias vacacionales!");
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Iterator<GregorianCalendar> it = vacationDaylist.iterator();		
			while (it.hasNext()) {
				System.out.println(sdf.format(it.next().getTime()));
			}
		}
		System.out.println();
	}

	public static void main(String[] args) throws ParseException {

		Calendario c1 = new Calendario(1999);
		Calendario c2 = new Calendario(2000);
		Scanner sc = new Scanner(System.in);
		String answer;
		boolean exit = false;
		int op;

		while(!exit){
			System.out.println();
			System.out.println("-- Menu Principal --\n");
			System.out.println(" 1: Calendario()");
			System.out.println(" 2: Calendario(int year)");
			System.out.println(" 3: Calendario(Calendario C)");
			System.out.println(" 4: void addVacationDay(GregorianCalendar date)");
			System.out.println(" 5: void deleteVacationDay(GregorianCalendar date)");
			System.out.println(" 6: void setSpecialDate(GregorianCalendar date, String specialDate)");
			System.out.println(" 7: void setNumberOfDoctors(GregorianCalendar date, String shiftType, int numberOfDoctors)");
			System.out.println(" 8: int getCalendarYear()");
			System.out.println(" 9: Turno getShift(GregorianCalendar date, String shiftType)");
			System.out.println("10: ArrayList<Turno> getShiftsOfADay(GregorianCalendar date)");
			System.out.println("11: ArrayList<Turno> getALLShifts()");
			System.out.println("12: boolean existsVacationDay(GregorianCalendar date)");
			System.out.println("13: ArrayList<GregorianCalendar> getALLVacationDates()");		
			System.out.println("14: int getNumberOfVacationDays()");
			System.out.println("15: int getNumberOfShifts()");
			System.out.println("16: saveDataCalendar");
			System.out.println(" 0: Salir\n");

			op = sc.nextInt();

			switch(op){
			case 1:{
				System.out.println("1: Calendario()\n");
				c1 = new Calendario();
				System.out.println("Se ha creado el calendario c1!\n");
				break;
			}
			case 2:{
				System.out.println("2: Calendario(int year)\n");
				System.out.println("Introducir 'anio' del nuevo calendario:");
				int year = sc.nextInt();
				c1 = new Calendario(year);
				System.out.println("Se ha creado el calendario c1 del anio " + year + "!\n");
				break;
			}
			case 3:{
				System.out.println("3: Calendario(Calendario calendar)\n");
				c2 = new Calendario(c1);
				System.out.println("Se ha creado el calendario c2, que es copia del calendario c1!\n");
				break;
			}
			case 4:{
				System.out.println("4: void addVacationDay(GregorianCalendar date)\n");
				boolean gotoMainMenu = false;				
				while(!gotoMainMenu){
					GregorianCalendar date = new GregorianCalendar();
					readDate(sc,date);
					if (!c1.existsVacationDay(date)) {
						c1.addVacationDay(date);
						System.out.print("Se ha anadido el dia vacional ");
						printDate(date);
						System.out.println(" al calendario c1!\n");
						System.out.println("-- Dias vacacionales del calendario c1 --");
						printVacationDayList(c1.getALLVacations());
					}
					else {
						System.out.print("\nEl dia vacacional ");
						printDate(date);
						System.out.println(" ya existe!\n");
					}
					System.out.println("Anadir otro dia vacacional? {s|n}: \n");
					answer = sc.next();
					while (!(answer.equals("s") || answer.equals("n"))) {
						System.out.println("Elegir una de las siguientes opciones:");
						System.out.println("s: Anadir dia vacacional     |     n: Volver al Menu Principal");
						answer = sc.next();
					}
					if (answer.equals("n")) gotoMainMenu = true;
				}
				break;
			}
			case 5:{
				System.out.println("5: deleteVacationDay(GregorianCalendar date)\n");
				System.out.println("-- Dias vacacionales del calendario c1 --");
				printVacationDayList(c1.getALLVacations());
				boolean gotoMainMenu = false;
				while(!gotoMainMenu){
					GregorianCalendar date = new GregorianCalendar();
					readDate(sc,date);
					if (c1.existsVacationDay(date)) {
						c1.deleteVacationDay(date);
						System.out.print("\nSe ha eliminado el dia vacional ");
						printDate(date);
						System.out.println(" del calendario c1!\n");
					}
					else {
						System.out.print("\nEl dia vacacional ");
						printDate(date);
						System.out.println(" no existe!\n");
					}
					System.out.println("-- Dias vacacionales del calendario c1 --");
					printVacationDayList(c1.getALLVacations());
					System.out.println("Eliminar otro dia vacacional? {s|n}: \n");
					answer = sc.next();
					while (!(answer.equals("s") || answer.equals("n"))) {
						System.out.println("Elegir una de las siguientes opciones:");
						System.out.println("s: Eliminar dia vacacional     |     n: Volver al Menu Principal");
						answer = sc.next();
					}
					if (answer.equals("n")) gotoMainMenu = true;

				}
				break;
			}
			case 6:{
				System.out.println("6: void setSpecialDate(GregorianCalendar date, String specialDate)\n");
				GregorianCalendar date = new GregorianCalendar();
				readDate(sc,date);
				if (c1.existsVacationDay(date)) {
					System.out.println("Introducir 'fecha especial':");
					String specialDate = sc.next();
					c1.setSpecialDate(date, specialDate);
					System.out.print("\nSe ha modificado la fecha especial del dia vacional ");
					printDate(date);
					System.out.println(" del calendario c1!\n");
				}
				else {
					System.out.print("\nEl dia vacacional ");
					printDate(date);
					System.out.println(" no existe!\n");
				}
				break;
			}
			case 7:{
				System.out.println("7: void setNumberOfDoctors(GregorianCalendar date, String shiftType, int numberOfDoctors)\n");
				GregorianCalendar date = new GregorianCalendar();
				readDate(sc,date);
				if (c1.existsVacationDay(date)) {
					System.out.println("Introducir 'tipo de turno':");
					String shiftType = sc.next();
					System.out.println("Introducir 'numero de doctores':");
					int numberOfDoctors = sc.nextInt();
					if (numberOfDoctors < 0) System.out.print("\nEl numero de doctores introducido no es correcto");
					else {
						c1.setNumberOfDoctors(date, shiftType, numberOfDoctors);
						System.out.print("\nSe ha modificado el numero de doctores del turno de " + shiftType + " del dia vacional ");
						printDate(date);
						System.out.println(" del calendario c1!\n");
					}
				}
				else {
					System.out.print("\nEl dia vacacional ");
					printDate(date);
					System.out.println(" no existe!\n");
				}
				break;
			}
			case 8:{
				System.out.println("8: int getCalendarYear()\n");
				int year = c1.getCalendarYear();
				if (year != -1) System.out.println("Anio del calendario c1: " + year + "\n");
				else System.out.println("No se ha definido el anio para el calendario c1!");

				break;
			}			
			case 9:{
				System.out.println("9: Turno getShift(GregorianCalendar date, String shiftType)\n");
				GregorianCalendar date = new GregorianCalendar();
				readDate(sc,date);
				if (!c1.existsVacationDay(date)) {
					System.out.println("\nEl turno no existe!\n");
				}
				else {
					System.out.println("Introducir 'tipo' del turno: {manana | tarde | noche}");
					String shiftType = sc.next();
					if (!shiftType.equals(shiftTypes[0]) && !shiftType.equals(shiftTypes[1]) && !shiftType.equals(shiftTypes[2])) {
						System.out.println("\nEl tipo de turno introducido no es correcto!\n");
					}
					else {
						Turno T = c1.getShift(date, shiftType);
						printShift(T);
					}
				}
				break;
			}
			case 10:{
				System.out.println("10: ArrayList<Turno> getShiftsOfADay(GregorianCalendar date)\n");
				GregorianCalendar date = new GregorianCalendar();
				readDate(sc,date);
				if (!c1.existsVacationDay(date)) {
					System.out.println("\nEl dia vacacional no existe!\n");
				}
				else {
					printShiftsOfADay(c1.getShiftsOfADay(date));
				}
				break;
			}
			case 11:{
				System.out.println("11: ArrayList<Turno> getALLShifts()\n");
				System.out.println("-- Turnos del calendario c1 --");
				printALLShifts(c1.getALLShifts());
				System.out.println("-- Turnos del calendario c2 --");
				printALLShifts(c2.getALLShifts());
				break;
			}
			case 12:{
				System.out.println("12: existsVacationDay(GregorianCalendar date)\n");
				GregorianCalendar date = new GregorianCalendar();
				readDate(sc,date);
				System.out.print("\nEl dia vacional ");
				printDate(date);
				if (c1.existsVacationDay(date)) System.out.println(" existe!\n");
				else System.out.println(" no existe!\n");
				break;
			}
			case 13:{
				System.out.println("13: ArrayList<GregorianCalendar> getALLVacationDates()\n");
				System.out.println("-- Dias vacacionales del calendario c1 --");
				printVacationDayList(c1.getALLVacations());
				System.out.println("-- Dias vacacionales del calendario c2 --");
				printVacationDayList(c2.getALLVacations());
				break;
			}
			case 14:{
				System.out.println("14: getNumberOfVacationDays()\n");
				System.out.println("Numero de dias vacacionales del calendario c1: " + c1.getNumberOfVacations());
				System.out.println("Numero de dias vacacionales del calendario c2: " + c2.getNumberOfVacations() + "\n");
				break;
			}
			case 15:{
				System.out.println("15: int getNumberOfShifts()\n");
				System.out.println("Numero de turnos del calendario c1: " + c1.getNumberOfShifts());
				System.out.println("Numero de turnos del calendario c2: " + c2.getNumberOfShifts() + "\n");
				break;
			}
				
			case 0:{
				System.out.println("Salir? {s|n}: \n");
				answer = sc.next();
				while (!(answer.equals("s") || answer.equals("n"))) {
					System.out.println("Elegir una de las siguientes opciones:");
					System.out.println("s: Salir     |     n: Volver al Menu Principal");
					answer = sc.next();
				}
				if (answer.equals("s")) exit = true;
				break;
			}
			default: {
				System.out.println("Opcion incorrecta o no disponible!\n");
				break;
			}
			}

			if (!exit) {
				System.out.println("Teclear c para continuar\n");
				answer = sc.next();
				while (!answer.equals("c")) {
					System.out.println("Teclear c para continuar\n");
					answer = sc.next();
				}
			}
		}
		sc.close();
		System.out.println("-- fin del programa --");
	}
}
