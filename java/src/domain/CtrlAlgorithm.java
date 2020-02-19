package domain;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import data.CtrlDatosFichero;
import model.Doctor;
import model.Hospital;
import model.Asignaciones;

/**
 * 
 * @author Alex Morral
 *
 */

public class CtrlAlgorithm {
	private Graf<Nodo> g;
	private Graf<Nodo> gResidual;
	private Hospital hosp;
	private Asignaciones resMap;
	private ArrayList<nodoTurno> turnosSinSol;
	private ArrayList<Integer> numSinSol;
	private HashMap<Integer,Solutions> sols;
	private int idActual;
	
	private HashMap<Integer, ArrayList<String>> asignDoc;
	private ArrayList<String> tSinSol;
	private HashMap<Integer, Double> sueldos;
	
	//Creadora por defecto
	public CtrlAlgorithm(Hospital h) {
		hosp = h;
		gResidual = null;
		resMap = new Asignaciones();
		turnosSinSol = new ArrayList<nodoTurno>();
		numSinSol = new ArrayList<Integer>();
		idActual = 0;
		sols = new HashMap<Integer,Solutions>();
		asignDoc = new HashMap<Integer, ArrayList<String>>();
		tSinSol = new ArrayList<String>();
		sueldos = new HashMap<Integer, Double>();
	}
	
	
	//Funcion que genera el grafo del hospital
	public void generateGraf() throws IOException{
		CtrlGrafo CtrlGraf = new CtrlGrafo();
		try	{
			CtrlGraf.fillGrafo(hosp);
		} catch(IOException e) {
			System.out.println(e);
		}
		g = CtrlGraf.getGraf();
		
	}
	
