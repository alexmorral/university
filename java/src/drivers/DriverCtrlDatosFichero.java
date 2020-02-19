package drivers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import data.CtrlDatosFichero;

public class DriverCtrlDatosFichero {

	private static void muestraOpciones() {
		System.out.print("¿Que desea hacer?\n\n");
		System.out.print("1: getHospitals\n");
		System.out.print("2: getIdHopitals\n");
		System.out.print("3: getDataHospital\n");
		System.out.print("4: getDataDoctors\n");
		System.out.print("5: getDataCale\n");
		System.out.print("6: getDataRes\n");
		System.out.print("7: existHospId\n");
		System.out.print("8: existsCale\n");
		System.out.print("9: existsDoctor\n");
		System.out.print("10: existsRes\n");
		System.out.print("11: saveDataHosp\n");
		System.out.print("12: saveDataDoctors\n");
		System.out.print("13: saveDataCalendar\n");
		System.out.print("14: saveDataRes\n");
		System.out.print("15: removepart\n");
		System.out.print("16: getYear\n");
		System.out.print("17: howManySolutions\n");
		System.out.print("18: existsSol\n");
		System.out.print("19: getsol\n");
		System.out.print("20: getnosol\n");
		System.out.print("0: Salir\n");
	}

	public static void main(String[] args) throws IOException{

		Scanner teclado;
		teclado = new Scanner(System.in);
	    
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		int opcion = -1;
		System.out.print("Driver CtrlDatosFichero\n");
		 ArrayList<String> alhosp = new ArrayList<String>();
		 Integer num = null;
		 int id;
		muestraOpciones();
		opcion = teclado.nextInt();
		while(opcion != 0){
			
			switch(opcion){
			
			
			case 1:	//ok
			    ArrayList<String> ids1 = new ArrayList<String>();
				try{
					ids1=inOut.getHopitals();
					}catch(IOException e) {
						System.out.println(e);
					}
					for(int i = 0; i<ids1.size();++i){
						System.out.println(ids1.get(i));
					}
				break;
			case 2: //ok	
				ArrayList<Integer> ids = new ArrayList<Integer>();
				ids=inOut.getIdHopitals();
				
				for(int i = 0; i<ids.size();++i){
					System.out.println(ids.get(i));
				}
				break;
			case 3: //ok
				System.out.print("Ingrese el id del Hospital para cargarlo en el programa\n");
				num=teclado.nextInt();
					if(inOut.existHospId(num)){
						alhosp.clear();
						alhosp=inOut.getDataHospital(num,null);
						if(!alhosp.isEmpty()){
								System.out.print("Hospital Cargado Correctamente\n");
							for(int i =0; i<alhosp.size()-1;++i){
								System.out.print(alhosp.get(i)+" ");
							}
							System.out.print(alhosp.get(alhosp.size()-1));
							System.out.print("\n");
						}
						else{
							System.out.print("Error!\n");
						}
					}
					else System.out.print("No Existe este hospital\n");
				break;
			case 4: //ok
				System.out.print("Ingrese el id del hospital para obtener sus doctores\n");
				num=teclado.nextInt();
					if(inOut.existHospId(num)){
						alhosp.clear();
						if(inOut.existsDoctors(num))	alhosp=inOut.getDataDoctors(num,null);
						if(!alhosp.isEmpty()){
							System.out.print("Doctores Correctamente cargados\n");
							for(int i =0; i<alhosp.size()-1;++i){
								System.out.print(alhosp.get(i)+" ");
							}
							System.out.print(alhosp.get(alhosp.size()-1));
							System.out.print("\n");
						}
						else {
							System.out.print("Error este Hospital no tiene Doctores!\n");
						}
					}
					else System.out.print("No Existe este hospital\n");
				break;
			case 5: //ok
				System.out.print("Ingrese el id del hospital para obtener el Calendario\n");
				num=teclado.nextInt();
					if(inOut.existHospId(num)){
						alhosp.clear();
						if(inOut.existsCalendar(num))alhosp=inOut.getDataCale(num,null);
						if(!alhosp.isEmpty()){
							System.out.print("Calendario Correctamente Cargado\n");
							for(int i =0; i<alhosp.size()-1;++i){
								System.out.print(alhosp.get(i)+" ");
							}
							System.out.print(alhosp.get(alhosp.size()-1));
							System.out.print("\n");
						}
						else {
							System.out.print("ERROR este Hospital no tiene Calendario!\n");
						}
					}
					else System.out.print("No Existe este hospital\n");
				break;
			case 6: //ok
				System.out.print("Ingrese el id del hospital para obtener las Restricciones\n");
				num=teclado.nextInt();
					if(inOut.existHospId(num)){
						alhosp.clear();
						if(inOut.existsRes(num))alhosp=inOut.getDataRes(num,null);
						if(!alhosp.isEmpty()){
							System.out.print("Restricciones Correctamente Cargadas\n");
							for(int i =0; i<alhosp.size()-1;++i){
								System.out.print(alhosp.get(i)+" ");
							}
							System.out.print(alhosp.get(alhosp.size()-1));
							System.out.print("\n");
						}
						else {
							System.out.print("ERROR este Hospital no tiene Restricciones!\n");
						}
					}
					else System.out.print("No Existe este hospital\n");
				break;
			case 7: //ok
				System.out.print("Ingrese el id del Hospital para ver si existe:\n");
				id=teclado.nextInt();
				if(inOut.existHospId(id)) System.out.print("Si que existe\n");
				else System.out.print("No existe\n");
				break;					
			case 8://ok
					
				System.out.print("Ingrese el id del hospital para ver si tiene Calendario en Data\n");
				num=teclado.nextInt();
					if(inOut.existHospId(num)){
						alhosp.clear();
						if(inOut.existsCalendar(num)){
							System.out.print("Si que tiene\n");
						}
						else {
							System.out.print("NO TIENE\n");
						}
					}
					else System.out.print("No Existe este hospital\n");
				break;
			case 9://ok
				
				System.out.print("Ingrese el id del hospital para ver si tiene Doctores\n");
				num=teclado.nextInt();
					if(inOut.existHospId(num)){
						alhosp.clear();
						if(inOut.existsDoctors(num)){
							System.out.print("Si que tiene\n");
						}
						else {
							System.out.print("NO TIENE\n");
						}
					}
					else System.out.print("No Existe este hospital\n");
				break;
			case 10: //ok
				
				System.out.print("Ingrese el id del hospital para ver si tiene Restricciones\n");
				num=teclado.nextInt();
					if(inOut.existHospId(num)){
						alhosp.clear();
						if(inOut.existsRes(num)){
							System.out.print("Si que tiene\n");
						}
						else {
							System.out.print("NO TIENE\n");
						}
					}
					else System.out.print("No Existe este hospital\n");
				break;
			case 11: //ok
					System.out.print("Igrese id Hosp\n");
					num=teclado.nextInt();
					alhosp=inOut.getDataHospital(num,null);
					inOut.saveDataHosp(alhosp, num);
					System.out.print("Hospital guardado correctamente\n");
					break;
			case 12: //ok
					System.out.print("Ingrese el id del hospital para Guardar los doctores\n");
					num=teclado.nextInt();
					if(inOut.existsDoctors(num)){
						alhosp=inOut.getDataDoctors(num,null);
						inOut.saveDataDoctors(alhosp, num);
						System.out.print("Doctores almacenados correctamente\n");
					}
					else {
						System.out.print("No hay doctores a guardar\n");
					}

				
					break;
			case 13: //ok
					System.out.print("Ingrese el id del hospital para Guardar el Calendario\n");
					num=teclado.nextInt();
					if(inOut.existsCalendar(num)){
						alhosp=inOut.getDataCale(num,null);
						inOut.saveDataCale(alhosp, num);
						System.out.print("Calendario guardado correctamente\n");
					}
					else {
						System.out.print("No hay calendario a guardar\n");
					}
					break;
			case 14: //ok
				System.out.print("Ingrese el id del hospital para guardar las Restricciones\n");
				num=teclado.nextInt();
				if(inOut.existsRes(num)){
					alhosp=inOut.getDataRes(num,null);
					inOut.saveDataRes(alhosp, num);
					System.out.print("Restricciones guardadas correctamente\n");
				}
				else {
					System.out.print("No hay Restricciones a guardar\n");
				}
				break;
			case 15:
				System.out.print("Ingrese el id del hospital para eliminar contenido\n");
				num=teclado.nextInt();
				System.out.print("Ingrese (.H .D .C o .R\n");
				String s = teclado.next();
				inOut.removePart(num,s);
				System.out.print("Se ha eliminado el contenido\n");
				break;
				
			case 16:
				System.out.print("Ingrese el id del hospital para ver el anyo\n");
				num=teclado.nextInt();
				int year=inOut.getYear(num,null);
				System.out.printf("year: %d\n",year);
				break;
			case 17:
				System.out.print("Ingrese el id del hospital para ver el numero de soluciones que tiene:\n");
				num=teclado.nextInt();
				int sols=inOut.howManySolutions(num);
				System.out.printf("sols: %d\n",sols);
				
				
				break;
				
			case 18:
				System.out.print("Ingrese el id del hospital para ver si existe una solucion en un hospital:\n");
				num=teclado.nextInt();
				System.out.print("ingrese el id solucion:\n");
				sols=teclado.nextInt();
				if(inOut.existsSol(num, sols)) System.out.printf("Si que existe\n");
				else System.out.printf("No existe\n");
				
				break;
			case 19:
				System.out.print("Ingrese el id del hospital:\n");
				num=teclado.nextInt();
				System.out.print("ingrese el id solucion para obtenerla:\n");
				sols=teclado.nextInt();
				ArrayList<String> sol = inOut.getDataSol(num, sols);
				for(int i=0;i<sol.size();++i) System.out.print(" "+sol.get(i));
				System.out.println();
				break;
			case 20:
				System.out.print("Ingrese el id del hospital:\n");
				num=teclado.nextInt();
				System.out.print("ingrese el id solucion para obrener los turnos no asignados:\n");
				sols=teclado.nextInt();
				ArrayList<String> nosol = inOut.getDataNoSol(num, sols);
				for(int i=0;i<nosol.size();++i) System.out.print(" "+nosol.get(i));
				System.out.println();
				break;
			
				default:break;
			}
			muestraOpciones();
			opcion = teclado.nextInt();
		}
		teclado.close();
		System.out.print("PROGRAM EXIT");
	}
}
