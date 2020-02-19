package domain;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import data.CtrlDatosFichero;

import model.*;

/**
 * Controladora de la clase Doctor y Restriccion
 * @author Sergi Orra 
 */
public class CtrlDoctor {
	
	/** Atributos */
	private ArrayList<Doctor> Doctors;
	private int yearCalendario;
	
	private static String readDate(String date,String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		GregorianCalendar gc=new GregorianCalendar();
		sdf.setLenient(false);
		gc.setTime(sdf.parse(date));
		SimpleDateFormat sdf2 = new SimpleDateFormat(format);
		String result =sdf2.format(gc.getTime());
		return result;
	}

	
	/**
	* Constructora del control
	*/
	public CtrlDoctor(){
		Doctors = new ArrayList<Doctor>();
	}
	
	/**
	* Constructora del control con atributo
	* @param Doc: Lista de los Doctores del hospital
	* 		 cal: Year del calendario que se usa en el hospital
	*/
	public CtrlDoctor(ArrayList<Doctor> Doc, int cal){
		Doctors = Doc;
		yearCalendario = cal;
	}
	
	
	
/* Metodos publicos */
	
	/**
	*Consultora de todos los identificadores dels doctores
	* @return ids de los doctores
	*/
	public ArrayList<Integer> getAllDoctors() {
		ArrayList<Integer> docs = new ArrayList<Integer>();
		for (int i = 0; i < Doctors.size(); ++i) {
			docs.add(Doctors.get(i).getId());
		}
		return docs;
	}
	
	/**
	*Modificadora del year
	* @param y: Year nuevo
	*/	
	public void setCalendariYear(int y) {
		yearCalendario = y;
	}
	
