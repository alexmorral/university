package domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import data.CtrlDatosFichero;
/**
 * Representa una solucion
 * @author oscar
 *
 */
public class Solutions {

	private int id;
	private String Name;
	private ArrayList<String> coment;
	private HashMap<Integer, ArrayList<String>> asignDoc;
	private HashMap<Integer, Double> sueldos;
	private ArrayList<String> tSinSol;
	
	/**
	 * 
	 */
	public Solutions() {
		
	}
	
	/**
	 * 
	 * @param idSol id de la solucion
	 * @param comentari comentario de la solucion
	 * @param asign turnos asignados a cada doctor
	 * @param sinsol turnos no asignados de la solucion
	 * @param suel sueldo que cobrara el doctor con esta solucion
	 */
	public Solutions(int idSol,String name,ArrayList<String> comentari ,HashMap<Integer, ArrayList<String>> asign, ArrayList<String> sinsol, HashMap<Integer, Double> suel){
		Name = new String();
		coment = new ArrayList<String>();
		asignDoc = new HashMap<Integer, ArrayList<String>>();
		sueldos = new HashMap<Integer, Double> ();
		tSinSol = new ArrayList<String> ();
		id=idSol;
		Name=name;
		for(int i=0;i<comentari.size();++i){
			coment.add(comentari.get(i));
		}
		Iterator<Integer> it = asign.keySet().iterator();
		while(it.hasNext()){
			 Integer doc = it.next();
			 ArrayList<String> assigs = asign.get(doc);
			 asignDoc.put(doc,assigs);
			 sueldos.put(doc,suel.get(doc));
		}
		for(int i =0; i<sinsol.size();++i){
			tSinSol.add(sinsol.get(i));
		}
	}
	
	/**
	 * 
	 * @return el id de la solucion
	 */
	public int getId(){
		return id;
	}
	/**
	 * 
	 * @return el nombre de la solucion
	 */
	public String getName(){
		return Name;
	}
	
	/**
	 * 
	 * @return el comentario de la solucion
	 */
	public ArrayList<String> getComent(){
		return coment;
	}
	/**
	 * 
	 * @return las asignaciones de cada doctor
	 */
	public HashMap<Integer, ArrayList<String>> getDatesAsigned(){
		return asignDoc;
	}
	
	/**
	 * 
	 * @return los turnos no asignados de la solucion
	 */
	public ArrayList<String> getTurnosSinSol () {
		return tSinSol;
	}
	
	/**
	 * 
	 * @return el sueldo que cobraran los doctores con esta solucion
	 */
	public HashMap<Integer, Double> getSueldoAsigned(){
		return sueldos;
	}
	
	/**
	 * 
	 * @param asign los turnos assignados a cada doctor
	 */
	public void setDatesAsigned (HashMap<Integer, ArrayList<String>> asign){
		Iterator<Integer> it = asign.keySet().iterator();
		while(it.hasNext()){
			 Integer doc = it.next();
			 ArrayList<String> assigs = asign.get(doc);
			 asignDoc.put(doc,assigs);
			 
		}
	}
	
	public void setName (String name){
		Name = name;
	}
	
	public void setSueldos (HashMap<Integer, Double>suel){
		Iterator<Integer> it = suel.keySet().iterator();
		while(it.hasNext()){
			 Integer doc = it.next();
			 sueldos.put(doc,suel.get(doc));
		}
	}
	
	/**
	 * 
	 * @param turns los turnos no asignados
	 */
	public void settSinSol(ArrayList<String> turns){
		tSinSol.clear();
		for(int i =0; i<turns.size();++i){
			tSinSol.add(turns.get(i));
		}
	}
	/**
	 * 
	 * @param comentary el comentario de la solucion
	 */
	public void setComent(ArrayList<String> comentary){
		coment.clear();
		for(int i =0; i<comentary.size();++i){
			coment.add(comentary.get(i));
		}
	}
	/**
	 * 
	 * @param id el id nuevo de esta solucion
	 */
	public void setId(int id) {
		this.id=id;
	}
	
	/**
	 * 
	 * @param id el id del hospital que guarda la solucion
	 * @param idSol id de la solucion
	 * @throws IOException  errores de fichero
	 */
	public void saveSol(int id, int idSol) throws IOException{
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		ArrayList<String> sol = new ArrayList<String>();
		ArrayList<String> noSol = new ArrayList<String>();
		
		HashMap<Integer, ArrayList<String>> asignDocTemp = getDatesAsigned();
		Iterator<Integer> it = asignDocTemp.keySet().iterator();
		
		sol.add(Integer.toString(idSol));
		 sol.add(Name.replace(" ","%"));
		 String comment = new String("");
		 for(String t : coment) {
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
		  sol.add(Double.toString((sueldos).get(doc))); //sueldo
		}
		noSol.add(Integer.toString(idSol));
		ArrayList<String> tSinSolTemp = tSinSol;
		for(String s : tSinSolTemp){
			noSol.add(s);
		}
		inOut.saveDataSol(idSol, sol, noSol, id);
	}
	
	/**
	 * 
	 * @param id id del hospital donde esta la solucion
	 * @param idsol id de la solucion
	 * @throws IOException errors de fichero
	 */
	public void getSol(int id, int idsol) throws IOException{
		CtrlDatosFichero inOut = new CtrlDatosFichero();
		ArrayList<String> sol = inOut.getDataSol(id,idsol);
		ArrayList<String> noSol = inOut.getDataNoSol(id,idsol);
		if(!sol.isEmpty()){
			int i = 0;
			int idSol = Integer.parseInt(sol.get(i)); //idsol
			this.id=idSol;
			++i;
			int doc=Integer.parseInt(sol.get(i)); //iddoc
			ArrayList<String> asigsDoc = new ArrayList<String>();
			++i;
			int size = Integer.parseInt(sol.get(i));//numFechas
			for(int j = 0; j<size;++j){
				++i;
				asigsDoc.add(sol.get(i));
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
		}
			tSinSol.clear();
		if(!noSol.isEmpty()){
			for(int j=0;j<noSol.size();++j){
				tSinSol.add(noSol.get(j));//llenamos tSinSol
			}
		}
	}
	
	
	
	
	
}//ficlas
