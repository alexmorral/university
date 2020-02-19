
//autor: Oscar Urgelles

package drivers;
import java.text.DateFormat;
import java.util.*;


import model.Calendario;
import model.Doctor;
import model.Hospital;
import model.Turno;

public class DriverHospital {

	static void MostrarTurno(Turno t){
		System.out.printf("--------------------.\n");
		GregorianCalendar c1 = t.getDate();
		String fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(c1.getTime());
		System.out.printf("fecha: %s\n", fecha);
		System.out.printf("\n");
		System.out.printf("tipoturno: %s\n",t.getShiftType());
		System.out.printf("especial: %s\n",t.getSpecialDate());
		System.out.printf("numDoctores: %d\n",t.getNumberOfDoctors());
		System.out.printf("--------------------.\n");

	}
	static void MostrarCal(Calendario cal){
		
		System.out.printf("Calendari del any %d\n",cal.getCalendarYear());
		System.out.printf("Esta format per: %d turns vacacionals\n",cal.getNumberOfShifts());	
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
		if(cal.getNumberOfVacations()==0) System.out.printf("Actualment l'Hospital no te calendari\n");
		else {
			System.out.printf("El calendari te %d Turns Vacacionals\n",cal.getNumberOfShifts());
			ArrayList<Turno> alturno = cal.getALLShifts();
			for(int i=0;i<alturno.size();++i){
				MostrarTurno(alturno.get(i));
			}
		}
		System.out.printf("______________________________\n");
		
	}

