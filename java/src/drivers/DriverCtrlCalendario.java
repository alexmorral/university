package drivers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import java.io.IOException;
import data.CtrlDatosFichero;
import domain.*;
import model.*;

public class DriverCtrlCalendario {
	static void MostrarTurno(Turno t){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	System.out.printf("--------------------.\n");
	GregorianCalendar c1 = t.getDate();
	
	String fecha = sdf.format(c1.getTime());
	System.out.printf("fecha: %s\n", fecha);
	System.out.printf("tipoturno: %s\n",t.getShiftType());
	System.out.printf("especial: %s\n",t.getSpecialDate());
	System.out.printf("numDoctores: %d\n",t.getNumberOfDoctors());
	System.out.printf("--------------------.\n");

}

	
	
	
	public static void main(String[] args) throws ParseException {
		
		Scanner sc = new Scanner(System.in);
		String answer;
		boolean exit = false;
		int op;
		CtrlDatosFichero inOut= new CtrlDatosFichero();
		CtrlHospital Ho = new CtrlHospital();
		
		/*
		int year=-1;
		int id= 100;
		try{
			Ho.cargarHospital(id);
			if(inOut.existsCalendar(id)){
				year=inOut.getYear(id,null);
			}
		}catch (IOException e){System.out.print("fallo");}
		Ho.addCalendar(year);
		CtrlCalendario cal = new CtrlCalendario(Ho.getCalendar());
		
		Calendario calendar1 = cal.getCalendar();
		System.out.print("Driver CtrlCalendario\n");
		if(calendar1.getNumberOfVacationDates()==0) System.out.printf("Actualment l'Hospital no te calendari\n");
		else {
			System.out.printf("El calendari te %d Turns Vacacionals\n",calendar1.getNumberOfShifts());
			ArrayList<Turno> alturno = calendar1.getALLShifts();
			for(int i=0;i<alturno.size();++i){
				MostrarTurno(alturno.get(i));
			}
		}
		*/

		while(!exit){
			System.out.println();
			System.out.println("-- Menu Principal --\n");
			System.out.println("1: readCalendar \n");
			System.out.println("2: addVacationDay \n");
			System.out.println("3: modifyVacationDay \n");			
			System.out.println("4: deleteVacationDay \n");
			System.out.println("5: writeCalendar \n");
			System.out.println("6:  getCalendarYear \n");
			System.out.println(" 0: Salir\n");
			
			
			op = sc.nextInt();

			switch(op){
			/*case 1:{
				
				System.out.print("Stub:Hospital id = 100, No te calendari\n");
				try{
				
				System.out.println();
				System.out.print("introduzca el id de un Hospital para cargar su calendario o 100 para seguir con Stub\n");
				id = sc.nextInt();
				cal.readCalendar(id);
				}catch (IOException e){System.out.print("No se ha podido leer");}
				System.out.print("Se ha cargado el calendario\n");
				Calendario calendar = cal.getCalendar();
				if(calendar.getNumberOfVacationDates()==0) System.out.printf("Actualment l'Hospital no te calendari\n");
				else {
					System.out.printf("El calendari te %d Turns Vacacionals\n",calendar.getNumberOfShifts());
					ArrayList<Turno> alturno = calendar.getALLShifts();
					for(int i=0;i<alturno.size();++i){
						MostrarTurno(alturno.get(i));
					}
				}
				break;
			}
			case 2:{
				int dia,mes,any,morningDrs,eveningDrs,nightDrs;
				String especialDate = null;
				System.out.print("Creando fecha!\n");
				System.out.print("Introduzca el dia del mes\n");
				dia = sc.nextInt();
				System.out.print("Introduzca el mes del anyo (1-12)\n");
				mes = sc.nextInt();
				System.out.print("Introduzca el anyo\n");
				any = sc.nextInt();
				System.out.print("Introduzca el numero de doctores manana\n");
				morningDrs= sc.nextInt();
				System.out.print("Introduzca el numero de doctores tarde\n");
				eveningDrs=sc.nextInt();
				System.out.print("Introduzca el numero de doctores noche\n");
				nightDrs=sc.nextInt();
				System.out.print("Quieres que sea fecha especial? (s o n)\n");
				String sino = sc.next();
				if(sino.equals("s")){
				System.out.print("Introduzca que fecha especial es! (navdiad,reyes..)\n");
				especialDate=sc.next();
				}
				try{
				cal.addVacationDay(dia,mes,any,morningDrs,eveningDrs,nightDrs,especialDate,especialDate,especialDate);
				cal.writeCalendar(id);
				} catch (IOException e){System.out.print("NO SE HA PODIDO Introducir\n");}
				break;
			}
			*/
			
			case 1:
				System.out.println("ReadCalendar\n");
				System.out.println("Esta operacion lee un Calendario contenido en un \nFicheroHospital o en un Fichero con un Calendario");
					System.out.print("iNtroduce el id del Hospital");
					int id=sc.nextInt();
					boolean exists=false;
					try{
						 exists = inOut.existsCalendar(id);
					}catch (IOException e){System.out.print("NO SE HA PODIDO leer el archivo\n");}
					
						if(exists){
							try{
							Ho.cargarHospital(id);
							Ho.addCalendar(inOut.getYear(id,null));
							CtrlCalendario cal = new CtrlCalendario(Ho.getCalendar());
							
							cal.readCalendar(id, null);
							
							Calendario cale = cal.getCalendar();
							ArrayList<Turno> t = cale.getALLShifts();
							for(Turno tu: t){
								MostrarTurno(tu);
							}
							
							}catch (IOException e){System.out.print("no year");}
						}
						else{
							System.out.print("Este hospital no tenia calendario");
						}
			break;
				case 2:
					System.out.println("WriteCalendar\n");
					System.out.println("Esta operacion lee un Calendario de un fichero i lo vuelve a guardar");
						System.out.print("iNtroduce el id del Hospital");
						id=sc.nextInt();
						 exists=false;
						try{
							 exists = inOut.existsCalendar(id);
						}catch (IOException e){System.out.print("NO SE HA PODIDO leer el archivo\n");}
						
							if(exists){
								try{
								Ho.cargarHospital(id);
								Ho.addCalendar(inOut.getYear(id,null));
								CtrlCalendario cal = new CtrlCalendario(Ho.getCalendar());
								
								cal.readCalendar(id, null);
								
								Calendario cale = cal.getCalendar();
								ArrayList<Turno> t = cale.getALLShifts();
								for(Turno tu: t){
									MostrarTurno(tu);
								}
								cal.writeCalendar(id);
								
								}catch (IOException e){System.out.print("no year");}
							}
							else{
								System.out.print("Este hospital no tenia calendario");
							}
					
					
				break;
			
			case 3:
				System.out.print("Importar Calendario\n");
				System.out.print("Introduzca el path a buscar\n");
				String path = sc.next();
				
				System.out.print("iNtroduce el id del Hospital");
				id=sc.nextInt();
				CtrlCalendario cal;
						try{
						Ho.cargarHospital(id);
						Ho.addCalendar(-1);
						cal = new CtrlCalendario(Ho.getCalendar());
						cal.importCalendar(id,path);
						}catch (IOException e){System.out.print("no year");}
						
				break;
			
			case 5:{
				break;
			}			
			case 6:{
				break;
			}
			case 7:{
				break;
			}
			case 8:{
				break;
			}
			case 9:{
				break;
			}
			case 10:{

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

