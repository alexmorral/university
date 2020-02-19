package drivers;
import java.util.GregorianCalendar;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.ArrayList;

import model.MAX_Turnos_Rango;
import model.MAX_Turnos_por_Dia;
import model.NOT_Dia_Mes;
import model.NOT_Dia_Semana;
import model.NOT_Especial;
import model.NOT_Fecha;
import model.NOT_Turno;
import model.Restriccion;
import model.Turno;
import model.XOR;


public class DriverRestriccion {
	
	
	static void MostrarRestriccion(Restriccion RES){
		String TIPO = RES.getTipo(); 
		
		switch(TIPO){
		case "XOR":{
			ArrayList<Turno> listXOR = ((XOR) RES).getListTurnos();
			System.out.print("Los turnos de la restriccion " + TIPO + " son: \n");
			
			for (Turno turno : listXOR){
				GregorianCalendar fecha = turno.getDate();
				System.out.print(fecha.get(fecha.DAY_OF_MONTH));
				System.out.print("/" + (fecha.get(fecha.MONTH)+ 1) + "/");
				System.out.print(fecha.get(fecha.YEAR)+ "\n");
				//System.out.print("Full info: " + fecha + "\n");
				System.out.print(turno.getShiftType()+"\n\n");
				
			}
			break;
		}
		
		case "NOT_Dia_Mes" :{ 
			NOT_Dia_Mes N = (NOT_Dia_Mes)RES;
			int  dia = ((NOT_Dia_Mes) RES).getDiaMes(); 
			
			System.out.print("El dia del mes de la restriccion " + TIPO + " es " + dia + "\n");
			
			break;
		}
		case "NOT_Dia_Semana" :{ 
			String  dia = ((NOT_Dia_Semana)RES).getDiaSemana(); 
			System.out.print("El dia de la semana de la restriccion " + TIPO + " es " + dia + "\n");
			
			break;
		}
		
		case "NOT_Especial" :{
			String  especial = ((NOT_Especial)RES).getEspecial(); 
			System.out.print("El dia Especial de la restriccion " + TIPO + " es " + especial + "\n");;
			
			break;
		}
		
		case "NOT_Fecha" :{ 
			GregorianCalendar fecha = ((NOT_Fecha)RES).getFecha();
		
			System.out.print("El Dia de la restriccion " + TIPO + " es: ");
			System.out.print(fecha.get(fecha.DAY_OF_MONTH));
			System.out.print( "/" + (fecha.get(fecha.MONTH) + 1) + "/");
			System.out.print(  fecha.get(fecha.YEAR)+ "\n\n");
			//System.out.print("Full info: " + fecha + "\n");
			break;
		}
		
		case "NOT_Turno" :{
			String  turno = ((NOT_Turno)RES).getTipoTurno(); 
			
			System.out.print("El turno de la restriccion " + TIPO + " es " + turno + "\n");
			break;
		}
		
		case "MAX_Turnos_por_Dia" :{
			int  numt = ((MAX_Turnos_por_Dia)RES).getNumTurnos(); 
			
			System.out.print("El maximo de turnos de la restriccion " + TIPO + " es " + numt + "\n");
			break;
		}
		
		case "MAX_Dias_Rango"  :{
			
			MAX_Turnos_Rango N = ((MAX_Turnos_Rango)RES);
			GregorianCalendar fechaIni = ((MAX_Turnos_Rango)RES).getFechaIni();
			GregorianCalendar fechaFin = ((MAX_Turnos_Rango)RES).getFechaFin();
			int  numDias = ((MAX_Turnos_Rango)RES).getNumDias();
			
			System.out.print("La fecha inicial de la restriccion" + TIPO + " es ");
			System.out.print(fechaIni.get(fechaIni.DAY_OF_MONTH));
			System.out.print("/" + (fechaIni.get(fechaIni.MONTH) + 1) + "/");
			System.out.print(fechaIni.get(fechaIni.YEAR)+ "\n\n");
			//System.out.print("Full info: " + fechaIni + "\n\n");
			
			System.out.print("La fecha final es ");
			System.out.print(fechaFin.get(fechaFin.DAY_OF_MONTH));
			System.out.print("/" + (fechaFin.get(fechaFin.MONTH) + 1) + "/");
			System.out.print(fechaFin.get(fechaFin.YEAR)+ "\n\n");
			//System.out.print("Full info: " + fechaFin + "\n\n");
			
			System.out.print("El numero maximo de dias a trabajar es " + numDias + "\n");
			break;
		}
		
		}
		return;
	}
	

