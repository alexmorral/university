package domain;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import model.*;
/**
 * 
 * @author oscar
 *
 */
public class CtrlGrafo {
	
	private int firstdoc;
	private int lastdoc;
	private int firsttorn;
	private int lasttorn;
	private int sink;
	private Graf<Nodo> grafo;
	
	
	public CtrlGrafo(){
		grafo=new Graf<Nodo>();
	}
	
	public Graf<Nodo> getGraf(){
		return grafo;
	}
	
	public void showNodos()throws IOException {
		
		for(int i =0; i<grafo.getNSize();++i){
			try {
				String tipo = grafo.getNode(i).getTipo();
				System.out.printf("Nodo: %d Tipo %s\n",i, tipo);
			} catch(IOException e) {
				throw new IOException(e);
			}
			
		}
		
	}
	
	private static String itos (int dia){
		String t;
		if(dia==2) t="lunes";
		else if(dia==3) t = "martes";
		else if(dia==4) t = "miercoles";
		else if(dia==5) t = "jueves";
		else if(dia==6) t = "viernes";
		else if(dia==7) t = "sabado";
		else t = "domingo";
		return t; 
	}
	
	public void fillGrafo(Hospital h) throws IOException {
		
		double fm, ft, fn;
		fm=h.getFactorM();
		ft=h.getFactorT();
		fn=h.getFactorN();
		
		int id = 0;
		// creamos grafo
		Nodo Source = new Nodo(0, "Source");	// creamos nodo source
		grafo.afegirNode(Source);    			//A�adimos el source
		ArrayList<Doctor> aldoc = new ArrayList<Doctor>();
		aldoc = h.getDoctors();	
		if (aldoc.isEmpty()) throw new IOException("No contiene doctores\n");
		for (int i = 0; i < aldoc.size(); ++i) {
			++id;
			if(i==0) firstdoc = id;
			nodoDoctor nDoc = new nodoDoctor(id, "Doctor", aldoc.get(i).getId());
			grafo.afegirNode(nDoc);
			grafo.conectarNodes(0, id, aldoc.get(i).getNumMaxTurn(), 0.0);
		}
		
		lastdoc = id;
		++id;
		Nodo Sink = new Nodo(id, "Sink");
		grafo.afegirNode(Sink);
		sink = id;
		//nodos de doctores anadidos
		//anadimos todos los turnos al grafo
		ArrayList<Turno> alturnos = new ArrayList<Turno> ();
		Calendario cal = h.getCalendario();
		alturnos = cal.getALLShifts();
		if (alturnos.isEmpty()) throw new IOException("No contiene turnos\n");
		
		for (int i = 0; i < alturnos.size(); ++i) {
			++id;
			if(i==0) firsttorn = id;
			nodoTurno nturn = new nodoTurno(id,"Turno", alturnos.get(i).getDate(), alturnos.get(i).getShiftType());
			grafo.afegirNode(nturn);
			grafo.conectarNodes(id, sink, alturnos.get(i).getNumberOfDoctors(),0.0); 
		}
		lasttorn=id;
		++id; 
				
		
		//nodos de turnos anadidos	
		// ahora vamos a anadir las restricciones de cada doctor.
		for(int i=firstdoc;i<=lastdoc;++i){ //para cada doctor:
			if(aldoc.get(i-firstdoc).isREmpty()){
				for(int j=firsttorn;j<=lasttorn;++j){
					int capacidad=1; //
					double coste =0;
					String tt = alturnos.get(j-firsttorn).getShiftType();
					double sueldo = aldoc.get(i-firstdoc).getSalaryTurn();
					if(tt.equals("manana")) coste=fm*sueldo;
					else if(tt.equals("tarde")) coste=ft*sueldo;
					else if(tt.equals("noche")) coste=fn*sueldo;
					grafo.conectarNodes(i, j, capacidad, coste);
				}
			}
			else {
			//caso donde si hay restricciones
				boolean Turnos[] = new boolean[alturnos.size()];
				for(int k=0;k<alturnos.size();++k) {
					Turnos[k]=true;
				}
				ArrayList<Restriccion> alRest = aldoc.get(i-firstdoc).getRestrictions();
				for(int k=0;k<alRest.size();++k){
					Restriccion res = alRest.get(k); //tengo la restriccion
					String restipe =res.getTipo(); // tengo el tipo
					if(restipe.equals("NOT_Turno")){
						NOT_Turno N = (NOT_Turno)res;
						String t = N.getTipoTurno();  
						for(int m=0;m<alturnos.size();++m){
							String tipot=alturnos.get(m).getShiftType(); //tipo de turno;
							if(t.equals(tipot)){
								Turnos[m]=false;
							}
						}
					}
					else if(restipe.equals("NOT_Fecha")){
						NOT_Fecha N = (NOT_Fecha)res;
						GregorianCalendar gc = N.getFecha();
						int cont=0;
						for(int m=0;m<alturnos.size();++m){
							GregorianCalendar gc1 = alturnos.get(m).getDate();
							if(gc1.equals(gc)){
								Turnos[m]=false;
								++cont;
							}
							if (cont==3) break;
						}
					}
					else if(restipe.equals("NOT_Especial")){
						NOT_Especial N = (NOT_Especial)res;
						String t =N.getEspecial();
						for(int m=0;m<alturnos.size();++m){
							String tipot=alturnos.get(m).getSpecialDate();
							if(t.equals(tipot)){
								Turnos[m]=false;
							}
						}
					}
					else if(restipe.equals("NOT_Dia_Semana")){
						NOT_Dia_Semana N = (NOT_Dia_Semana)res;
						String t = N.getDiaSemana();
						for(int m=0;m<alturnos.size();++m){
							GregorianCalendar gc1=alturnos.get(m).getDate();
							int weekN = gc1.get(GregorianCalendar.DAY_OF_WEEK);
							String strWeek=itos(weekN);
							if(t.equals(strWeek)){
								Turnos[m]=false;
							}
						}
					}
					else if (restipe.equals("NOT_Dia_Mes")){
						NOT_Dia_Mes N = (NOT_Dia_Mes)res;
						int  dia = N.getDiaMes();
						for(int m=0;m<alturnos.size();++m){
							GregorianCalendar gc1=alturnos.get(m).getDate();
							int day = gc1.get(GregorianCalendar.DAY_OF_MONTH);
							if(dia==day)Turnos[m]=false;
						}
					}				
				}
				for(int k=0;k<alRest.size();++k){
					Restriccion res = alRest.get(k); //tengo la restriccion
					String restipe =res.getTipo(); // tengo el tipo
					
					if (restipe.equals("XOR")){
						Nodo XOR = new Nodo(id, "XOR");
						grafo.afegirNode(XOR);
						grafo.conectarNodes(i, id ,1, 0.0);
						ArrayList<Turno> xorTurnos = ((XOR)res).getListTurnos();
						boolean modif = false;
						for(Turno turno : xorTurnos) {
							for(int m = 0; m < alturnos.size(); ++m) {
								
								GregorianCalendar gc1 = turno.getDate();
								String fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(gc1.getTime());
								
								GregorianCalendar gc2 = alturnos.get(m).getDate();
								String fecha1 = DateFormat.getDateInstance(DateFormat.SHORT).format(gc2.getTime());
																
								if(fecha.equals(fecha1) & alturnos.get(m).getShiftType().equals(turno.getShiftType()) & Turnos[m]==true) {
									modif = true;
									int capacidad = 1;
									double coste = 0;
									String tt = alturnos.get(m).getShiftType();
									double sueldo = aldoc.get(i-firstdoc).getSalaryTurn();
									if(tt.equals("manana")) coste=fm*sueldo;
									else if(tt.equals("tarde")) coste=ft*sueldo;
									else if(tt.equals("noche")) coste=fn*sueldo;
									grafo.conectarNodes(id, m+firsttorn, capacidad, coste);
									Turnos[m]=false;
								}
							}
						}
						if(modif==true)++id;
						else {
							grafo.removeNode(id);
						}
					}
				}
				for(int k=0;k<alRest.size();++k){
					Restriccion res = alRest.get(k);
					String restipe =res.getTipo(); 
					if (restipe.equals("MAX_Turnos_por_Dia")){
						MAX_Turnos_por_Dia N = (MAX_Turnos_por_Dia)res;
						int max = N.getNumTurnos();	
						if(max==0) for(int m=0;m<alturnos.size();++m) Turnos[m]=false;
						else if(max == 1 | max ==2){
							for(int m=0;m<alturnos.size();m=m+3){
								boolean modif =false;
								Nodo MAX = new Nodo(id, "MaxTDia");
								grafo.afegirNode(MAX);
								grafo.conectarNodes(i, id, max, 0.0);
								if(Turnos[m]==true){
									modif=true;
									int capacidad = 1;
									double coste = 0;
									String tt = alturnos.get(m).getShiftType();
									double sueldo = aldoc.get(i-firstdoc).getSalaryTurn();
									if(tt.equals("manana")) coste=fm*sueldo;
									else if(tt.equals("tarde")) coste=ft*sueldo;
									else if(tt.equals("noche")) coste=fn*sueldo;
									grafo.conectarNodes(id, m+firsttorn, capacidad, coste);
									Turnos[m]=false;
								}
								if(Turnos[m+1]==true){
									modif=true;
									int capacidad = 1;
									double coste = 0;
									String tt = alturnos.get(m+1).getShiftType();
									double sueldo = aldoc.get(i-firstdoc).getSalaryTurn();
									if(tt.equals("manana")) coste=fm*sueldo;
									else if(tt.equals("tarde")) coste=ft*sueldo;
									else if(tt.equals("noche")) coste=fn*sueldo;
									grafo.conectarNodes(id, m+1+firsttorn, capacidad, coste);
									Turnos[m+1]=false;
								}
								if(Turnos[m+2]==true){
									modif=true;
									int capacidad = 1;
									double coste = 0;
									String tt = alturnos.get(m+2).getShiftType();
									double sueldo = aldoc.get(i-firstdoc).getSalaryTurn();
									if(tt.equals("manana")) coste=fm*sueldo;
									else if(tt.equals("tarde")) coste=ft*sueldo;
									else if(tt.equals("noche")) coste=fn*sueldo;
									grafo.conectarNodes(id, m+2+firsttorn, capacidad, coste);
									Turnos[m+2]=false;
								}
								if(modif==true)++id;
								else grafo.removeNode(id);
							}
						}
					}
					else if (restipe.equals("MAX_Turnos_Rango")){
						MAX_Turnos_Rango N = ((MAX_Turnos_Rango)res);
						GregorianCalendar fechaIni = ((MAX_Turnos_Rango)res).getFechaIni();
						GregorianCalendar fechaFin = ((MAX_Turnos_Rango)res).getFechaFin();
						int firstDay = fechaIni.get(GregorianCalendar.DAY_OF_YEAR);
						int  lastDay = fechaFin.get(GregorianCalendar.DAY_OF_YEAR);
						int numDias=N.getNumDias();
						boolean modif = false;
						Nodo MRango = new Nodo(id, "MRango");
						grafo.afegirNode(MRango);
						grafo.conectarNodes(i, id, numDias, 0.0);
						for(int m=0;m<alturnos.size();++m){
							GregorianCalendar gc1 = alturnos.get(m).getDate();
							int day = gc1.get(GregorianCalendar.DAY_OF_YEAR);
							System.out.print("entru\n");
							if((day>=firstDay & day <=lastDay) & Turnos[m]==true){
								System.out.print("entru1\n");
								modif=true;
								int capacidad = 1;
								double coste = 0;
								String tt = alturnos.get(m).getShiftType();
								double sueldo = aldoc.get(i-firstdoc).getSalaryTurn();
								if(tt.equals("manana")) coste=fm*sueldo;
								else if(tt.equals("tarde")) coste=ft*sueldo;
								else if(tt.equals("noche")) coste=fn*sueldo;
								grafo.conectarNodes(id, m+firsttorn, capacidad, coste);
								Turnos[m]=false;
							}
						}
						if(modif==true)++id;
						else grafo.removeNode(id);
					}
					/*else if (restipe.equals("MAX_Turnos_Consecutivos")){
					
					}*/
					
				}//fi else de restricciones
				for(int j=firsttorn;j<=lasttorn;++j){
					if(Turnos[j-firsttorn]==true ){
						int capacidad=1;
						double coste =0;
						String tt = alturnos.get(j-firsttorn).getShiftType();
						double sueldo = aldoc.get(i-firstdoc).getSalaryTurn();
						if(tt.equals("manana")) coste=fm*sueldo;
						else if(tt.equals("tarde")) coste=ft*sueldo;
						else if(tt.equals("noche")) coste=fn*sueldo;
						grafo.conectarNodes(i, j, capacidad, coste);	
					}
				}
			}//fin de restricciones de un doctor			
		}//fi for de llenar restricciones para todos los doctores
	}//fin de llenagrafo
}//ficlas
