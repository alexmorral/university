package drivers;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.io.IOException;

import domain.*;


public class DriverController {
	
	private static void muestraOpciones() {
		System.out.print("¿Que desea hacer?\n\n");
		System.out.print("1: Anadir Doctor al Hospital\n");
		System.out.print("2: Modificar un Doctor\n");
		System.out.print("3: Anadir un dia al Calendario\n");
		System.out.print("4: Anadir restriccion MAX turnos por dia \n");
		System.out.print("5: Anadir restriccion MAX turnos en un rango de fechas\n");
		System.out.print("6: Anadir restriccion NOT dia mes\n");
		System.out.print("7: Anadir restriccion NOT dia semana\n");
		System.out.print("8: Anadir restriccion NOT dia especial\n");
		System.out.print("9: Anadir restriccion NOT fecha\n");
		System.out.print("10: Anadir restriccion NOT turno\n");
		System.out.print("11: Anadir restriccion XOR\n");
		System.out.print("12: Ejecutar MaxFlow (FordFulkerson)\n");
		System.out.print("13: Ejecutar MaxFlow (EdmondsKarp)\n");
		System.out.print("14: Ejecutar MaxFlow (Dijkstra)\n");
		System.out.print("0: Salir\n");
	}
	
public static void main(String[] args) throws IOException{
		
		Scanner teclado;
		teclado = new Scanner(System.in);
	    
	    CtrlHospital CtrlHosp = new CtrlHospital();

	    int id, ini, fin, idRes, maxT, d1, m1, a1, numMax;
		String nombre, fecha, especial, turno;
		double sueldo;
	    double fm, ft, fn;
	    int year;
	    ArrayList<Integer> docs;
	    HashMap<Integer, ArrayList<String>> asign;
	    Set<Integer> ids;
	    
	    
		System.out.print("Introduce el ID del hospital: ");
		id = teclado.nextInt();
		System.out.print("Introduce el nombre del hospital: ");
		nombre = teclado.next();
		System.out.print("Introduce el factorManana: ");
		fm = teclado.nextDouble();
		System.out.print("Introduce el factorTarde: ");
		ft = teclado.nextDouble();
		System.out.print("Introduce el factorNoche: ");
		fn = teclado.nextDouble();
		System.out.print("Introduce el anyo del Calendario: ");
		year = teclado.nextInt();
		try {
			CtrlHosp.crearHospital(id, nombre, fm, ft, fn);
			System.out.println("Hospital Creado Correctamente");
		} catch (IOException e) {
			System.out.println(e);
		}
		CtrlHosp.addCalendar(year);
	    CtrlCalendario CtrlCal = new CtrlCalendario(CtrlHosp.getCalendar());
	    CtrlDoctor CtrlDoct = new CtrlDoctor(CtrlHosp.getDoctors(), CtrlCal.getCalendarYear());
	    CtrlAlgorithm CtrlAlg = new CtrlAlgorithm(CtrlHosp.getHospital());
	    
		int opcion = -1;
		muestraOpciones();
		opcion = teclado.nextInt();
		
		
		while(opcion != 0){
			
			switch(opcion){
				case 1:
					System.out.print("Introduce el ID del doctor: ");
					id = teclado.nextInt();
					System.out.print("Introduce el nombre del doctor: ");
					nombre = teclado.next();
					System.out.print("Introduce el numero máximo de turnos del doctor: ");
					numMax = teclado.nextInt();
					System.out.print("Introduce el sueldo del doctor: ");
					sueldo = teclado.nextDouble();
					
					try {
						CtrlHosp.crearDoctor(id, nombre, numMax, sueldo);
						System.out.println("Doctor Creado Correctamente");
					} catch(IOException e) {
						System.out.println(e);
					}
					break;
				
				case 2:
					System.out.print("Introduce el ID del doctor que quieres eliminar: ");
					id = teclado.nextInt();
				
					try {
						CtrlHosp.eliminarDoctor(id);
						System.out.println("Doctor Eliminado Correctamente");
					} catch(IOException e) {
						System.out.println(e);
					}
					break;
					
				case 3:
					int dia, mes, yea, numDocM, numDocT, numDocN;
					System.out.print("Introduce el dia: ");
					dia = teclado.nextInt();
					System.out.print("Introduce el mes: ");
					mes = teclado.nextInt();
					System.out.print("Introduce el year: ");
					yea = teclado.nextInt();
					System.out.print("Introduce el numero de doctores para el turno morning: ");
					numDocM = teclado.nextInt();
					System.out.print("Introduce el numero de doctores para el turno afternoon: ");
					numDocT = teclado.nextInt();
					System.out.print("Introduce el numero de doctores para el turno night: ");
					numDocN = teclado.nextInt();
					System.out.print("Introduce un dia especial [navidad semana_santa noche_vieja noche_buena]: ");
					especial = teclado.next();
					try {
						CtrlCal.addVacationDayDeprecated(dia, mes, yea, numDocM, numDocT, numDocN, especial,especial,especial);
						System.out.println("Fecha (3 turnos) introducida correctamente");
					} catch(IOException e) { System.out.println(e); }
					break;
				
				case 4:
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					System.out.print("Introduce el maximo de turnos por dia\n");
					maxT = teclado.nextInt();
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					try {
						CtrlDoct.addResMAX_Turnos_por_Dia(id, idRes, maxT);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
				
				case 5:
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					System.out.print("Introduce la fecha inicial (dd MM yyyy)\n");
					d1 = teclado.nextInt();
					m1 = teclado.nextInt();
					a1 = teclado.nextInt();
					System.out.print("Introduce la fecha final (dd MM yyyy)\n");
					int d2 = teclado.nextInt();
					int m2 = teclado.nextInt();
					int a2 = teclado.nextInt();
					System.out.print("Introduce el maximo de turnos en el rango\n");
					maxT = teclado.nextInt();
					try {
						CtrlDoct.addResMAX_Turnos_Rango(id, idRes, d1, m1, a1, d2, m2, a2, maxT);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
					
				case 6:
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					System.out.print("Introduce el dia del mes\n");
					int dia_mes = teclado.nextInt();
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					try {
						CtrlDoct.addResNOT_Dia_Mes(id, idRes, dia_mes);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
				
				case 7:
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					System.out.print("Introduce el dia de la semana [lunes martes miercoles jueves viernes sabado domingo]\n");
					String diaSem = teclado.next();
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					try {
						CtrlDoct.addResNOT_Dia_Semana(id, idRes, diaSem);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
				
				case 8:
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					System.out.print("Introduce el dia especial [navidad semana_santa noche_vieja noche_buena]\n");
					especial = teclado.next();
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					try {
						CtrlDoct.addResNOT_Especial(id, idRes, especial);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
					
				case 9:
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					System.out.print("Introduce la fecha (dd MM yyyy)\n");
					d1 = teclado.nextInt();
					m1 = teclado.nextInt();
					a1 = teclado.nextInt();
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					try {
						CtrlDoct.addResNOT_Fecha(id, idRes, d1, m1, a1);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
					
				case 10:
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					System.out.print("Introduce el turno [manana tarde noche]\n");
					turno = teclado.next();
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					try {
						CtrlDoct.addResNOT_Turno(id, idRes, turno);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
				
				case 11:
					ArrayList<Integer> d = new ArrayList<Integer>();
					ArrayList<Integer> m = new ArrayList<Integer>();
					ArrayList<Integer> a = new ArrayList<Integer>();
					ArrayList<String> turnos = new ArrayList<String>();
					System.out.print("Introduce el ID del doctor\n");
					id = teclado.nextInt();
					String opt = "S";
					while(opt.equals("s") || opt.equals("S")) {
						System.out.print("Introduce la fecha (dd MM yyyy)\n");
						d1 = teclado.nextInt();
						d.add(d1);
						m1 = teclado.nextInt();
						m.add(m1);
						a1 = teclado.nextInt();
						a.add(a1);
						System.out.print("Introduce el turno [manana tarde noche]\n");
						turno = teclado.next();
						turnos.add(turno);
						System.out.print("Quieres anadir otro turno? (S/s N/n)\n");
						opt = teclado.next();
					}
					System.out.print("Introduce el id de la restriccion\n");
					idRes = teclado.nextInt();
					try {
						CtrlDoct.addResXOR(id, idRes, d, m, a, turnos);
						System.out.println("Restriccion anadida correctamente al doctor: id="+ id);
					} catch (IOException e) { System.out.println(e); }
					break;
					
				case 12:
					CtrlAlg.generateGraf();
					docs = CtrlDoct.getAllDoctors();
					ini = 0;
					fin = docs.size()+1;
					CtrlAlg.findMaxFlowFulk(ini, fin);
					CtrlAlg.findSolution(ini, fin);
					asign = CtrlAlg.getDatesAsigned();
					ids = asign.keySet();
					for (Integer idDoc : ids){
						ArrayList<String> fechasAsignedDoc = asign.get(idDoc);
						System.out.printf("Doctor %d\n", idDoc);
						for(String st : fechasAsignedDoc){
							System.out.printf("%s\n",st);
						}
						System.out.printf("Sueldo: %f\n\n", CtrlAlg.getSueldoAsigned().get(idDoc));
					}
					
					System.out.println("Turnos sin solucion:");
					for(String st : CtrlAlg.getTurnosSinSol()) {
						System.out.println(st);
					}
					break;
					
				case 13:
					CtrlAlg.generateGraf();
					docs = CtrlDoct.getAllDoctors();
					ini = 0;
					fin = docs.size()+1;
					CtrlAlg.findMaxFlowEk(ini, fin);
					CtrlAlg.findSolution(ini, fin);
					asign = CtrlAlg.getDatesAsigned();
					ids = asign.keySet();
					for (Integer idDoc : ids){
						ArrayList<String> fechasAsignedDoc = asign.get(idDoc);
						System.out.printf("Doctor %d\n", idDoc);
						for(String st : fechasAsignedDoc){
							System.out.printf("%s\n",st);
						}
						System.out.printf("Sueldo: %f\n\n", CtrlAlg.getSueldoAsigned().get(idDoc));
					}
					
					System.out.println("Turnos sin solucion:");
					for(String st : CtrlAlg.getTurnosSinSol()) {
						System.out.println(st);
					}
					break;
				case 14:
					CtrlAlg.generateGraf();
					docs = CtrlDoct.getAllDoctors();
					ini = 0;
					fin = docs.size()+1;
					CtrlAlg.findMaxFlowDijk(ini, fin);
					CtrlAlg.findSolution(ini, fin);
					asign = CtrlAlg.getDatesAsigned();
					ids = asign.keySet();
					for (Integer idDoc : ids){
						ArrayList<String> fechasAsignedDoc = asign.get(idDoc);
						System.out.printf("Doctor %d\n", idDoc);
						for(String st : fechasAsignedDoc){
							System.out.printf("%s\n",st);
						}
						System.out.printf("Sueldo: %f\n\n", CtrlAlg.getSueldoAsigned().get(idDoc));
					}
					
					System.out.println("Turnos sin solucion:");
					for(String st : CtrlAlg.getTurnosSinSol()) {
						System.out.println(st);
					}
					
					break;
				default: break;
			}
			muestraOpciones();
			opcion = teclado.nextInt();
		}
		teclado.close();
		System.out.print("PROGRAM EXIT");
	}
	
	
	
}
