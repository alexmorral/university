package drivers;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import model.Calendario;
import model.Hospital;
import model.Doctor;
import model.Turno;
import data.CtrlDatosFichero;
import domain.CtrlCalendario;



import data.CtrlDatosFichero;
import domain.CtrlHospital;


public class DriverCtrlHospital {
	private static void muestraOpciones() {
		System.out.print("¿Que desea hacer?\n\n");
		System.out.print("1: MostrarOpciones\n");
		System.out.print("2: verHospitales()\n");
		System.out.print("3: importarHospital\n");
		System.out.print("4: verDoctores()\n");
		System.out.print("5: cargarHospital\n");
		System.out.print("6: addCalendar\n");
		System.out.print("7: getDataDoctors\n");
		System.out.print("8: crearHospital\n");
		System.out.print("9: modificarHospital\n");
		System.out.print("10: eliminarHospital\n");
		System.out.print("11: getDoctors\n");
		System.out.print("12: getCalendar\n");
		System.out.print("13: getFDI\n");
		System.out.print("14: getHospital\n");
		System.out.print("15: getNameHospital\n");
		System.out.print("16: eliminarDoctor\n");
		System.out.print("17: saveDataHosp\n");
		System.out.print("18: saveDataDoctors\n");
		System.out.print("19: saveDataCale\n");
		System.out.print("20: guardarHospital\n");
		System.out.print("0: Salir\n");
	}

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
	static void MostrarDoc(Doctor doc){
		System.out.printf("--------------------.\n");
		System.out.printf("ID: %d\n",doc.getId());
		System.out.printf("Nom: %s\n",doc.getName());
		System.out.printf("Num Max turnos: %d\n",doc.getNumMaxTurn());
		System.out.printf("Sueldo Por turno: %s €\n",doc.getSalaryTurn());
		if(doc.isREmpty()) System.out.printf("No te restriccions: \n");
		else System.out.printf("Te restriccions\n");
		System.out.printf("--------------------.\n");
	}
	static void MostrarHospital(Hospital hosp){
		ArrayList<Doctor> aldoc;
		
		System.out.printf("ID Hospital: %d\n",hosp.getId());
		System.out.printf("Nombre: %s\n",hosp.getNombre());
		System.out.printf("Factor Mati: %f\n",hosp.getFactorM());
		System.out.printf("Factor Tarda: %f\n",hosp.getFactorT());
		System.out.printf("Factor Nit: %f\n",hosp.getFactorN());
		if(hosp.isDocEmpty()) {
			System.out.printf("No hi ha doctors\n");
		}
		else {
			System.out.printf("LLista de Doctors:\n");
			System.out.printf("Hi ha %d Doctors\n",hosp.docSize());
			aldoc=hosp.getDoctors();
			Iterator<Doctor> itr = aldoc.iterator();
			int i = 1;
			while(itr.hasNext()){
				Doctor d = new Doctor();
				d=itr.next();
				System.out.printf("%d.\n",i);
				MostrarDoc(d);
				++i;
			}
		}
		Calendario cal = hosp.getCalendario();
		if(!hosp.existsCalendar()) System.out.printf("Actualment l'Hospital no te calendari\n");
		else {
			System.out.printf("El calendari te %d Turns Vacacionals\n",cal.getNumberOfShifts());
			ArrayList<Turno> alturno = cal.getALLShifts();
			for(int i=0;i<alturno.size();++i){
				MostrarTurno(alturno.get(i));
			}
		}
		System.out.printf("______________________________\n");
		
	}
	
