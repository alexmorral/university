/*  Autor: Sergi Orra  */

package drivers;
import java.util.*;

import model.*;
import java.text.ParseException;

public class DriverDoctor {
	
	
	public static void main(String args[]) throws ParseException {
		Scanner teclado;
		teclado = new Scanner(System.in);		
		int nummax, iddoctor, opcion, id;
		opcion = id = -1;
		String nombredoctor;
		double sueldo;
		boolean rescorrecta = true;
		Doctor doc = new Doctor();
		Restriccion res = new Restriccion();
		
		System.out.print("------------------------------------\n");
		System.out.print("Que desea hacer?\n");
		System.out.print("0: Terminar programa\n");
		System.out.print("1: Crear Doctor\n");
		System.out.print("2: Add restriccion al doctor\n");
		System.out.print("3: Eliminar restriccion a un doctor\n");
		System.out.print("4: Consultar retriccions del Doctor\n");
		System.out.print("5: Consultar atributos del Doctor (Sin restricciones)\n");
		System.out.print("6: Modificar atributos del Doctor\n");
		System.out.print("7: Eliminar todas las restricciones del Doctor\n");
		System.out.print("------------------------------------\n");
			
		opcion = teclado.nextInt(); //leemos opcion
		
		while(opcion != 0){	
			if (opcion == 1){
				if (id == -1) {
					id = 1;
					System.out.printf("Ingrese el Id, Nombre, Numero maximo de turnos y Sueldo del Doctor\n");
					iddoctor = teclado.nextInt();
					nombredoctor = teclado.next();
					nummax = teclado.nextInt();	
					sueldo = teclado.nextDouble();
					doc = new Doctor(iddoctor, nombredoctor, nummax, sueldo);
					System.out.printf("El Doctor %d ha sido creado.\n", doc.getId());	
				}
				else System.out.printf("El Doctor ya esta creado.\n");
			}
			else if (opcion == 2) {
				if (id == -1) System.out.printf("El Doctor aun no esta creado.\n");
				else {
					System.out.printf("Ingrese la id y el tipo de restriccion\n");
					int idRestriccion = teclado.nextInt();
					String tipoRestriccion = teclado.next();
					if (tipoRestriccion.equals("MAX_Turnos_Rango")) {
						System.out.printf("Restriccion tipo MAX_Turnos_Rango, ingrese la fecha inico (dd MM aaaa), fecha fin y el numero de Turnos.\n");
						int numTurnos;
						int d1, d2, m1, m2, a1, a2;
						d1 = teclado.nextInt();
						m1 = teclado.nextInt();
						a1 = teclado.nextInt();
						d2 = teclado.nextInt();
						m2 = teclado.nextInt();
						a2 = teclado.nextInt();
						numTurnos = teclado.nextInt();
						res = new MAX_Turnos_Rango(idRestriccion, d1, m1, a1, d2, m2, a2, numTurnos);
					}
					else if (tipoRestriccion.equals("MAX_Turnos_Consecutivos")) {
						System.out.printf("Restriccion tipo MAX_Turnos_Consecutivos, ingrese el numero maximo de turnos.\n");
						int numTurnos;
						numTurnos = teclado.nextInt();
						res = new MAX_Turnos_Consecutivos(idRestriccion, numTurnos);
					}
					else if (tipoRestriccion.equals("MAX_Turnos_por_Dia")) {
						System.out.printf("Restriccion tipo MAX_Turnos_por_Dia, ingrese el numero maximo de turnos.\n");
						int numTurnos;
						numTurnos = teclado.nextInt();
						res = new MAX_Turnos_por_Dia(idRestriccion, numTurnos);
					}
					else if (tipoRestriccion.equals("NOT_Dia_Mes")) {
						System.out.printf("Restriccion tipo NOT_Dia_Mes, ingrese el dia del mes.\n");
						int diaMes;
						diaMes = teclado.nextInt();
						res = new NOT_Dia_Mes(idRestriccion, diaMes);
					}
					else if (tipoRestriccion.equals("NOT_Dia_Semana")) {
						System.out.printf("Restriccion tipo NOT_Dia_Setmana, ingrese el dia de la semana.\n");
						String diaSemana;
						diaSemana = teclado.next();
						res = new NOT_Dia_Semana(idRestriccion, diaSemana);
					}
					else if (tipoRestriccion.equals("NOT_Especial")) {
						System.out.printf("Restriccion tipo NOT_Especial, ingrese el dia especial.\n");
						String diaEspecial;
						diaEspecial = teclado.next();
						res = new NOT_Especial(idRestriccion, diaEspecial);
					}
					else if (tipoRestriccion.equals("NOT_Fecha")) {
						System.out.printf("Restriccion tipo NOT_Fecha, ingrese la fecha (dd MM aaaa).\n");
						int d, m, a;
						d = teclado.nextInt();
						m = teclado.nextInt();
						a = teclado.nextInt();
						res = new NOT_Fecha(idRestriccion, d, m, a);
					}
					else if (tipoRestriccion.equals("NOT_Turno")) {
						System.out.printf("Restriccion tipo NOT_Turno, ingrese el tipo de turno.\n");
						String tipoDia;
						tipoDia = teclado.next();
						res = new NOT_Turno(idRestriccion, tipoDia);
					}
					else if (tipoRestriccion.equals("XOR")) {
						System.out.printf("Restriccion tipo XOR, ingrese las diferentas fechas (dd MM aaaa) y el tipoTurno (manana, tarde, noche) de cada una. Pon un 0 en el dd para terminar.\n");
						ArrayList<Turno> listaXOR = new ArrayList<Turno>();
						int d, m, a;
						String tipoTurno;
						res = new XOR(idRestriccion, listaXOR);
						d = teclado.nextInt();
						while (d != 0) {
							m = teclado.nextInt();
							a = teclado.nextInt();
							tipoTurno = teclado.next();
							((XOR)res).AddTurno(d, m, a, tipoTurno);
							d = teclado.nextInt();
						}	
					}	
					else {
						System.out.printf("Restriccion erronea\n");
						rescorrecta = false;
					}
					if (rescorrecta) {
						boolean c = doc.addRestriction(res);
						if (c) System.out.printf("Restriccion anadida.\n");	
						else System.out.printf("Ya existe una restriccion con este Id.\n");
					}
				}
			}
			else if (opcion == 3) {
				if (id == -1) System.out.printf("El Doctor aun no esta creado.\n");
				else {
					if (doc.isREmpty()) System.out.printf("El Doctor no tiene ninguna restriccion por eliminar");
					else {
						System.out.printf("Ingrese la id de la restriccion que quieras eliminar\n");
						int idRestriccion = teclado.nextInt();
						boolean d = doc.deleteRestriction(idRestriccion);
						if (d) System.out.printf("Restriccion eliminada.\n");	
						else System.out.printf("Esta Id de restriccion no existe.\n");
					}
				}
			}
			else if (opcion == 4) {
				if (id == -1) System.out.printf("El Doctor aun no esta creado.\n");
				else {
					if (doc.isREmpty()) System.out.printf("El Doctor no tiene ninguna restriccion\n");
					else {
						System.out.printf("El Doctor contiene %d restriccion/es:\n\n", doc.Rsize());
						ArrayList<Restriccion> restr = new ArrayList<Restriccion>();
						restr = doc.getRestrictions();
						for (int i = 0; i < restr.size(); ++i) {
							System.out.printf("Id: %d\nTipo: %s\n\n", restr.get(i).getIdRestriccion(), restr.get(i).getTipo());
						}
					}
				}
			}
			else if (opcion == 5) {
				if (id == -1) System.out.printf("El Doctor aun no esta creado.\n");
				else {
					System.out.printf("Atributos del Doctor:\n");
					System.out.printf("Id: %d\nNombre: %s\nnumMaxTurnos: %d\nsueldoTurno: %f\n", doc.getId(), doc.getName(), doc.getNumMaxTurn(), doc.getSalaryTurn());
				}
			}
			else if (opcion == 6) {
				if (id == -1) System.out.printf("El Doctor aun no esta creado.\n");
				else {
					System.out.printf("Ingrese los nuevos atributos que quieres que tenga el doctor (Id, nombre, numMaxTurnos y sueldoTurno):\n");
					int idd = teclado.nextInt();
					String nombre = teclado.next();
					int nummaxturnos = teclado.nextInt();
					double sueldoturno = teclado.nextDouble();
					doc.setId(idd);
					doc.setName(nombre);
					doc.setNumMaxTurn(nummaxturnos);
					doc.setSalaryTurn(sueldoturno);
					System.out.printf("Atributos modificados correctamente");
				}	
			}
			else if (opcion == 7) {
				if (id == -1) System.out.printf("El Doctor aun no esta creado.\n");
				else {
					doc.clearRestrictions();
					System.out.printf("Restricciones eliminadas");			
				}
			}
			System.out.print("\n------------------------------------\n");
			System.out.print("Que desea hacer?\n");
			System.out.print("0: Terminar programa\n");
			System.out.print("1: Crear Doctor\n");
			System.out.print("2: Add restriccion al doctorr\n");
			System.out.print("3: Eliminar restriccion a un doctor\n");
			System.out.print("4: Consultar retriccions del Doctor\n");
			System.out.print("5: Consultar atributos del Doctor (Sin restricciones)\n");
			System.out.print("6: Modificar atributos del Doctor\n");
			System.out.print("7: Eliminar todas las restricciones del Doctor\n");
			System.out.print("------------------------------------\n");
			opcion = teclado.nextInt();		
		}
		teclado.close();
	}
}