	public static void main(String[] args) {
		
		System.out.print("¿Has iniciat el driver d'hospital!!\n");
		System.out.print("\n\n\n");
		
		Scanner teclado;
		teclado = new Scanner(System.in);
		Hospital HOSP = new Hospital();
		int id = -1;
		int opcion = -1;
		while(opcion != 25){
		
			System.out.print("\n¿Que vols fer?\n");
			System.out.print("0: Mostra Opcions\n");
			System.out.print("1-24: Opcions\n");
			System.out.print("25: Fi de l'aplicatiu\n");
			opcion = teclado.nextInt();
			switch(opcion){
			
				case 0:{
					System.out.print("0: Mostra opcions\n");
					System.out.print("1: Hospital()\n");
					System.out.print("2: Hospital (id,nom,fm,ft,fn)\n");
					System.out.print("3: Hospital (id,nom,fm,ft,fn,aldoc,cal)\n");
					System.out.print("4: getId()\n");
					System.out.print("5: getNombre()\n");
					System.out.print("6: getFactorM()\n");
					System.out.print("7: getFactorT()\n");
					System.out.print("8: getFactorN()\n");
					System.out.print("9: getDoctor(id)\n");
					System.out.print("10: getDoctors()\n");
					System.out.print("11: isDocEmpty()\n");
					System.out.print("12: docSize()\n");
					System.out.print("13: existsDoctor(id)\n");
					System.out.print("14: setNombre(nom)\n");
					System.out.print("15: setFactorM(factor)\n");
					System.out.print("16: setFactorT(factor)\n");
					System.out.print("17: setFactorN(factor)\n");
					System.out.print("18: setDoctor(d)\n");
					System.out.print("19: addDoctor(d)\n");
					System.out.print("20: addDoctors(aldoc)\n");
					System.out.print("21: deleteDoctor(id)\n");
					System.out.print("22: cleardoctors()\n");
					System.out.print("23: resetHosp()\n");
					System.out.print("24: getCalendario()\n");
					System.out.print("25: Fi de l'aplicatiu\n");
					System.out.print("\n");
					break;
				}
				case 1:{
					if(id!=1){
						id=1;
						System.out.print("Hospital Creat Correctament\n\n");
						MostrarHospital(HOSP);
					}
					else {
						System.out.print("Ja havies creat un hospital\n");
						System.out.print("Vols eliminar-lo per poder crear un de nou?\n");
						System.out.print("(si o no)\n");
						String resposta;
						resposta=teclado.next();
						if(resposta.equals("si")|resposta.equals("s")|resposta.equals("SI")|resposta.equals("S")) {
							id=-1;
						}
					}
					break;
				}
				case 2: { //
					if(id!=1){
						id=1;
						int codigo;
						String n;
						double fm;
						double ft;
						double fn;
						System.out.print("Ingrese el id del hospital: ");
						codigo = teclado.nextInt();
						System.out.print("Ingrese el nombre del hospital: ");
						n = teclado.next();
						System.out.print("Ingrese el factorManana: ");
						fm = teclado.nextDouble();
						System.out.print("Ingrese el factorTarde: ");
						ft = teclado.nextDouble();
						System.out.print("Ingrese el factorNoche: ");
						fn = teclado.nextDouble();
						HOSP = new Hospital(codigo,n,fm,ft,fn);
						System.out.print("Hospital Creat Correctament \n");
						MostrarHospital(HOSP);
					}
					else {
						System.out.print("Ja havies creat un hospital\n");
						System.out.print("Vols eliminar-lo per poder crear un de nou?\n");
						System.out.print("(si o no)\n");
						String resposta;
						resposta=teclado.next();
						if(resposta.equals("si")|resposta.equals("s")|resposta.equals("SI")|resposta.equals("S")) {
							id=-1;
						}
					}
					break;
				}
				case 3:{ //amb array
					if(id!=1){
					id=1;
					int codigo;
					String n;
					double fm;
					double ft;
					double fn;
					System.out.print("Ingrese el id del hospital: ");
					codigo = teclado.nextInt();
					System.out.print("Ingrese el nombre del hospital: ");
					n = teclado.next();
					System.out.print("Ingrese el factorManana: ");
					fm = teclado.nextDouble();
					System.out.print("Ingrese el factorTarde: ");
					ft = teclado.nextDouble();
					System.out.print("Ingrese el factorNoche: ");
					fn = teclado.nextDouble();
					Doctor doc1= new Doctor(1,"PRIMERO",1,1.0);
					Doctor doc2= new Doctor(2,"SEGUNDO",2,2.0);
					Doctor doc3= new Doctor(3,"TERCERO",3,3.0);	
					Doctor doc4= new Doctor(4,"QUARTO",4,4.0);	
					Doctor doc5= new Doctor(5,"QUINTO",5,5.0);
					ArrayList<Doctor> aldoc = new ArrayList<Doctor>();
					aldoc.add(doc1);
					aldoc.add(doc2);
					aldoc.add(doc3);
					aldoc.add(doc4);
					aldoc.add(doc5);
					System.out.print("S'ha Creat un Arraylist amb 5 doctors per simular l'entrada\n");
					int year, month, day;
					year = 2014;
					month = 2;
					day = 20;
					GregorianCalendar gc1= new GregorianCalendar(year, month-1, day);
					GregorianCalendar gc2= new GregorianCalendar(year, month-1, day+1);
					Calendario cale = new Calendario(year);
					cale.addVacationDay(gc1);
					cale.addVacationDay(gc2);
					System.out.print("S'ha Creat un calendari amb els dies 20/2/2014 y 21/2/2014 per " +
							"simular l'entrada\n");
					HOSP = new Hospital(codigo,n,fm,ft,fn,aldoc,cale);
					System.out.print("Hospital Creat Correctament\n");
					MostrarHospital(HOSP);
				}
				else {
					System.out.print("Ja havies creat un hospital\n");
					System.out.print("Vols eliminar-lo per poder crear un de nou?\n");
					System.out.print("(si o no)\n");
					String resposta;
					resposta=teclado.next();
					if(resposta.equals("si")|resposta.equals("s")|resposta.equals("SI")|resposta.equals("S")) {
						id=-1;
					}
				}
				break;
				}
				
				case 4:{ //getid
					if(id!=-1){
						System.out.printf("ID Hospital: %d\n",HOSP.getId());
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 5:{ //getNombre()
					if(id!=-1){
						System.out.printf("Nombre Hospital: %s\n",HOSP.getNombre());
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 6:{
					if(id!=-1){
						System.out.printf("Factor Mati: %f\n",HOSP.getFactorM());
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 7:{
					if(id!=-1){
						System.out.printf("Factor Tarde: %f\n",HOSP.getFactorT());
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 8:{
					if(id!=-1){
						System.out.printf("Factor Noche: %f\n",HOSP.getFactorN());
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 9:{
					if(id!=-1){
						System.out.print("ID dels doctors de l'Hospital: \n");
						ArrayList<Doctor> aldoc;
						aldoc=HOSP.getDoctors();
						Iterator<Doctor> itr = aldoc.iterator();
						Doctor doc;
						while(itr.hasNext()){
							doc=itr.next();
							System.out.printf("ID: %d\n",doc.getId());
						}
						System.out.print("introdueix la ID del doctor a mostrar \n");
						int d=teclado.nextInt();
						if(HOSP.existsDoctor(d)) {
							doc=HOSP.getDoctor(d);
							MostrarDoc(doc);
						}
						else{
							System.out.print("No existia aquest doctor al hospital\n");
						}
					}
					else System.out.print("Hospital No creat\n");
					break;			
				}
				case 10:{
					if(id!=-1){
						ArrayList<Doctor> aldoc;
						aldoc=HOSP.getDoctors();
						Iterator<Doctor> itr = aldoc.iterator();
						Doctor doc;
						while(itr.hasNext()){
							doc=itr.next();
							MostrarDoc(doc);
						}
						if(HOSP.docSize()==0)System.out.print("No hi ha doctors..\n");
					}
					else System.out.print("Hospital No creat\n");
					break;	
				}
				case 11:{
					if(id!=-1){
						if(HOSP.isDocEmpty()) System.out.print("L'hospital es buit\n");
						else System.out.print("L'hospital conte doctors\n");
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 12:{
					if(id!=-1){
						System.out.printf("L'hospital conte: %d Doctors\n",HOSP.docSize());
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 13:{
					if(id!=-1){
						System.out.print("ID dels doctors de l'Hospital: \n");
						ArrayList<Doctor> aldoc;
						aldoc=HOSP.getDoctors();
						Iterator<Doctor> itr = aldoc.iterator();
						Doctor doc;
						while(itr.hasNext()){
							doc=itr.next();
							System.out.printf("ID: %d\n",doc.getId());
						}
						System.out.print("Itrodueix una id de doctor per comprovar si hi es o no \n");
						int d=teclado.nextInt();
						if(HOSP.existsDoctor(d)) System.out.print("Aquest doctor si que existeix al hospital\n");
						else System.out.print("No existia aquest doctor al hospital\n");
					}
					else System.out.print("Hospital No creat\n");
					break;
				}
				case 14:{
					if(id!=-1){
						System.out.print("Introdueix el nou nom de l'hospital:");
						String nom = teclado.next();
						HOSP.setNombre(nom);
						System.out.print("Nom de l'Hospital cambiat correctament!\n");
						MostrarHospital(HOSP);
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 15:{
					if(id!=-1){
						System.out.print("Introdueix el nou factorMati de l'hospital:");
						Double fact = teclado.nextDouble();
						HOSP.setFactorM(fact);
						System.out.print("Factor Mati cambiat correctament!\n");
						MostrarHospital(HOSP);
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 16:{
					if(id!=-1){
						System.out.print("Introdueix el nou factorTarda de l'hospital:");
						Double fact = teclado.nextDouble();
						HOSP.setFactorT(fact);
						System.out.print("Factor Tarda cambiat correctament!\n");
						MostrarHospital(HOSP);
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 17:{
					if(id!=-1){
						System.out.print("Introdueix el nou factorNit de l'hospital:");
						Double fact = teclado.nextDouble();
						HOSP.setFactorN(fact);
						System.out.print("Factor Nit cambiat correctament!\n");
						MostrarHospital(HOSP);
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 18:{
					if(id!=-1){
						int Id;
						System.out.print("Per aquesta opcio s'ha modificat internament el doctor\n");
						System.out.print("en realitat aquesta operacio rep un Doctor d\n");
						System.out.print("\nID dels doctors de l'Hospital: \n");
						ArrayList<Doctor> aldoc;
						aldoc=HOSP.getDoctors();
						Iterator<Doctor> itr = aldoc.iterator();
						Doctor doc;
						while(itr.hasNext()){
							doc=itr.next();
							System.out.printf("ID: %d\n",doc.getId());
						}
							System.out.print("Introdueix el id del doctor a modificar:");
						Id = teclado.nextInt();
						if(HOSP.existsDoctor(Id)){
							Doctor d = HOSP.getDoctor(Id);
							MostrarDoc(d);
							double sueldo;
							sueldo=d.getSalaryTurn();
							sueldo=sueldo+1000;
							d.setSalaryTurn(sueldo);
							HOSP.setDoctor(d);
							System.out.print("S'ha modificat el doctor d!\n");
							MostrarDoc(HOSP.getDoctor(Id));
						}
						else System.out.print("El doctor no pertany aquest Hospital!\n");
					}
					else System.out.print("Hospital No creat!!\n");
					break;
				}
				case 19:{
					if(id!=-1){
						System.out.print("Introdueix l'id, el nom, el numeroMaxideTorns i el sou per Torn del doctor \n");
						int Id, numMax;
						String nombre;
						Double sueldo;
						Id = teclado.nextInt();
						nombre = teclado.next();
						numMax = teclado.nextInt();
						sueldo = teclado.nextDouble();
						Doctor doc = new Doctor(Id, nombre, numMax, sueldo);
						if(HOSP.existsDoctor(Id)) System.out.print("Et recordo que ja existeix aquest identificador\n");
						else{
							HOSP.addDoctor(doc);
							System.out.print("\n Doctor Afegit!\n");
							MostrarHospital(HOSP);
						}
					}
					else System.out.print("Hospital No creat\n");
					break;
				}
				case 20:{
					if(id!=-1){
						Doctor doc1= new Doctor(1,"PRIMERO",1,1.0);
						Doctor doc2= new Doctor(2,"SEGUNDO",2,2.0);
						Doctor doc3= new Doctor(3,"TERCERO",3,3.0);
						Doctor doc4= new Doctor(4,"QUARTO",4,4.0);
						Doctor doc5= new Doctor(5,"QUINTO",5,5.0);
						ArrayList<Doctor> aldoc = new ArrayList<Doctor>();
						aldoc.add(doc1);
						aldoc.add(doc2);
						aldoc.add(doc3);
						aldoc.add(doc4);
						aldoc.add(doc5);
						System.out.print("doc1=Doctor(1,PRIMERO,1,1.0)\n");
						System.out.print("doc2=Doctor(2,SEGUNDO,2,2.0)\n");
						System.out.print("doc3=Doctor(3,TERCERO,3,3.0)\n");
						System.out.print("doc4=Doctor(4,QUARTO,4,4.0)\n");
						System.out.print("doc5=Doctor(5,QUINTO,5,5.0)\n");
						HOSP.addDoctors(aldoc);
						System.out.print("S'han afegit els 5 doctors al Hospital. +" +
						"Recorda que aquesta funcio sobreescriu els doctors\n");
						MostrarHospital(HOSP);
					}
					else System.out.print("Hospital No creat\n");
					break;
				}
				case 21:{
					if(id!=-1){
						int iden;
						ArrayList<Doctor> aldoc;
						aldoc=HOSP.getDoctors();
						Iterator<Doctor> itr = aldoc.iterator();
						while(itr.hasNext()){
							Doctor d = new Doctor();
							d=itr.next();
							System.out.printf("ID: %d\n",d.getId());
						}
						System.out.print("Ingrese el Id del Doctor a borrar\n");
						
						iden = teclado.nextInt();
						if(HOSP.existsDoctor(iden)== false) System.out.print("Et recordo que no existeix aquest identificador\n");
						else{
							HOSP.deleteDoctor(iden);
							System.out.print("\n Doctor Borrat!\n");
								MostrarHospital(HOSP);
						}
					}
					else System.out.print("Hospital No creat\n");
					break;
				}
				case 22:{
					if(id!=-1){
						if(HOSP.docSize()>0) HOSP.cleardoctors();
						System.out.print("L'hospital ja no te doctors\n");
					}
					else System.out.print("Hospital No creat\n");
					break;
				}
				case 23:{
					if(id!=-1){
						HOSP.resetHosp();
						System.out.print("Les dades del hospital han estat resetejades\n");
						MostrarHospital(HOSP);
					}
					else System.out.print("Hospital No creat\n");
					break;
				}
				case 24:{
					if(id!=-1){
						Calendario cal = HOSP.getCalendario();
						MostrarCal(cal);
					}
					else System.out.print("Hospital No creat\n");
					break;
				}
				
				case 25:{ //fiaplicatiu
					opcion = 25;
				}
				default: break;
			}
		}
		teclado.close();
		System.out.print("PROGRAM EXIT");
	}
}