	//Funcion que llama al algoritmo Max Flow con DFS
	public void findMaxFlowFulk(int in, int out) {
		FordFulkerson<Nodo> ffulk = new FordFulkerson<Nodo>(in,out,g);
		try {
			gResidual = new Graf<Nodo>();
			gResidual = ffulk.findMaxFlow(g, in, out);
			System.out.println("MaxFlow: " + ffulk.getMaxFlow());
		} catch(IOException e){
			System.out.println(e);
		}
		
	}
	//Funcion que llama al algoritmo Max Flow con BFS
	public void findMaxFlowEk(int in, int out) {
		EdmondsKarp<Nodo> ffulk = new EdmondsKarp<Nodo>(in,out,g); 
		try {
			gResidual = new Graf<Nodo>();
			gResidual = ffulk.findMaxFlow(g, in, out);
			System.out.println("MaxFlow: " + ffulk.getMaxFlow());
		} catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	//Funcion que llama al algoritmo Max Flow con Dijkstra
	public void findMaxFlowDijk(int in, int out) {
		FFDijkstra<Nodo> ffulk = new FFDijkstra<Nodo>(in,out,g);
		try {
			gResidual = new Graf<Nodo>();
			gResidual = ffulk.findMaxFlow(g, in, out);
			System.out.println("MaxFlow: " + ffulk.getMaxFlow());
		} catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	//Funcion que interpreta el grafo residual
	public boolean findSolution(int in, int out) {
		InterpretarResultado result = new InterpretarResultado(gResidual, in, out);
		try {
			result.InterpretarGrafo();
			resMap = result.getMapSol();
			turnosSinSol = result.getTurnosSinSol();
			numSinSol = result.getnumSinSol();
		} catch (IOException e) {
			System.out.println(e);
		}
		return result.hasSolution();
	}
	
	//Funcion que retorna el grafo
	public Graf<Nodo> getGraf() {
		return gResidual;
	}
	
	//Funcion devuelve las fechas y turnos asignados de un doctor con id = id
	public void generateDatesAsigned(){
		asignDoc = new HashMap<Integer, ArrayList<String>>();
		ArrayList<Doctor> docs = hosp.getDoctors();
		for(Doctor d : docs) {
			ArrayList<String> asign = new ArrayList<String>();
			ArrayList<String> fechasAsignadas = resMap.getFechasAsignaciones(d.getId());
			ArrayList<String> turnosAsignados = resMap.getTipoTurnoAsignaciones(d.getId());
			for(int i = 0; i < fechasAsignadas.size(); ++i) {
				asign.add(fechasAsignadas.get(i) + " " + turnosAsignados.get(i));
			}
			asignDoc.put(d.getId(), asign);
		}
	}
	
	
	//Funcion devuelve el sueldo de un doctor con id = id
	public void generateSueldoAsigned(){
		sueldos = new HashMap<Integer, Double>();
		ArrayList<Doctor> docs = hosp.getDoctors();
		for(Doctor d : docs) {
			sueldos.put(d.getId(), resMap.getSueldoTotal(d.getId()));
		}
	}
	
	
	public void generateTurnosSinSol(){
		tSinSol = new ArrayList<String>();
		String fecha;
		for(int i = 0; i < turnosSinSol.size(); ++i) {
			nodoTurno n = turnosSinSol.get(i);
			GregorianCalendar c1 = n.getFecha();
			fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(c1.getTime());
			tSinSol.add(fecha + " " + n.getTipoTurno() + " (" + numSinSol.get(i) + ")");
			
		}
	}
	
	public HashMap<Integer, ArrayList<String>> getDatesAsigned(){
		return asignDoc;
	}
	
	
	public ArrayList<String> getTurnosSinSol () {
		return tSinSol;
	}
	
	
	public HashMap<Integer, Double> getSueldoAsigned(){
		return sueldos;
	}
	
	/** Anyade una solucion a sols
	 * @param idSol identificador de la solucion
	 * @param name nombre de la solucion
	 * @param comentari comentario de la solucion
	 * @param asign turnos asignados a cada doctor
	 * @param sinsol turnos sin solucion
	 * @param suel sueldo de los doctores con todas sus asignaciones
	 */
	public void makeSol(int idSol,String name,ArrayList<String> comentari){
		Solutions sol = new Solutions(idSol,name,comentari, asignDoc,tSinSol,sueldos);
		sols.put(idSol,sol);
	}
	
	/**  carga una solucion de sols a ctrAlgoritm
	 * @param idsol carga la solucion en CtrlAlgoritm
	 */
	public void cargarSol(int idsol){
		Solutions sol = sols.get(idsol);
		asignDoc=sol.getDatesAsigned();
		tSinSol=sol.getTurnosSinSol();
		sueldos=sol.getSueldoAsigned();
		idActual=idsol;
	}
	
	public void getAllSols() throws IOException{
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		int nums=inOut.howManySolutions(hosp.getId());
		for(int i = 0;i<nums;++i){
			if(inOut.existsSol(hosp.getId(), i)) {
				getSol(hosp.getId(),i);
			}
		}
	}
	
	public String getNameSol(int id) {
		return (sols.get(id)).getName();
	}
	public String getCommentSol(int id) {
		ArrayList<String> comment = (sols.get(id)).getComent();
		String comentario = new String();
		for(String t : comment){
			comentario = comentario +" "+ t;
		}
		return comentario;
	}
	
	/**
	 * @return la id de la solucion que estamos tratando en ctrAlgoritm
	 */
	public int getIdActual(){
		return idActual;
	}
	
	/**
	 * @return los ids de las soluciones ya almacenadas
	 */
	public ArrayList<String> getAllInfoSolutions(){
		ArrayList<String> idssols = new ArrayList<String>();
		Set<Integer> set = sols.keySet();
		for(Integer i : set) {
			idssols.add(i+" - " +(sols.get(i)).getName());
		}
		return idssols;
	}
	
	
	
	
	public void setNameSol(int idsol, String name){
		sols.get(idsol).setName(name);
	}
	
	public void setComent(int idsol, ArrayList<String> coment){
		sols.get(idsol).setComent(coment);
	}
	
	public void setAssigs(int idsol,HashMap<Integer, ArrayList<String>> asign){
		sols.get(idsol).setDatesAsigned(asign);
	}
	
	public void setsueldos(int idsol,HashMap<Integer, Double> sueldo){
		sols.get(idsol).setSueldos(sueldo);
	}
	
	public void setTsinsol(int idsol,ArrayList<String> tsin) {
		sols.get(idsol).settSinSol(tsin);
	}
	
	public int getFIDS(){
		if(sols.isEmpty())return 0;
		else {
			for(int i =0; i<sols.size();++i){
				if(!sols.containsKey(i)) return i;
			}
		}
		return sols.size();
	}
	
	
	public void getSol(int id,int idSol) throws IOException{
		idActual=idSol;
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		ArrayList<String> sol = inOut.getDataSol(id,idSol);
		ArrayList<String> noSol = inOut.getDataNoSol(id,idSol);
		String name = "Noname";
		String comment = "";
		ArrayList<String> commentArray = new ArrayList<String>();
		if(!sol.isEmpty()){
			int i = 0;
			sol.get(i);//idSol
			++i;
			name = sol.get(i);//nom
			name = name.replace("%", " ");
			++i;
			comment = sol.get(i); //coment
			++i;
			while(i < sol.size()){
					int doc=Integer.parseInt(sol.get(i)); //iddoc
					ArrayList<String> asigsDoc = new ArrayList<String>();
					++i;
					int size = Integer.parseInt(sol.get(i));//numFechas
					for(int j = 0; j<size;++j){
						++i;
						asigsDoc.add(sol.get(i)+" "+sol.get(++i));
					}
					if(asignDoc.containsKey(doc)){
						asignDoc.remove(doc);
					}
					asignDoc.put(doc, asigsDoc); //ponemos las fechas en asigndoc
					if(sueldos.containsKey(doc)){
						sueldos.remove(doc);
					}
					++i;
					sueldos.put(doc,Double.parseDouble(sol.get(i))); //ponemos el sueldo en sueldos
					++i;
				}
			}
			tSinSol.clear();
		if(!noSol.isEmpty()){
			for(int j=1;j<noSol.size();j=j+3){
				int i = j;
				tSinSol.add(noSol.get(i)+" "+ noSol.get(i+1)+" "+noSol.get(i+2));//llenamos tSinSol
			}
		}
		String[] com = comment.split("%");
		for(String t : com) {
			commentArray.add(t);
		}
		makeSol(idSol, name, commentArray);
	}
	
	public void saveSol(int id, int idSol) throws IOException{
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		ArrayList<String> sol = new ArrayList<String>();
		ArrayList<String> noSol = new ArrayList<String>();
		
		HashMap<Integer, ArrayList<String>> asignDocTemp = (sols.get(idSol)).getDatesAsigned();
		Iterator<Integer> it = asignDocTemp.keySet().iterator();
		
		sol.add(Integer.toString(idSol));
		 sol.add((sols.get(idSol)).getName().replace(" ","%"));
		 String comment = new String("");
		 for(String t : sols.get(idSol).getComent()) {
			 if (!t.isEmpty()) {
				 comment = comment+"%"+t;
			 }
		 }
		 if(!comment.equals("")) sol.add(comment);
		 else {sol.add("-");}
		 
		while(it.hasNext()){
		 Integer doc = it.next();
		 ArrayList<String> assigs = asignDocTemp.get(doc);
		 	sol.add(Integer.toString(doc)); //iddoc
		 	sol.add(Integer.toString(assigs.size())); //numFechas
		  for(int i=0;i<assigs.size();++i){
			 sol.add(assigs.get(i)); //fechas
		  }
		  sol.add(Double.toString(((sols.get(idSol)).getSueldoAsigned()).get(doc))); //sueldo
		}
		noSol.add(Integer.toString(idSol));
		ArrayList<String> tSinSolTemp = tSinSol;
		for(String s : tSinSolTemp){
			noSol.add(s);
		}
		inOut.saveDataSol(idSol,sol, noSol, id);
	}
	
	public void deleteSol(int id, int idSol) throws IOException{
		if(sols.containsKey(idSol)) sols.remove(idSol);
		saveAllSOlutions(id);
	}
	
	/**guarda todas las soluciones
	 * 
	 * @param id el id del hospital
	 * @throws IOException errores de fichero
	 */
	public void saveAllSOlutions(int id) throws IOException{
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		inOut.removePart(id, ".S");
		Set<Integer> set = sols.keySet();
		for(Integer i : set) {
			Solutions sol = sols.get(i);
			sol.saveSol(id, i);
		}
	}
	
	public void addTurnToDoctor(int idDoc, String turnoConDoctors) throws IOException{
		String[] parts = turnoConDoctors.split(" ");
		String turno = parts[0]+" "+parts[1];
		for(String t : asignDoc.get(idDoc)) {
			if(t.equals(turno)) throw new IOException("Este doctor ya trabaja este turno");
		}
		/** EDIT SUELDO FROM sueldos*/
		String tipoT = parts[1];
		double sueldoBase = (hosp.getDoctor(idDoc)).getSalaryTurn();
		double sueldoASumar = 0;
		switch(tipoT) {
			case "manana":
				sueldoASumar = sueldoBase*hosp.getFactorM();
				break;
			case "tarde":
				sueldoASumar = sueldoBase*hosp.getFactorT();
				break;
			case "noche":
				sueldoASumar = sueldoBase*hosp.getFactorN();
				break;
		}
		double oldSueldo = sueldos.get(idDoc);
		sueldos.put(idDoc, oldSueldo+sueldoASumar);
		/** ADD TO asignDoc*/
		asignDoc.get(idDoc).add(turno);
		/** REMOVE/EDIT FROM tSinSol*/
		boolean modified = false;	
		for(int i = 0; i < tSinSol.size() && !modified; ++i) {
			String t = tSinSol.get(i);
			if(t.equals(turnoConDoctors)) {
				modified = true;
				String[] parts2 = parts[2].split("[()]");
				int numDocs = Integer.parseInt(parts2[1]);
				tSinSol.remove(turnoConDoctors);
				if(numDocs > 1) {
					--numDocs;
					tSinSol.add(turno+" "+"("+numDocs+")");
				}
			}
		}
	}
	public void deleteTurnFromDoctor(int idDoc, String turno) throws IOException{
		ArrayList<String> asignacionesDoc = asignDoc.get(idDoc);
		turno = turno.replaceAll("-", "");
		turno = turno.replaceFirst(" ", "");
		String[] split = turno.split(" ");
		/** EDIT SUELDO FROM sueldos*/
		String tipoT = split[1];
		double sueldoBase = (hosp.getDoctor(idDoc)).getSalaryTurn();
		double sueldoADescontar = 0;
		switch(tipoT) {
			case "manana":
				sueldoADescontar = sueldoBase*hosp.getFactorM();
				break;
			case "tarde":
				sueldoADescontar = sueldoBase*hosp.getFactorT();
				break;
			case "noche":
				sueldoADescontar = sueldoBase*hosp.getFactorN();
				break;
		}
		double oldSueldo = sueldos.get(idDoc);
		sueldos.put(idDoc, oldSueldo-sueldoADescontar);
		/** REMOVE FROM asignDoc*/
		boolean deleted = false;
		for(int i = 0; i < asignacionesDoc.size() && !deleted; ++i){
			String t = asignacionesDoc.get(i);
			if(t.equals(turno)) {
				asignacionesDoc.remove(t);
				deleted = true;
			}
		}
		/** ADD/EDIT TO tSinSol*/
		boolean modified = false;
		for(int i = 0; i < tSinSol.size() && !modified; ++i) {
			String[] parts = (tSinSol.get(i)).split(" ");
			String t = parts[0]+" "+parts[1];
			if(t.equals(turno)) {
				modified = true;
				String[] parts2 = parts[2].split("[()]");
				int numDocs = Integer.parseInt(parts2[1]);
				tSinSol.remove(tSinSol.get(i));
				++numDocs;
				tSinSol.add(turno+" "+"("+numDocs+")");
				
			}
		}
		if(!modified) {tSinSol.add(turno+" "+"("+1+")");}
	}
	
}
