package drivers;

import java.util.GregorianCalendar;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import model.Turno;

/**
 * Driver de la clase
 * @author Felix Fernando Ramos Velazquez
 */
public class DriverTurno {

	private static void readDate(Scanner sc, GregorianCalendar date) throws ParseException {
		System.out.println("Introducir 'fecha' { dd-mm-aaaa }:");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(false);
		String s = sc.next();
		date.setTime(sdf.parse(s));
	}

	private static void printDate(GregorianCalendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
		System.out.print(sdf.format(date.getTime()));
	}

	public static void printShift(Turno turno) {
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");
		System.out.println("Dia vacacional:\t" + sdf.format(turno.getDate().getTime()));
		System.out.println("Tipo de turno:\t" + turno.getShiftType());
		System.out.println("Fecha especial:\t" + turno.getSpecialDate());
		System.out.println("Num. doctores:\t" + turno.getNumberOfDoctors() + "\n");
	}

	public static void main(String[] args) throws ParseException {

		Turno t1 = new Turno();
		Turno t2 = new Turno();
		Scanner sc = new Scanner(System.in);
		String answer;
		boolean exit = false;
		int op;

		while(!exit){

			System.out.println("-- Menu Principal --\n");
			System.out.println(" 1: Turno()");
			System.out.println(" 2: Turno (GregorianCalendar date, String shiftType)");
			System.out.println(" 3: Turno (GregorianCalendar date, String shiftType, String specialDate, int numberOfDoctors)");
			System.out.println(" 4: Turno(Turno T)");
			System.out.println(" 5: void setSpecialDate(String specialDate)");
			System.out.println(" 6: void setNumberOfDoctors(int numberOfDoctors)");
			System.out.println(" 7: GregorianCalendar getDate()");
			System.out.println(" 8: String getShiftType()");
			System.out.println(" 9: String getSpecialDate()");
			System.out.println("10: int getNumberOfDoctors()");
			System.out.println(" 0: Salir");

			op = sc.nextInt();

			switch(op){
			case 1:{
				System.out.println("1: Turno()\n");
				t1 = new Turno();
				System.out.println("Se ha creado el turno vacio t1!\n");
				break;
			}
			case 2:{
				System.out.println("2: Turno (GregorianCalendar date, String shiftType)\n");
				GregorianCalendar date = new GregorianCalendar();
				readDate(sc,date);
				System.out.println("Introducir 'tipo':");
				String shiftType = sc.next();
				t1 = new Turno(date, shiftType);
				System.out.println("Se ha creado el turno t1!\n");
				System.out.println("-- t1 --");
				printShift(t1);
				if (t2.getDate() != null) {
					System.out.println("-- t2 --");
					printShift(t2);
				}

				break;
			}
			case 3:{
				System.out.println("3: Turno (GregorianCalendar date, String shiftType, String specialDate, int numberOfDoctors)\n");
				GregorianCalendar date = new GregorianCalendar();
				readDate(sc,date);
				System.out.println("Introducir 'tipo':");
				String shiftType = sc.next();
				System.out.println("Introducir 'fecha especial':");
				String specialDate = sc.next();
				System.out.println("Introducir 'numero de doctores':");
				int numberOfDoctors = sc.nextInt();
				t1 = new Turno(date, shiftType, specialDate, numberOfDoctors);
				System.out.println("Se ha creado el turno t1!\n");
				System.out.println("-- t1 --");
				printShift(t1);
				if (t2.getDate() != null) {
					System.out.println("-- t2 --");
					printShift(t2);
				}
				break;
			}
			case 4:{
				System.out.println("4: Turno(Turno T)");
				if (t1.getDate() != null) {
					t2 = new Turno(t1);
					System.out.println("\nSe ha creado el turno t2, que es copia del turno t1!\n");
					System.out.println("-- t1 --");
					printShift(t1);
					System.out.println("-- t2 --");
					printShift(t2);
				}
				else {
					System.out.println("t1 (T turno origen) no esta inicializado!\n");
				}
				break;
			}

			case 5:{
				System.out.println("5: void setSpecialDate(String specialDate)\n");
				System.out.println("Introducir 'fecha especial':");
				String specialDate = sc.next();
				t1.setSpecialDate(specialDate);
				System.out.println("Se ha modificado la fecha especial del turno t1!\n");
				System.out.println("-- t1 --");
				printShift(t1);
				break;
			}
			case 6:{
				System.out.println("6: void setNumberOfDoctors(int numberOfDoctors)\n");
				System.out.println("Introducir 'numero de doctores':");
				int numberOfDoctors = sc.nextInt();
				t1.setNumberOfDoctors(numberOfDoctors);
				System.out.println("Se ha modificado el numero de doctores del turno t1!\n");
				System.out.println("-- t1 --");
				printShift(t1);
				break;
			}
			case 7:{
				System.out.println("7: GregorianCalendar getDate()\n");
				if (t1.getDate() != null) {
					System.out.print("Fecha de t1: ");
					printDate(t1.getDate());
					System.out.println();
				}
				else System.out.println("t1 no esta inicializado!");
				System.out.println();
				break;
			}
			case 8:{
				System.out.println("8: String getShiftType()\n");
				if (t1.getDate() != null) System.out.println("Tipo de t1: " + t1.getShiftType());
				else System.out.println("t1 no esta inicializado!");
				System.out.println();
				break;
			}
			case 9:{
				System.out.println("9: String getSpecialDate()\n");
				if (t1.getDate() != null) System.out.println("Fecha especial de t1: " + t1.getSpecialDate());
				else System.out.println("t1 no esta inicializado!");
				System.out.println();
				break;
			}
			case 10:{
				System.out.println("10: int getNumberOfDoctors()\n");
				if (t1.getDate() != null) System.out.println("Numero de doctores de t1: " + t1.getNumberOfDoctors());
				else System.out.println("t1 no esta inicializado!");
				System.out.println();
				break;
			}
			case 0:{
				System.out.println("Salir? {s|n}: \n");
				answer = sc.next();
				while (!(answer.equals("s") || answer.equals("n"))) {
					System.out.println("Elegir una de las siguientes opciones:");
					System.out.println("s: Salir     |     n: Continuar");
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