	/**
	*Consultora del nombre del Doctor
	* @param idDoc: Identificador del Doctor
	* @return El nombre del Doctor
	* @throws Exception
	*/	
	public String getNombreDoctor(int idDoc) throws IOException {	
		if(idDoc < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				return Doctors.get(i).getName();
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		return null;
	}
	
	/**
	*Consultora del numero maximo de turnos del Doctor
	* @param idDoc: Identificador del Doctor
	* @return Numero maximo de turnos del Doctor
	* @throws Exception
	*/	
	public Integer getNumMaxTurnosDoctor(int idDoc) throws IOException {		
		if(idDoc < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				return Doctors.get(i).getNumMaxTurn();
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		return null;
	}
	
	/**
	*Consultora del sueldo por turno del Doctor
	* @param idDoc: Identificador del Doctor
	* @return Sueldo por turno del Doctor
	* @throws Exception
	*/	
	public Double getSueldoTurnosDoctor(int idDoc) throws IOException {		
		if(idDoc < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				return Doctors.get(i).getSalaryTurn();
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		return null;
	}
	
	
	public ArrayList<ArrayList<String>> loadRest(int id){
		Doctor doct = new Doctor();
		for(Doctor doc: Doctors){
			if(doc.getId() == id){
				doct = doc;
			}
		}
		return  doct.loadRest();
	}
	
	
	/** 
	* Modificadora del Doctor
	* @param idDoc: es el nuevo identificador del Doctor
	* 		 nombre: nuevo nombre del Doctor
	* 		 numMax: nuevo  numero maximo de turnos del Doctor
	* 		 sueldo: nuevo suledo por turno del doctor
	* @throws Exception
	*/
	public void setDoctor(int idDoc, String nombre, int numMax, double sueldo) throws IOException {
		if(idDoc < 0) throw new IOException("Valor del identificador incorrecto");
		if(numMax < 0) throw new IOException("Numero maximo de turnos incorrecto");
		if(sueldo < 0.0) throw new IOException("Sueldo por turno incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				Doctors.get(i).setName(nombre);
				Doctors.get(i).setNumMaxTurn(numMax);
				Doctors.get(i).setSalaryTurn(sueldo);
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	
	/**
	* Consultora del tipo de la Restriccion
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return El tipo de la restriccion
	* @throws Exception
	*/
	public String getTipoRestriccion(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						return alres.get(j).getTipo();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	/**
	* Consultora de los ids de los doctores
	* @param 
	* @return ArrayList con todos los ids de los doctores
	*/
	public ArrayList<Integer> getIdsDoctores() {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(Doctor doc : Doctors){
			ids.add(doc.getId());
		}
		return ids;
	}
	
	/**
	* Consultora de la primera posicion libre para asignar ID al doctor
	* @param 
	* @return Primera ID libre
	*/
	public Integer getFDIdocs() throws IOException {
		ArrayList<Integer> ids = getIdsDoctores();
		 Collections.sort(ids, new Comparator<Integer>() 
		            { public int compare(Integer p, Integer q)
		            {
			            if(p > q) return 1;
			            if(p < q) return -1;
		                return 0;
		            }
		            } );
		for(int i=0; i< Doctors.size();++i){
			if(i!=ids.get(i)){
				return i;
			}
		}
		return ids.size();
	}
	
	/**
	* Consultora de los ids de las restricciones
	* @param id: id del doctor que consultar sus restricciones
	* @return ArrayList con todas las ids de las restricciones
	*/
	public ArrayList<Integer> getIdsRestrictions(int id) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(Doctor doc : Doctors){
			if(doc.getId()==id){
				ArrayList<Restriccion>Res=doc.getRestrictions();
				for(Restriccion res: Res){
					ids.add(res.getIdRestriccion());
				}
			}
		}
		return ids;
	}
	
	/**
	 * 
	 * @return return el primer id disponible de las restricciones
	 * @throws IOE
	 */
	public Integer getFDIRes(int id) throws IOException {
		ArrayList<Integer> ids = this.getIdsRestrictions(id);
		 Collections.sort(ids, new Comparator<Integer>() 
		            { public int compare(Integer p, Integer q)
		            {
			            if(p > q) return 1;
			            if(p < q) return -1;
		                return 0;
		            }
		            } );
		
			for(int i=0; i< ids.size();++i){
				if(i!=ids.get(i)){
					return i;
				}
			}
		return ids.size();
	}
	
	/**
	* Consultora del numero maximo de turnos que se trabaja por dia de la Restriccion MAX_Turnos_por_Dia
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return numero maximo de turnos que se trabaja por dia
	* @throws Exception
	*/
	public Integer getMAX_Turnos_Por_Dia(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						MAX_Turnos_por_Dia N = (MAX_Turnos_por_Dia)alres.get(j);
						return N.getNumTurnos();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Consultora de la fecha de inicio de la Restriccion MAX_Turnos_Rango
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return fecha inicio de la restriccion
	* @throws Exception
	*/
	public GregorianCalendar getMAX_Turnos_Rango_FeIni(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						MAX_Turnos_Rango N = (MAX_Turnos_Rango)alres.get(j);
						return N.getFechaIni();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	/**
	* Consultora de la fecha fin de la Restriccion MAX_Turnos_Rango
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return fecha fin de la restriccion
	* @throws Exception
	*/
	public GregorianCalendar getMAX_Turnos_Rango_FeFin(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						MAX_Turnos_Rango N = (MAX_Turnos_Rango)alres.get(j);
						return N.getFechaFin();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	/**
	* Consultora del numero de turnos de la Restriccion MAX_Turnos_Rango
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return numero de turnos de la restriccion
	* @throws Exception
	*/
	public Integer getMAX_Turnos_Rango_numT(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						MAX_Turnos_Rango N = (MAX_Turnos_Rango)alres.get(j);
						return N.getNumDias();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Consultora del dia del mes de la Restriccion NOT_Dia_Mes
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return Dia del mes de la restriccion
	* @throws Exception
	*/
	public Integer getNOT_Dia_Mes(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						NOT_Dia_Mes N = (NOT_Dia_Mes)alres.get(j);
						return N.getDiaMes();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Consultora del dia de la semana de la Restriccion NOT_Dia_Semana
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return Dia de la semana de la restriccion
	* @throws Exception
	*/
	public String getNOT_Dia_Semana(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						NOT_Dia_Semana N = (NOT_Dia_Semana)alres.get(j);
						return N.getDiaSemana();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Consultora del dia especial de la Restriccion NOT_Especial
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return Dia especial de la restriccion
	* @throws Exception
	*/
	public String getNOT_Especial(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						NOT_Especial N = (NOT_Especial)alres.get(j);
						return N.getEspecial();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Consultora de la fecha de la Restriccion NOT_Fecha
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return Fecha de la restriccion
	* @throws Exception
	*/
	public GregorianCalendar getNOT_Fecha(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						NOT_Fecha N = (NOT_Fecha)alres.get(j);
						return N.getFecha();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Consultora del tipo de tunro de la Restriccion NOT_Turno
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return Tipo de turno de la restriccion
	* @throws Exception
	*/
	public String getNOT_Turno(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						NOT_Turno N = (NOT_Turno)alres.get(j);
						return N.getTipoTurno();
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Consultora de la lista de fechas de la restriccion XOR
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return Lista de fechas de la restriccion
	* @throws Exception
	*/
	public ArrayList<GregorianCalendar> getXOR_Dates(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						XOR N = (XOR)alres.get(j);
						ArrayList<GregorianCalendar> dates = new ArrayList<GregorianCalendar>();
						ArrayList<Turno> turnos = N.getListTurnos();
						for (int z = 0; z < turnos.size(); ++z) {
							GregorianCalendar fecha = turnos.get(z).getDate();
							dates.add(fecha);
						}
						return dates;
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	/**
	* Consultora de la lista de tipos de turnos de la restriccion XOR
	* @param idDoc: Identificador del Doctor
	* 		 idRes: Identificador de la restriccion
	* @return Lista de tipos de turnos de la restriccion
	* @throws Exception
	*/
	public ArrayList<String> getXOR_TipoTurno(int idDoc, int idRes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat, trobat2;
		trobat = trobat2 = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getIdRestriccion() == idRes) {
						trobat2 = true;
						XOR N = (XOR)alres.get(j);
						ArrayList<String> tTurno = new ArrayList<String>();
						ArrayList<Turno> turnos = N.getListTurnos();
						for (int z = 0; z < turnos.size(); ++z) {
							tTurno.add(turnos.get(z).getShiftType());
						}
						return tTurno;
					}
					if (trobat2) break;
				}
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		if (!trobat2) throw new IOException("No existe una Restriccion con esta Id");
		return null;
	}
	
	
	/**
	* Guarda todos los datos de las restricciones en una ArrayList para luego, guardarlo en los datos.
	* @param id: Id del hospital que guardar las restricciones
	* @return
	* @throws IOException
	*/
	public void saveDataRes(int id)throws IOException{
		ArrayList<String> alRes = new ArrayList<String>();
		
			for(int i=0;i<Doctors.size();++i){
				int idDoc=Doctors.get(i).getId();
				if(!Doctors.get(i).isREmpty()){
					alRes.add(Integer.toString(idDoc));
					ArrayList<Restriccion> Res = Doctors.get(i).getRestrictions();
					int numRes=Res.size();
					alRes.add(Integer.toString(numRes));
					for(int j=0;j<numRes;++j){
						alRes.add(Integer.toString(Res.get(j).getIdRestriccion()));
						alRes.add(Res.get(j).getTipo()); // ok vamos a ver kual es
						if(Res.get(j).getTipo().equals("NOT_Turno")){
							NOT_Turno N = (NOT_Turno)Res.get(j);
							alRes.add(N.getTipoTurno());
						}
						else if(Res.get(j).getTipo().equals("NOT_Fecha")){
							NOT_Fecha N = (NOT_Fecha)Res.get(j);
							GregorianCalendar gc=N.getFecha();
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String fecha =sdf.format(gc.getTime());
							alRes.add(fecha);
							
						}
						else if(Res.get(j).getTipo().equals("NOT_Especial")){
							NOT_Especial N = (NOT_Especial)Res.get(j);
							alRes.add(N.getEspecial());
						}
						else if(Res.get(j).getTipo().equals("NOT_Dia_Semana")){
							NOT_Dia_Semana N = (NOT_Dia_Semana)Res.get(j);
							alRes.add(N.getDiaSemana());
						}
						else if(Res.get(j).getTipo().equals("NOT_Dia_Mes")){
							NOT_Dia_Mes N = (NOT_Dia_Mes)Res.get(j);
							alRes.add(Integer.toString(N.getDiaMes()));
						}
						
						else if(Res.get(j).getTipo().equals("MAX_Turnos_Rango")){
							
							MAX_Turnos_Rango N = (MAX_Turnos_Rango)Res.get(j);
							
							GregorianCalendar gc=N.getFechaIni();
							GregorianCalendar gc1=N.getFechaFin();
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String fechaini =sdf.format(gc.getTime());
							String fechafin =sdf.format(gc1.getTime());
							alRes.add(fechaini);
							alRes.add(fechafin);
							alRes.add(Integer.toString(N.getNumDias()));
						}
						else if(Res.get(j).getTipo().equals("MAX_Turnos_por_Dia")){
							MAX_Turnos_por_Dia N = (MAX_Turnos_por_Dia)Res.get(j);
							alRes.add(Integer.toString(N.getNumTurnos()));
						}
						else if(Res.get(j).getTipo().equals("XOR")){
							XOR N = (XOR)Res.get(j);
							ArrayList<Turno> alTurn=N.getListTurnos();
							SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
							alRes.add(Integer.toString(alTurn.size()));
							for(int k=0;k<alTurn.size();++k){
								Turno t=alTurn.get(k);
								GregorianCalendar gc=t.getDate();
								String fecha =sdf.format(gc.getTime());
								alRes.add(fecha);
								alRes.add(t.getShiftType());
							}
						}
					}
				}
			}
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		inOut.saveDataRes(alRes, id);
	}
	
	/**
	* Carga todas las restricciones al hospital 
	* @param id: Id del hospital donde cargar las restricciones 
	* @return
	* @throws IOException
	*/
	public void addResData(int id,String path)throws IOException{
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		ArrayList<String> alRes = inOut.getDataRes(id,path);
			int idDoc;
		for(int i=0;i<alRes.size();++i){
			int NumRes, idRes;
			idDoc=Integer.parseInt(alRes.get(i));
			++i;
			NumRes=Integer.parseInt(alRes.get(i));
			for(int j=0; j<NumRes;++j){
				++i;
				idRes=Integer.parseInt(alRes.get(i));
				++i;
				String tipo = alRes.get(i);
				if(tipo.equals("NOT_Turno")){
						++i;
						String word = alRes.get(i); //tipoturno
						addResNOT_Turno(idDoc,idRes,word);
				}
				else if(tipo.equals("NOT_Fecha")){
					++i;
					String fecha =  alRes.get(i);
					int d=0,m=0,a=0;
					try{
					d=Integer.parseInt(readDate(fecha,"dd"));
					 m=Integer.parseInt(readDate(fecha,"MM"));
					 a =Integer.parseInt(readDate(fecha,"yyyy"));
					}catch (ParseException e){System.out.print("ERROR");}
					addResNOT_Fecha(idDoc,idRes,d,m,a);
				}
				else if(tipo.equals("NOT_Especial")){
					++i;
					String word=alRes.get(i); //especial
					addResNOT_Especial(idDoc,idRes,word);
				}
				else if(tipo.equals("NOT_Dia_Semana")){
					++i;
					String word=alRes.get(i);
					addResNOT_Dia_Semana(idDoc,idRes,word);
				}
				else if(tipo.equals("NOT_Dia_Mes")){
					++i;
					int dia =Integer.parseInt(alRes.get(i));
					addResNOT_Dia_Mes(idDoc,idRes,dia);
				}
				else if(tipo.equals("MAX_Turnos_Rango")){
					int d1=0, m1=0, a1=0, d2=0,m2=0,a2=0;
					++i;
					String fechaini =  alRes.get(i);
					++i;
					String fechafin =  alRes.get(i);
					try{
						d1=Integer.parseInt(readDate(fechaini,"dd"));
						 m1=Integer.parseInt(readDate(fechaini,"MM"));
						 a1 =Integer.parseInt(readDate(fechaini,"yyyy"));
						 d2=Integer.parseInt(readDate(fechafin,"dd"));
						 m2=Integer.parseInt(readDate(fechafin,"MM"));
						 a2 =Integer.parseInt(readDate(fechafin,"yyyy"));
					}catch (ParseException e){System.out.print("ERROR");}
					++i;
					int numT=Integer.parseInt(alRes.get(i));
					addResMAX_Turnos_Rango(idDoc,idRes,d1, m1, a1, d2,m2,  a2, numT);					
				}
				else if(tipo.equals("MAX_Turnos_por_Dia")){
					++i;
					int numT =Integer.parseInt(alRes.get(i));
					addResMAX_Turnos_por_Dia(idDoc,idRes,numT);
				}
				else if(tipo.equals("XOR")){
					++i;
					int numdays=Integer.parseInt(alRes.get(i));
					ArrayList<Integer> diaXOR = new ArrayList<Integer>();
					ArrayList<Integer> mesXOR = new ArrayList<Integer>();
					ArrayList<Integer> yearXOR = new ArrayList<Integer>();
					ArrayList<String> tipoTurnoXOR = new ArrayList<String>();
					for(int k=0;k<numdays;++k){
						++i;
						String strDate =  alRes.get(i);
							int d = Integer.parseInt(strDate.substring(0, 2));
							int M = Integer.parseInt(strDate.substring(2, 4));
							int year =Integer.parseInt(strDate.substring(4));
						diaXOR.add(d);
						mesXOR.add(M);
						yearXOR.add(year);
						++i;
						tipoTurnoXOR.add(alRes.get(i));
					}
					addResXOR(idDoc,idRes,diaXOR,mesXOR,yearXOR,tipoTurnoXOR);
				}
			}
		}
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo MAX_Turnos_por_Dia nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  numT: Numero de turnos	
	 * @throws Exception
	 */
	public void addResMAX_Turnos_por_Dia(int idDoc, int idRes, int numT) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		if(numT < 0 || numT > 3) throw new IOException("Valor del numero de turnos incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				/* Comprovar si ya hay una restriccion MAX_Turnos_por_Dia */
				for (int z = 0; z < alres.size(); ++z) {
					if (alres.get(z).getTipo().equals("MAX_Turnos_por_Dia")) throw new IOException("Este doctor ya tiene una restriccion MAX Turnos por Dia");
				}
				trobat = true;
				Restriccion res = new MAX_Turnos_por_Dia(idRes, numT);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo MAX_Turnos_Rango nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  d1: Dia primera fecha
	 * 		  m1: Mes primera fecha
	 * 		  a1: Year primera fecha	
	 * 		  d2: Dia segunda fecha
	 * 		  m2: Mes segunda fecha
	 * 		  a2: Year segunda fecha
	 * 		  numT: Numero de turnos
	 * @throws Exception
	 */
	public void addResMAX_Turnos_Rango(int idDoc, int idRes, int d1, int m1, int a1, int d2, int m2, int a2, int numT) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		if (a1 != yearCalendario || a2 != yearCalendario) throw new IOException("Year incorrecto");
		if (d1 < 1 || d1 > 31 || m1 < 1 || m1 > 12 || a1 < 1) throw new IOException("Valores de la fecha incorrecto");
		if (d2 < 1 || d2 > 31 || m2 < 1 || m2 > 12 || a2 < 1) throw new IOException("Valores de la fecha incorrecto");
		if(numT < 0) throw new IOException("Numero de turnos incorrecto");
		if (m1 > m2) throw new IOException("Fecha final es anterior a la fecha inicial");
		if (m1 == m2) {
			if (d1 >= d2) throw new IOException("Fecha final es anterior a la fecha inicial");
		}
		GregorianCalendar fecha1 = new GregorianCalendar(a1,m1-1,d1);
		GregorianCalendar fecha2 = new GregorianCalendar(a2,m2-1,d2);
		if (!fecha1.isLenient() || !fecha2.isLenient()) throw new IOException("Fecha invalida");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				int firstDay = fecha1.get(GregorianCalendar.DAY_OF_YEAR);
				int lastDay = fecha2.get(GregorianCalendar.DAY_OF_YEAR);
				/*Comprovar si el doctor tiene una XOR con una fecha dentro del rango de la MAX que se quiere poner */
				for (int j = 0; j < alres.size(); ++j) {
					if (alres.get(j).getTipo().equals("XOR")) {
						XOR N = (XOR)alres.get(j);
						ArrayList<Turno> listXOR = N.getListTurnos();
						for (int z = 0; z < listXOR.size(); ++z) {
							int diaXOR = listXOR.get(z).getDate().get(GregorianCalendar.DAY_OF_YEAR);
							if (diaXOR <= lastDay && diaXOR >= firstDay) throw new IOException("Este doctor ya tiene una XOR que apunta una turno del rango de la MAX que se uqiere poner");
						}
					}
					/* comprovo si hi ha alguna max rango solapada */
					if (alres.get(j).getTipo().equals("MAX_Turnos_Rango")) {
						MAX_Turnos_Rango N = (MAX_Turnos_Rango)alres.get(j);
						if (m1 > N.getFechaIni().get(GregorianCalendar.MONTH)+1 && (m1 < N.getFechaFin().get(GregorianCalendar.MONTH)+1)) throw new IOException("MAX Solapada con MAX");
						if (m2 > N.getFechaIni().get(GregorianCalendar.MONTH)+1 && (m2 < N.getFechaFin().get(GregorianCalendar.MONTH)+1)) throw new IOException("MAX Solapada con MAX");
						if (m1 == N.getFechaIni().get(GregorianCalendar.MONTH)+1 && (m1 < N.getFechaFin().get(GregorianCalendar.MONTH)+1)) {
							if (d1 >= N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) throw new IOException("MAX Solapada con MAX");
						}
						if ((m2 == N.getFechaFin().get(GregorianCalendar.MONTH)+1) && (m2 > N.getFechaIni().get(GregorianCalendar.MONTH)+1)) {
							if (d2 <= N.getFechaFin().get(GregorianCalendar.DAY_OF_MONTH)) throw new IOException("MAX Solapada con MAX");
						}
						if ((m2 == N.getFechaIni().get(GregorianCalendar.MONTH)+1) && (m1 < N.getFechaIni().get(GregorianCalendar.MONTH)+1)) {
							if (d2 >= N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) throw new IOException("MAX Solapada con MAX");
						}
						if ((m1 == N.getFechaFin().get(GregorianCalendar.MONTH)+1) && (m2 > N.getFechaFin().get(GregorianCalendar.MONTH)+1)) {
							if (d2 >= N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) throw new IOException("MAX Solapada con MAX");
						}
						if ((m1 < N.getFechaIni().get(GregorianCalendar.MONTH)+1) && (m2 > N.getFechaIni().get(GregorianCalendar.MONTH)+1)) throw new IOException("MAX Solapada con MAX7");
						if (m1 == N.getFechaFin().get(GregorianCalendar.MONTH)+1 && m1 > N.getFechaIni().get(GregorianCalendar.MONTH)+1) {
							if (d1 <=  N.getFechaFin().get(GregorianCalendar.DAY_OF_MONTH)) throw new IOException("MAX Solapada con MAX");
						}
						if (m2 == N.getFechaIni().get(GregorianCalendar.MONTH)+1 && m2 < N.getFechaFin().get(GregorianCalendar.MONTH)+1) {
							if (d2 >=  N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) throw new IOException("MAX Solapada con MAX");
						}
						if ((m2 == N.getFechaFin().get(GregorianCalendar.MONTH)+1) && (m1 == N.getFechaIni().get(GregorianCalendar.MONTH)+1)) {
							if ((d1 >= N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) && (d1 <= N.getFechaFin().get(GregorianCalendar.DAY_OF_MONTH))) throw new IOException("MAX Solapada con MAX");
							if ((d2 >= N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) && (d2 <= N.getFechaFin().get(GregorianCalendar.DAY_OF_MONTH))) throw new IOException("MAX Solapada con MAX");
							if ((d1 >= N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) && (d2 <= N.getFechaFin().get(GregorianCalendar.DAY_OF_MONTH))) throw new IOException("MAX Solapada con MAX");
							if ((d1 <= N.getFechaIni().get(GregorianCalendar.DAY_OF_MONTH)) && (d2 >= N.getFechaFin().get(GregorianCalendar.DAY_OF_MONTH))) throw new IOException("MAX Solapada con MAX");
						}
					}
				}
				Restriccion res = new MAX_Turnos_Rango(idRes, d1, m1, a1, d2, m2, a2, numT);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo NOT_Dia_Mes nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  diaMes: Dia del mes	
	 * @throws Exception
	 */
	public void addResNOT_Dia_Mes(int idDoc, int idRes, int diaMes) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		if (diaMes < 1 || diaMes > 31) throw new IOException("Dia del mes incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				/* Comprovar si hay alguna otra NOT_Dia_MES con el mismo dia */
				for (int z = 0; z < alres.size(); ++z) {
					if (alres.get(z).getTipo().equals("NOT_Dia_Mes")) {
						NOT_Dia_Mes N = (NOT_Dia_Mes)alres.get(z);
						int dia2 = N.getDiaMes();
						if (diaMes == dia2) throw new IOException("El doctor ya tiene una restriccion NOT Dia Mes igual");
					}
				}
				trobat = true;
				Restriccion res = new NOT_Dia_Mes(idRes, diaMes);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo NOT_Dia_Semana nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  dia: Dia de la semana	
	 * @throws Exception
	 */
	public void addResNOT_Dia_Semana(int idDoc, int idRes, String dia) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		if (!dia.equals("lunes") && !dia.equals("martes") && !dia.equals("miercoles") && !dia.equals("jueves") &&
			!dia.equals("viernes") && !dia.equals("sabado") && !dia.equals("domingo")) throw new IOException("Dia de la semana incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				/* Comprovar si hay alguna otra NOT_Dia_Semana con el mismo dia */
				for (int z = 0; z < alres.size(); ++z) {
					if (alres.get(z).getTipo().equals("NOT_Dia_Semana")) {
						NOT_Dia_Semana N = (NOT_Dia_Semana)alres.get(z);
						String dia2 = N.getDiaSemana();
						if (dia.equals(dia2)) throw new IOException("El doctor ya tiene una restriccion NOT Dia Semana igual");
					}
				}
				trobat = true;
				Restriccion res = new NOT_Dia_Semana(idRes, dia);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo NOT_Especial nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  especial: Dia especial
	 * @throws Exception
	 */
	public void addResNOT_Especial(int idDoc, int idRes, String especial) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		if (!especial.equals("navidad") && !especial.equals("semana_santa") && !especial.equals("noche_vieja") &&
			!especial.equals("noche_buena")) throw new IOException("Dia especial incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				/* Comprovar si hay alguna otra NOT_Especial igual */
				for (int z = 0; z < alres.size(); ++z) {
					if (alres.get(z).getTipo().equals("NOT_Especial")) {
						NOT_Especial N = (NOT_Especial)alres.get(z);
						String dia2 = N.getEspecial();
						if (especial.equals(dia2)) throw new IOException("El doctor ya tiene una restriccion NOT Especial igual");
					}
				}
				trobat = true;
				Restriccion res = new NOT_Especial(idRes, especial);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo NOT_Fecha nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  d: Dia de la fecha
	 * 		  m: Mes de la fecha
	 * 		  a: Year de la fecha	
	 * @throws Exception
	 */
	public void addResNOT_Fecha(int idDoc, int idRes, int d, int m, int a) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		if (a != yearCalendario) throw new IOException("Year incorrecto");
		if (d < 1 || d > 31 || m < 1 || m > 12 || a < 1) throw new IOException("Valores de la fecha incorrecto");
		GregorianCalendar fecha = new GregorianCalendar(a,m-1,d);
		if (!fecha.isLenient()) throw new IOException("Fecha invalida");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				/* Comprovar si hay alguna otra NOT_fecha con la misma fecha */
				for (int z = 0; z < alres.size(); ++z) {
					if (alres.get(z).getTipo().equals("NOT_Fecha")) {
						NOT_Fecha N = (NOT_Fecha)alres.get(z);
						GregorianCalendar dia2 = N.getFecha();
						if ((dia2.get(GregorianCalendar.DAY_OF_MONTH) == d) &&
							(dia2.get(GregorianCalendar.MONTH)+1 == m) &&
							(dia2.get(GregorianCalendar.YEAR) == a)) throw new IOException("El doctor ya tiene una restriccion NOT Fecha igual");
					}
				}
				trobat = true;
				Restriccion res = new NOT_Fecha(idRes, d, m, a);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo NOT_Turno nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  turno: Tipo del turno	
	 * @throws Exception
	 */
	public void addResNOT_Turno(int idDoc, int idRes, String turno) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		if (!turno.equals("manana") && !turno.equals("tarde") && !turno.equals("noche")) throw new IOException("Tipo de turno incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				/* Comprovar si hay alguna otra NOT_Turno igual */
				for (int z = 0; z < alres.size(); ++z) {
					if (alres.get(z).getTipo().equals("NOT_Turno")) {
						NOT_Turno N = (NOT_Turno)alres.get(z);
						String turno2 = N.getTipoTurno();
						if (turno.equals(turno2)) throw new IOException("El doctor ya tiene una restriccion NOT Turno igual");
					}
				}
				trobat = true;
				Restriccion res = new NOT_Turno(idRes, turno);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}
	
	/** 
	 * Metodo que inserta una restriccion tipo XOR nueva en el Doctor
	 * @param idDoc: Identificador del Doctor
	 * 		  idRes: Identificador de la restriccion
	 * 		  diaXOR: Lista de los dias de las diferentes fechas de la restriccion	
	 * 		  mesXOR: Lista de los meses de las diferentes fechas de la restriccion	
	 *        yearXOR: Lista de los years de las diferentes fechas de la restriccion	
	 *        tipoTurnoXOR: Lista de los tipos de tunros de las diferentes fechas de la restriccion	
	 * @throws Exception
	 */
	public void addResXOR(int idDoc, int idRes, ArrayList<Integer> diaXOR, ArrayList<Integer> mesXOR,  ArrayList<Integer> yearXOR, ArrayList<String> tipoTurnoXOR) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		for (int i = 0; i < diaXOR.size(); ++i) {
			if (yearXOR.get(i) !=  yearCalendario) throw new IOException("Year incorrecto");
			if (diaXOR.get(i) < 1 || diaXOR.get(i) > 31 || mesXOR.get(i) < 1 || mesXOR.get(i) > 12 ||
				yearXOR.get(i) < 1) throw new IOException("valores de la fecha incorrecto");
			if (!tipoTurnoXOR.get(i).equals("manana") && !tipoTurnoXOR.get(i).equals("tarde") && !tipoTurnoXOR.get(i).equals("noche")) throw new IOException("Tipo del turno incorrecto");
			GregorianCalendar fecha = new GregorianCalendar(yearXOR.get(i),mesXOR.get(i)-1,diaXOR.get(i));
			if (!fecha.isLenient()) throw new IOException("Fecha invalida");
		}
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				ArrayList<Turno> listXOR = new ArrayList<Turno>();
				ArrayList<Restriccion> alres = Doctors.get(i).getRestrictions();
				for (int j = 0; j < diaXOR.size(); ++j) {
					GregorianCalendar fecha = new GregorianCalendar(yearXOR.get(j),mesXOR.get(j)-1,diaXOR.get(j));
					Turno turno = new Turno(fecha, tipoTurnoXOR.get(j));
					listXOR.add(turno);
				}
				/* Comprovar si hay alguna otra XOR o MAX_Turnos_Rango en algun mismo turno de la XOR que se quiere poner */
				for (int z = 0; z < alres.size(); ++z) {
					if (alres.get(z).getTipo().equals("XOR")) {
						XOR N = (XOR)alres.get(z);
						ArrayList<Turno> listXOR2 = N.getListTurnos();
						for (int j = 0; j < listXOR.size(); ++j) {
							for (int k = 0; k < listXOR2.size(); ++k) {
								if (listXOR.get(j).getDate().get(GregorianCalendar.DAY_OF_YEAR) == listXOR2.get(k).getDate().get(GregorianCalendar.DAY_OF_YEAR) &&
									listXOR.get(j).getDate().get(GregorianCalendar.MONTH) == listXOR2.get(k).getDate().get(GregorianCalendar.MONTH) &&
									listXOR.get(j).getDate().get(GregorianCalendar.YEAR) == listXOR2.get(k).getDate().get(GregorianCalendar.YEAR) &&
									listXOR.get(j).getShiftType() == listXOR2.get(k).getShiftType()) throw new IOException("Este doctor ya tiene una restriccion XOR apuntando a algun turno de la que quieres poner");			
							}
						}
					}
					if (alres.get(z).getTipo().equals("MAX_Turnos_Rango")) {
						MAX_Turnos_Rango N = (MAX_Turnos_Rango)alres.get(z);
						int firstDay = N.getFechaIni().get(GregorianCalendar.DAY_OF_YEAR);
						int lastDay = N.getFechaFin().get(GregorianCalendar.DAY_OF_YEAR);
						for (int j = 0; j < listXOR.size(); ++j) {
							GregorianCalendar t = listXOR.get(j).getDate();
							int diaXor = t.get(GregorianCalendar.DAY_OF_YEAR);
							if (diaXor <= lastDay && diaXor >= firstDay) throw new IOException("Este Doctor ya tiene una restriccion MAX_Turnos_Rango con una fecha de la XOR"); 
						}
					}
				}
				Restriccion res = new XOR(idRes, listXOR);
				boolean c = Doctors.get(i).addRestriction(res);
				if (!c) throw new IOException("Ya existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
		
	}
	
	/** 
	 * Metodo que elimina una restriccion del Doctor
	 * @param idRes: Identificador de la restriccion
	 * 		  idDoc: Identificador del Doctor
	 * @throws Exception
	 */
	public void eliminarRestriccion(int idRes, int idDoc) throws IOException {
		if(idDoc < 0 || idRes < 0) throw new IOException("Valor del identificador incorrecto");
		boolean trobat = false;
		for (int i = 0; i < Doctors.size(); ++i) {
			if (Doctors.get(i).getId() == idDoc) {
				trobat = true;
				boolean c = Doctors.get(i).deleteRestriction(idRes);
				if (!c) throw new IOException("No existe una restriccion con esta Id");	
			}
			if (trobat) break;
		}
		if (!trobat) throw new IOException("No existe un Doctor con esta Id");
	}

}