	public static void main(String[] args) {
		
		Scanner teclado;
		teclado = new Scanner(System.in);
		
		int id = -1;
		int opcion = -1;
		String seguir;
		Restriccion RES = new Restriccion(); 
		
		while(opcion != 0){
			
			System.out.print("-----------------------------------------------------\n");
			System.out.print("|               ¿Que desea hacer?                   |\n");
			System.out.print("|---------------------------------------------------|\n");
			System.out.print("| 1: Crear Restriccion tipo XOR                     |\n");
			System.out.print("| 2: Crear Restriccion tipo NOT_Dia_Mes             |\n");
			System.out.print("| 3: Crear Restriccion tipo NOT_Dia_Semana          |\n");
			System.out.print("| 4: Crear Restriccion tipo NOT_Especial            |\n");
			System.out.print("| 5: Crear Restriccion tipo NOT_Fecha               |\n");
			System.out.print("| 6: Crear Restriccion tipo NOT_Turno               |\n"); 
			System.out.print("| 7: Crear Restriccion tipo MAX_Turnos_por_Dia      |\n");
			System.out.print("| 8: Crear Restriccion tipo MAX_Dias_Rango          |\n");
			System.out.print("| 9: Ver Restriccion ya creada                      |\n");
			System.out.print("-----------------------------------------------------\n");
			
			
			opcion = teclado.nextInt(); //leemos opcion
				
			switch(opcion){
				case 1:{ //XOR
					++id;
					int next;//Gestionar el bucle interno
					ArrayList<Turno> listXOR = new ArrayList<Turno>();
					int dia, mes, anio;
					String turno;
					System.out.print("Introduce una fecha (dd MM aaaa) \n");
					dia = teclado.nextInt();
					mes = teclado.nextInt();
					anio = teclado.nextInt();
					System.out.print("Introduce su correspondiente turno (mañana/noche/tarde) \n");
					turno = teclado.next();
					RES = new XOR(id, listXOR);
					((XOR) RES).AddTurno(dia, mes, anio, turno);
					
					do {
						next = -1;
						
						System.out.print("Introduce otra fecha (dd MM aaaa) \n");
						dia = teclado.nextInt();
						mes = teclado.nextInt();
						anio = teclado.nextInt();
						System.out.print("Introduce su correspondiente turno (mañana/tarde/noche) \n");
						turno = teclado.next();
						
						((XOR) RES).AddTurno(dia, mes, anio,turno);
												
						while(next == -1){//GESTION DE SEGUIR AÑADIENDO FECHAS
							System.out.print("Seguir añadiendo fechas? (Si/No) \n");
							seguir = teclado.next();
	
							if(seguir.equals("No") || seguir.equals("no") || seguir.equals("NO")
									|| seguir.equals("N") || seguir.equals("n"))next = 0;
							
							else if(seguir.equals("Si") || seguir.equals("si") || seguir.equals("SI")
									|| seguir.equals("S") || seguir.equals("s")) next = 1;
							else System.out.print("WRONG ANSWER \n");
								
						}
						
						
					} while(next == 1);
					
					System.out.print("DONE\n");
					break;
				}
				case 2: { //NOT_Dia_Mes
					++id;
					int dia;
					System.out.print("Introduce el dia del mes(1-31) que no quieras trabajar\n");
					dia = teclado.nextInt();
					RES = new NOT_Dia_Mes(id, dia);
					System.out.print("DONE\n");
					break;
					
				}
				case 3:{ //NOT_Dia_Semana
					++id;
					String dia;
					System.out.print("Introduce el dia de la semana(Lunes-Domingo) que no quieras trabajar\n");
					dia = teclado.next();
					RES = new NOT_Dia_Semana(id, dia);
					System.out.print("DONE\n");
					break;
				}
				case 4:{ //NOT_Especial
					++id;
					String dia;
					System.out.print("Introduce el dia Especial(Navidad,SantGervasi,...) que no quieras trabajar\n");
					dia = teclado.next();
					RES = new NOT_Especial(id, dia);
					System.out.print("DONE\n");
					break;
				}
				case 5:{ //NOT_Fecha
					
					++id;
					int dia, mes, anio;
					System.out.print("Introduce una fecha (dd/MM/aaaa) que no quieras trabajar\n");
					dia = teclado.nextInt();
					mes = teclado.nextInt();
					anio = teclado.nextInt();
					RES = new NOT_Fecha(id, dia, mes, anio);
					System.out.print("DONE\n");
					break;
				}
					
					
					
				case 6:{ //NOT_Turno
					++id;
					String Turno;
					System.out.print("Introduce el Turno (Mañana/Tarde/Noche) que no quieras trabajar\n");
					Turno = teclado.next();
					RES = new NOT_Especial( id, Turno);
					System.out.print("DONE\n");
					break;
				}
				case 7:{ //MAX_Turnos_por_Dia
					++id;
					int dias;
					System.out.print("Introduce el maximo de turnos por dia que quieras trabajar (1 o 2)\n");
					dias = teclado.nextInt();
					RES = new MAX_Turnos_por_Dia(id, dias);
					System.out.print("DONE\n");
					break;
				}
				
				
				case 8:{ //MAX_Turnos_Rango
					++id;
					int diaI,diaF,mesI,mesF,anioI,anioF,turnos;
					System.out.print("Introduce la fecha inicial (dd MM aaaa) del rango de dias\n");
					diaI = teclado.nextInt();
					mesI = teclado.nextInt();
					anioI = teclado.nextInt();
					System.out.print("Introduce la fecha final (dd MM aaaa) del rango de dias\n");
					diaF = teclado.nextInt();
					mesF = teclado.nextInt();
					anioF = teclado.nextInt();
					System.out.print("Introduce el numero de turnos\n");
					turnos = teclado.nextInt();
					
					RES = new MAX_Turnos_Rango(id, diaI, diaF, mesI, mesF, anioI, anioF,turnos);
					System.out.print("DONE\n");
					break;
					
				}
				
				case 9:{
					if(id != -1) MostrarRestriccion(RES);
					else System.out.print("Aun no hay ninguna Restriccion!\n\n");
					break;
				}
				
				default: break;
			
			}
			
			// GESTION DE SALIDA DEL BUCLE
			opcion = -1;
			
			while(opcion == -1){
				System.out.print("\n Desea Continuar? (Si/No)\n");
				seguir = teclado.next();
				if(seguir.equals("No") || seguir.equals("no") || seguir.equals("N")
					|| seguir.equals("NO") || seguir.equals("n")) opcion = 0; 
				else if(seguir.equals("Si") || seguir.equals("si") || seguir.equals("S")
							|| seguir.equals("SI") || seguir.equals("s")) opcion = 99; 
				else System.out.print("WRONG ANSWER \n");
			}
			
			
		}
		System.out.print("PROGRAM EXIT");
	}
	
}
