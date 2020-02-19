package drivers;
import model.*;
import domain.*;
import data.CtrlDatosFichero;

import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;


public class DriverCtrlDoctor {

	private static void mostrarOpciones() {
		System.out.print("-----------------------------------------------\n");
		System.out.print("�Que desea hacer?\n");
		System.out.print("0: Salir\n");
		System.out.print("1: Consultar Doctor\n");
		System.out.print("2: Modificar Doctor\n");
		System.out.print("3: Anadir restriccion tipo MAX_Turnos_por_Dia\n");
		System.out.print("4: Anadir restriccion tipo MAX_Turnos_Rango\n");
		System.out.print("5: Anadir restriccion tipo NOT_Dia_Mes\n");
		System.out.print("6: Anadir restriccion tipo NOT_Dia_Semana\n");
		System.out.print("7: Anadir restriccion tipo NOT_Especial\n");
		System.out.print("8: Anadir restriccion tipo NOT_Fecha\n");
		System.out.print("9: Anadir restriccion tipo NOT_Turno\n");
		System.out.print("10: Anadir restriccion tipo XOR\n");
		System.out.print("11: Eliminar restriccion\n");
		System.out.print("12: Consultar restricciones Doctor\n");
		System.out.print("13: SaveDataRes\n");
		System.out.print("14: getFDIdocs\n");
		System.out.print("15: getFDIRes\n");
		System.out.print("16 addResData\n");
		System.out.print("-----------------------------------------------\n\n");
	}
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, ParseException {
		Scanner teclado;
		teclado = new Scanner(System.in);
		
		int opcion, idDoc, idRes;
		ArrayList<Doctor> docs = new ArrayList<Doctor>();
		
		Doctor doc1 = new Doctor(0, "Juan", 10, 50);
		Doctor doc2 = new Doctor(1, "Julio", 12, 60);
		Doctor doc3 = new Doctor(2, "Sergio", 15, 50);
		Doctor doc4 = new Doctor(3, "David", 5, 70);
		Doctor doc5 = new Doctor(4, "Jorge", 20, 40);
		docs.add(doc1);
		docs.add(doc2);
		docs.add(doc3);
		docs.add(doc4);
		docs.add(doc5);
		
		
		CtrlDoctor ctrlDoc = new CtrlDoctor(docs, 2014);
		CtrlDatosFichero inOut1 = new CtrlDatosFichero();
		CtrlHospital Hosp = new CtrlHospital();
		Hosp.cargarHospital(0);
		Hosp.getDataDoctors(0);
		int year=inOut1.getYear(0,null);
		Hosp.addCalendar(year);
		System.out.print("HospitalCargat\n");
		CtrlCalendario cal= new CtrlCalendario(Hosp.getCalendar());
		System.out.print("cargu el calendari\n");
		cal.readCalendar(0,null);
		System.out.printf("cargu el calendar de data %d\n",cal.getCalendarYear());
		CtrlDoctor s = new CtrlDoctor(Hosp.getDoctors(),cal.getCalendarYear());
		if(inOut1.existsRes(0))s.addResData(0,null);
		
		
		mostrarOpciones();
		opcion = teclado.nextInt();
		
		while(opcion != 0){	
			if (opcion == 1) {
				System.out.print("Ingrese ID del doctor a consultar\n");
				idDoc = teclado.nextInt();
				try {
					System.out.println("Nombre Doctor: "+ctrlDoc.getNombreDoctor(idDoc)+"\n"
					+"NumMaxTurnos: "+ctrlDoc.getNumMaxTurnosDoctor(idDoc)+ "\nSueldo por Turno: "+
					ctrlDoc.getSueldoTurnosDoctor(idDoc)+"\n");
				} catch(IOException e) { System.out.println(e); }
			}
			else if (opcion == 2) {
				String nom;
				int numMax;
				double sueldo;
				System.out.print("Ingrese ID del doctor a consultar\n");
				idDoc = teclado.nextInt();
				System.out.print("Ingrese el nuevo nombre del doctor\n");
				nom = teclado.next();
				System.out.print("Ingrese el nuevo numero maximo de turnos del doctor\n");
				numMax = teclado.nextInt();
				System.out.print("Ingrese el nuevo sueldo por turno del doctor\n");
				sueldo = teclado.nextDouble();
				try {
					s.setDoctor(idDoc, nom, numMax, sueldo);
					//ctrlDoc.setDoctor(idDoc, nom, numMax, sueldo);
					Hosp.saveDataDoctors();
					System.out.println("Doctor modificado correctamente\n");
				} catch(IOException e) { System.out.println(e); }
			}
			else if (opcion == 3) {
				int turnos;
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Ingrese el ID de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				System.out.print("Ingrese el numero maximo de turnos por dia\n");
				turnos = teclado.nextInt();
				try {
					s.addResMAX_Turnos_por_Dia(idDoc, idRes, turnos);
					//ctrlDoc.addResMAX_Turnos_por_Dia(idDoc, idRes, turnos);
					System.out.println("Restriccion anadida correctamente\n");
					s.saveDataRes(0);
				} catch (IOException e) { System.out.println(e); }
			}
			else if (opcion == 4) {
				int d1, m1, a1, d2, m2, a2, turnos;
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Ingrese el ID de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				System.out.print("Ingrese la fecha inicial (dd MM yyyy)\n");
				d1 = teclado.nextInt();
				m1 = teclado.nextInt();
				a1 = teclado.nextInt();
				System.out.print("Ingrese la fecha final (dd MM yyyy)\n");
				d2 = teclado.nextInt();
				m2 = teclado.nextInt();
				a2 = teclado.nextInt();
				System.out.print("Igrese el numero maximo de turnos en el rango\n");
				turnos = teclado.nextInt();
				try {
					s.addResMAX_Turnos_Rango(idDoc, idRes, d1, m1, a1, d2, m2, a2, turnos);
					//ctrlDoc.addResMAX_Turnos_Rango(idDoc, idRes, d1, m1, a1, d2, m2, a2, turnos);
					s.saveDataRes(0);
					System.out.println("Restriccion anadida correctamente\n");
				} catch (IOException e) { System.out.println(e); }
			}
			else if (opcion == 5) {
				int dia;
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Ingrese el ID de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				System.out.print("Ingrese el dia del mes\n");
				dia = teclado.nextInt();
				try {
					s.addResNOT_Dia_Mes(idDoc, idRes, dia);
					//ctrlDoc.addResNOT_Dia_Mes(idDoc, idRes, dia);
					s.saveDataRes(0);
					System.out.println("Restriccion anadida correctamente\n");
				} catch (IOException e) { System.out.println(e); }			
			}
			else if (opcion == 6) {
				String dia;
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Ingrese el ID de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				System.out.print("Ingrese el dia de la semana (lunes, martes, miercoles, jueves, viernes, sabado o domingo)\n");
				dia = teclado.next();
				try {
					s.addResNOT_Dia_Semana(idDoc, idRes, dia);
					//ctrlDoc.addResNOT_Dia_Semana(idDoc, idRes, dia);
					s.saveDataRes(0);
					System.out.println("Restriccion anadida correctamente\n");
				} catch (IOException e) { System.out.println(e); }
			}
			else if (opcion == 7) {
				String especial;
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Ingrese el ID de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				System.out.print("Ingrese el dia especial (navidad, semana_santa, noche_vieja o noche_buena)\n");
				especial = teclado.next();
				try {
					s.addResNOT_Especial(idDoc, idRes, especial);
					//ctrlDoc.addResNOT_Especial(idDoc, idRes, especial);
					s.saveDataRes(0);
					System.out.println("Restriccion anadida correctamente\n");
				} catch (IOException e) { System.out.println(e); }
			}
			else if (opcion == 8) {
				int d1, m1, a1;
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Ingrese el ID de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				System.out.print("Ingrese la fecha (dd MM yyyy)\n");
				d1 = teclado.nextInt();
				m1 = teclado.nextInt();
				a1 = teclado.nextInt();
				try {
					s.addResNOT_Fecha(idDoc, idRes, d1, m1, a1);
					//ctrlDoc.addResNOT_Fecha(idDoc, idRes, d1, m1, a1);
					s.saveDataRes(0);
					System.out.println("Restriccion anadida correctamente\n");
				} catch (IOException e) { System.out.println(e); }
			}
			else if (opcion == 9) {
				String turno;
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Ingrese el ID de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				System.out.print("Ingrese el turno (manana, tarde o noche)\n");
				turno = teclado.next();
				try {
					s.addResNOT_Turno(idDoc, idRes, turno);
					//ctrlDoc.addResNOT_Turno(idDoc, idRes, turno);
					s.saveDataRes(0);
					System.out.println("Restriccion anadida correctamente");
				} catch (IOException e) { System.out.println(e); }
			}			
			else if (opcion == 10) {
				int d1, m1, a1;
				String turno;
				ArrayList<Integer> dia = new ArrayList<Integer>();
				ArrayList<Integer> mes = new ArrayList<Integer>();
				ArrayList<Integer> any = new ArrayList<Integer>();
				ArrayList<String> turnos = new ArrayList<String>();
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				//System.out.print("Introduce el id de la restriccion\n");
				//idRes = teclado.nextInt();
				idRes=s.getFDIRes(idDoc);
				String opt = "S";
				while(opt.equals("s") || opt.equals("S")) {
					System.out.print("Ingrese la fecha (dd MM yyyy)\n");
					d1 = teclado.nextInt();
					dia.add(d1);
					m1 = teclado.nextInt();
					mes.add(m1);
					a1 = teclado.nextInt();
					any.add(a1);
					System.out.print("Ingrese el turno (manana, tarde o noche)\n");
					turno = teclado.next();
					turnos.add(turno);
					System.out.print("Quieres anadir otro turno? (S/s N/n)\n");
					opt = teclado.next();
				}
				try {
					s.addResXOR(idDoc, idRes, dia, mes, any, turnos);
					//ctrlDoc.addResXOR(idDoc, idRes, dia, mes, any, turnos);
					s.saveDataRes(0);
					System.out.println("Restriccion anadida correctamente\n");
				} catch (IOException e) { System.out.println(e); }
			}
			else if (opcion == 11) {
				System.out.print("Ingrese el ID del doctor\n");
				idDoc = teclado.nextInt();
				System.out.print("Ingrese el ID de la restriccion\n");
				idRes = teclado.nextInt();
				try {
					s.eliminarRestriccion(idRes, idDoc);
					//ctrlDoc.eliminarRestriccion(idRes, idDoc);
					s.saveDataRes(0);
					System.out.println("Restriccion eliminada correctamente\n");
				} catch (IOException e) { System.out.println(e); }
			}
			else if (opcion == 12) {
				System.out.print("Ingrese el ID del doctor del cual quieres consultar sus restricciones\n");
				idDoc = teclado.nextInt();
				try {
					if (idDoc < 0 || idDoc > 4) System.out.println("Esta ID de doctor no existe\n");
					else {
						Doctor doc = docs.get(idDoc);
						if (doc.isREmpty())  System.out.println("Este doctor no contiene restricciones\n");
						else {
							ArrayList<Restriccion> alres = doc.getRestrictions();
							for (int i = 0; i < alres.size(); ++i) {
								int idR = alres.get(i).getIdRestriccion();
								if (alres.get(i).getTipo().equals("MAX_Turnos_por_Dia")) {
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									System.out.println("Maximo numero de turnos por dia: "+ctrlDoc.getMAX_Turnos_Por_Dia(idDoc,idR)+"\n");
								}
								else if (alres.get(i).getTipo().equals("MAX_Turnos_Rango")) {
									GregorianCalendar fecha1 = ctrlDoc.getMAX_Turnos_Rango_FeIni(idDoc, idR);
									GregorianCalendar fecha2 = ctrlDoc.getMAX_Turnos_Rango_FeFin(idDoc, idR);
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									System.out.println("Fecha inicio: "+fecha1.get(fecha1.DAY_OF_MONTH)+"/"+(fecha1.get(fecha1.MONTH)+1)+"/"+fecha1.get(fecha1.YEAR));
									System.out.println("Fecha fin: "+fecha2.get(fecha2.DAY_OF_MONTH)+"/"+(fecha2.get(fecha2.MONTH)+1)+"/"+fecha2.get(fecha2.YEAR));
									System.out.println("Numero maximo de turnos: "+ctrlDoc.getMAX_Turnos_Rango_numT(idDoc, idR)+"\n");
								}
								else if (alres.get(i).getTipo().equals("NOT_Dia_Mes")) {
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									System.out.println("Dia del mes: "+ctrlDoc.getNOT_Dia_Mes(idDoc,idR)+"\n");
								}
								else if (alres.get(i).getTipo().equals("Not_Dia_Semana")) {
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									System.out.println("Dia de la semana: "+ctrlDoc.getNOT_Dia_Semana(idDoc,idR)+"\n");
								}
								else if (alres.get(i).getTipo().equals("NOT_Especial")) {
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									System.out.println("Dia Especial: "+ctrlDoc.getNOT_Especial(idDoc,idR)+"\n");
								}
								else if (alres.get(i).getTipo().equals("NOT_Fecha")) {
									GregorianCalendar fecha = ctrlDoc.getNOT_Fecha(idDoc, idR);
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									System.out.println("Fecha: "+fecha.get(fecha.DAY_OF_MONTH)+"/"+(fecha.get(fecha.MONTH)+1)+"/"+fecha.get(fecha.YEAR)+"\n");
								}
								else if (alres.get(i).getTipo().equals("NOT_Turno")) {
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									System.out.println("Tipo turno: "+ctrlDoc.getNOT_Turno(idDoc,idR)+"\n");
								}
								else if (alres.get(i).getTipo().equals("XOR")) {
									ArrayList<GregorianCalendar> dates = ctrlDoc.getXOR_Dates(idDoc, idR);
									ArrayList<String> turnos = ctrlDoc.getXOR_TipoTurno(idDoc, idR);
									System.out.println("Restriccion tipo: "+ctrlDoc.getTipoRestriccion(idDoc, idR));
									for (int j = 0; j < dates.size(); ++j) {
										int d = dates.get(j).get(dates.get(j).DAY_OF_MONTH);
										int m = (dates.get(j).get(dates.get(j).MONTH)+1);
										int a = dates.get(j).get(dates.get(j).YEAR);
										System.out.println("-------------");
										System.out.println("Fecha: "+d+"/"+m+"/"+a);
										System.out.println("Tipo turno: "+turnos.get(j));
									}
									System.out.println("\n");
								}
							}
						}
					}
					
				} catch (IOException e) { System.out.println(e); }
			}
			else if(opcion == 13){
				System.out.print("ingrese id hosp:\n");
				int id = teclado.nextInt();
				Integer num=id;
				CtrlDatosFichero inOut = new CtrlDatosFichero();
				CtrlHospital Ho = new CtrlHospital();
				Ho.cargarHospital(num);
				Ho.getDataDoctors(id);
				year=inOut.getYear(id,null);
				Ho.addCalendar(year);
				System.out.print("HospitalCargat\n");
				CtrlCalendario cal1= new CtrlCalendario(Ho.getCalendar());
				System.out.print("cargu el calendari\n");
				cal1.readCalendar(id,null);
				System.out.printf("cargu el calendar de data %d\n",cal.getCalendarYear());
				CtrlDoctor s1 = new CtrlDoctor(Ho.getDoctors(),cal1.getCalendarYear());
				s1.addResData(id,null);
				System.out.print("OOuyeah\n");
				s1.saveDataRes(id);
				System.out.print("Fet\n");
			}
			else if(opcion==14){
				System.out.print("ingrese id hosp:\n");
				int id = teclado.nextInt();
				Integer num=id;
				CtrlDatosFichero inOut = new CtrlDatosFichero();
				CtrlHospital Ho = new CtrlHospital();
				Ho.cargarHospital(num);
				Ho.getDataDoctors(id);
				year=inOut.getYear(id,null);
				Ho.addCalendar(year);
				System.out.print("HospitalCargat\n");
				CtrlCalendario cal2= new CtrlCalendario(Ho.getCalendar());
				System.out.print("cargu el calendari\n");
				cal2.readCalendar(id,null);
				System.out.printf("cargu el calendar de data %d\n",cal2.getCalendarYear());
				CtrlDoctor s2 = new CtrlDoctor(Ho.getDoctors(),cal.getCalendarYear());
				s2.addResData(id,null);
				System.out.printf("El primer doctor lliure es: %d\n",s.getFDIdocs());
			}
			else if(opcion==15){
				System.out.print("ingrese id hosp:\n");
				int id = teclado.nextInt();
				Integer num=id;
				CtrlDatosFichero inOut = new CtrlDatosFichero();
				CtrlHospital Ho = new CtrlHospital();
				Ho.cargarHospital(num);
				Ho.getDataDoctors(id);
				year=inOut.getYear(id,null);
				Ho.addCalendar(year);
				System.out.print("HospitalCargat\n");
				CtrlCalendario cal3= new CtrlCalendario(Ho.getCalendar());
				System.out.print("cargu el calendari\n");
				cal3.readCalendar(id,null);
				System.out.printf("cargu el calendar de data %d\n",cal3.getCalendarYear());
				CtrlDoctor s3 = new CtrlDoctor(Ho.getDoctors(),cal.getCalendarYear());
				s3.addResData(id,null);
				System.out.print("ingrese el id del doctor:\n");
				int ne=teclado.nextInt();
				System.out.printf("la primera res lliure es: %d\n",s.getFDIRes(ne));
				
			}
			else if(opcion == 16){
				System.out.print("ingrese id hosp:\n");
				int id = teclado.nextInt();
				Integer num=id;
				CtrlDatosFichero inOut = new CtrlDatosFichero();
				CtrlHospital Ho = new CtrlHospital();
				Ho.cargarHospital(num);
				Ho.getDataDoctors(id);
				year=inOut.getYear(id,null);
				Ho.addCalendar(year);
				System.out.print("HospitalCargat\n");
				CtrlCalendario cal1= new CtrlCalendario(Ho.getCalendar());
				System.out.print("cargu el calendari\n");
				cal1.readCalendar(id,null);
				System.out.printf("cargu el calendar de data %d\n",cal.getCalendarYear());
				CtrlDoctor s1 = new CtrlDoctor(Ho.getDoctors(),cal1.getCalendarYear());
				s1.addResData(id,null);
				System.out.print("OOuyeah\n");
				s1.saveDataRes(id);
				System.out.print("Fet\n");
				
			}
			mostrarOpciones();
			opcion = teclado.nextInt();
		}
		teclado.close();
		System.out.print("PROGRAM EXIT");
	}

}