	public static void main(String[] args) throws IOException{
		Scanner teclado;
		teclado = new Scanner(System.in);
		int opcion = -1;
	    muestraOpciones();
	    opcion = teclado.nextInt();
	    CtrlHospital domain = new CtrlHospital();
	    CtrlDatosFichero inOut = new CtrlDatosFichero();
	    
	    
		while(opcion != 0) {
	        
			switch(opcion){
			/*
				case 31:
					System.out.println("Ingrese el id del hospital: ");
					id = teclado.nextInt();
					System.out.println("Ingrese el nombre del hospital: ");
					nombre = br.readLine();
					System.out.println("Ingrese el factorMañana: ");
					fm = teclado.nextDouble();
					System.out.println("Ingrese el factorTarde: ");
					ft = teclado.nextDouble();
					System.out.println("Ingrese el factorNoche: ");
					fn = teclado.nextDouble();
					try{
						domain.crearHospital(id, nombre, fm, ft, fn);
					} catch(IOException e) { throw new IOException(e); }
					System.out.println("Hospital creado correctamente");	
					break;
					
				case 32:
					System.out.println("Ingrese el nuevo nombre del hospital: ");
					nombre = br.readLine();
					System.out.println("Ingrese el nuevo factorMañana: ");
					fm = teclado.nextDouble();
					System.out.println("Ingrese el nuevo factorTarde: ");
					ft = teclado.nextDouble();
					System.out.println("Ingrese el nuevo factorNoche: ");
					fn = teclado.nextDouble();
					try {
						domain.modificarHospital(nombre, fm, ft, fn);
					} catch(IOException e) { throw new IOException(e); }
					System.out.println("Hospital modificado correctamente");
					break;
				
				case 33:
					int numMax;
					Double sueldo;
					System.out.println("Ingrese el identificador del Doctor: ");
					id = teclado.nextInt();
					System.out.println("Ingrese el nombre del Doctor: ");
					nombre = br.readLine();
					System.out.println("Ingrese el número máximo de turnos del Doctor: ");
					numMax = teclado.nextInt();	
					System.out.println("Ingrese el sueldo del Doctor: ");
					sueldo = teclado.nextDouble();
					try {
						domain.crearDoctor(id, nombre, numMax, sueldo);
						
					}
					catch(IOException e) { throw new IOException(e); }
					System.out.println("Doctor añadido correctamente");
					break;
				
				case 34:

					System.out.println("Ingrese el identificador del Hospital: ");
					int Id = teclado.nextInt();
					try{
						
						domain.cargarHospital(Id);
					}
					catch(IOException e) { throw new IOException(e); }
					System.out.println("Hospital correctamente cargado");
					break;
				
				case 35: 
						domain.guardarHospital();
					System.out.println("Hospital correctamente guardado");
					break;
					
				case 36: //Res Max Turnos Rango
					ArrayList<Doctor> aldoctor = new ArrayList<Doctor>();
					try{
					aldoctor = domain.getDoctors();
					}catch(IOException e) { throw new IOException(e); }
					System.out.println("Doctores Obtenidos:");
					for(int i=0;i<aldoctor.size();++i){
						System.out.printf("%d : doctor:%s\n",i,aldoctor.get(i).getName());
					}
					System.out.print("Ingrese el numero del doctor:\n");
					int x = teclado.nextInt();
					Doctor	m = aldoctor.get(x);
					Restriccion res;
					System.out.println("Ingrese el id Restriccion:");
					int idRestriccion = teclado.nextInt();
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
					m.addRestriction(res);
					break;
				case 37:
					Hospital Hosp =domain.getHospital();
					MostrarHospital(Hosp);
					break;
				
				
					*/
			case 1: 
				muestraOpciones();
				break;
			
				case 2:
					System.out.print("verHospitales\n");
					
					ArrayList<String> verHospitales = domain.verHospitales();
					for(int i=0;i<verHospitales.size();++i){
						System.out.println(verHospitales.get(i));
					}
					break;
				case 3:
					System.out.print("importarHospital\n");
					System.out.print("introduzca el path donde esta el Hospital a importar\n");
					String path =teclado.next();
					domain.importarHospital(path);
					System.out.print("Se ha importado el Hospital\n");
					break;
					
				case 4:
					System.out.print("VerDoctores\n");
					ArrayList<ArrayList<String>> verDoctores = domain.verDoctores();
						for(ArrayList<String> s : verDoctores){
							for(String s1 : s){
								System.out.print(s1+" ");
							}
							System.out.println();
						}
						System.out.println();
						
					break;
					
				case 5:
					System.out.print("CargarHospital\n");
					System.out.print("Introduzca el id Hospital a cargar\n");
					int id=teclado.nextInt();
					domain.cargarHospital(id);
					Hospital hosp=domain.getHospital();
					MostrarHospital(hosp);
					
				break;
				
				case 6:
					System.out.print("AddCalendar\n");
					System.out.print("iNtroduzca el anyo del calendario\n");
					int year = teclado.nextInt();
					domain.addCalendar(year);
					hosp =domain.getHospital();
					Calendario c = hosp.getCalendario();
					
					System.out.printf("el anyo del calendario es %d \n",c.getCalendarYear());
				break;
					
				case 7:
					System.out.print("GetDataDoctors\n");
					System.out.print("Introduzca el id Hospital para cargar sus doctores\n");
					id=teclado.nextInt();
					domain.getDataDoctors(id);
					hosp=domain.getHospital();
					MostrarHospital(hosp);
				break;
					
				case 8:
					System.out.print("CrearHospital\n");
					double fm,ft,fn;
					System.out.print("Introduzca id:");
					id=teclado.nextInt();
					System.out.print("Introduzca NOmbre: \n");
					String Name = teclado.next();
					System.out.print("Introduzca factor Manana:\n");
					fm=teclado.nextDouble();
					System.out.print("Introduzca factor Tarde: \n");
					ft=teclado.nextDouble();
					System.out.print("Introduzca factor Noche: \n");
					fn=teclado.nextDouble();
					domain.crearHospital(id, Name, fm, ft, fn);
					hosp=domain.getHospital();
					MostrarHospital(hosp);
					
					break;
				case 9:
					System.out.print("ModificarHospital\n");
					System.out.print("Introduzca NOmbre: \n");
					Name = teclado.next();
					System.out.print("Introduzca factor Manana:\n");
					fm=teclado.nextDouble();
					System.out.print("Introduzca factor Tarde: \n");
					ft=teclado.nextDouble();
					System.out.print("Introduzca factor Noche: \n");
					fn=teclado.nextDouble();
					domain.modificarHospital(Name, fm, ft, fn);
					hosp=domain.getHospital();
					MostrarHospital(hosp);
					break;
					
				case 10:
					System.out.print("EliminarHospital\n");
					System.out.print("Introduzca id:");
					id=teclado.nextInt();
					domain.deleteHospital(id);
					if(!inOut.existHospId(id)) System.out.print("Ya no existe este hospital en datos\n");
					else{
						domain.cargarHospital(id);
					
					hosp=domain.getHospital();
					MostrarHospital(hosp);
					}
					break;
					
				case 11:
					System.out.print("GetDoctors\n");
					ArrayList<Doctor> aldoctor = domain.getDoctors();
					for(Doctor doc:aldoctor){
						MostrarDoc(doc);
					}
					break;
					
				case 12:
					System.out.print("GetCalendar\n");
					System.out.print("se introduce un Turno para este ejemplo\n");
					domain.addCalendar(2014);
					CtrlCalendario calen= new CtrlCalendario(domain.getCalendar());
					calen.addVacationDayDeprecated(25, 12, 2014, 1, 1, 1, "navidad", "navidad","navidad");
					Calendario cal = domain.getCalendar();
					ArrayList<Turno> turns=cal.getALLShifts();
						for(Turno t: turns){
							MostrarTurno(t);
						}
					
					break;
				case 13:
					int di=domain.getFDI();
					System.out.printf("el primer id disponible es: %d\n",di);
					break;
				
				case 14:
					System.out.print("GetHospital\n");
					hosp=domain.getHospital();
					MostrarHospital(hosp);
				break;
				
				case 15:
					System.out.print("GetNameHospital\n");
					String name = domain.getNameHospital();
					System.out.print(name);
					System.out.println();
					break;
					
				case 16:
					System.out.print("EliminarDoctor\n");
					System.out.print("Introduzca id del doctor a eliminar:");
					id=teclado.nextInt();
					domain.eliminarDoctor(id);
					break;
				
				case 17:
					System.out.print("SaveDataHosp\n");
					domain.saveDataHosp();
				break;
				
				case 18:
					System.out.print("SaveDataDoctors\n");
					domain.saveDataDoctors();
					System.out.print("se han guardado los datos\n");
				break;
				case 19:
					System.out.print("SaveDataCale\n");
					domain.saveDataCale();
					System.out.print("se han guardado los datos\n");
				break;
				case 20:
					System.out.print("GuardarHospital\n");
					domain.guardarHospital();
					System.out.print("se han guardado los datos\n");
				break;
					
					/*
					
		
		System.out.print("17: saveDataHosp\n");
		System.out.print("18: saveDataDoctors\n");
		System.out.print("19: saveDataCale\n");
		
					*/
				default:
			}
			
			System.out.print("------------------\n");
			System.out.print("1: MostrarOpciones\n");
			System.out.print("------------------\n");
		    opcion = teclado.nextInt();
		}
		teclado.close();
		System.out.print("PROGRAM EXIT");
	}
}
