package domain;

import domain.Nodo;
import domain.Graf;
import domain.Nodo;
import model.Asignaciones;
import domain.nodoDoctor;
import domain.nodoTurno;

import java.io.IOException;
import java.util.ArrayList;

/**
*
* @author Axel Pelaez
*/

class listAndSalary{
    public ArrayList<nodoTurno> listaTurnos;
    public double sueldoTotal;
}

public class InterpretarResultado {

	private Graf<Nodo> graf;
	private int idFuente; 
	private int idSumidero;
	
	private Asignaciones mapSol;
	private ArrayList<nodoTurno> turnosSinSol;
	private ArrayList<Integer> numSinSol;
	private boolean sol;
	
	InterpretarResultado(Graf<Nodo> g, int NodoF, int NodoS){
		graf = g;
		idFuente = NodoF;
		idSumidero = NodoS;
		mapSol = new Asignaciones();
		turnosSinSol = new  ArrayList<nodoTurno>();
		sol = false;
		numSinSol  = new  ArrayList<Integer>();
	}
	
	InterpretarResultado(Graf<Nodo> g, Nodo NodoF, Nodo NodoS) throws IOException{
		graf = g;
		idFuente = g.getNodeId(NodoF);
		idSumidero = g.getNodeId(NodoS);	
		mapSol = new Asignaciones();
		turnosSinSol = new  ArrayList<nodoTurno>();
		sol = false;
		numSinSol  = new  ArrayList<Integer>();
		}
	
	
	//Metodos Publicos
	public Asignaciones getMapSol(){
		return mapSol;
	}
	
	public ArrayList<nodoTurno> getTurnosSinSol() {
		return turnosSinSol;
		
	}
	
	public ArrayList<Integer> getnumSinSol(){
		return numSinSol;
	}
	
	public boolean hasSolution() {
		return sol;
	}
	
	//Metodos Privados 
	
	/*
	 * PRE: Hay solucion.
	 * 
	 * Vamos recoriendo el grafo desde cada Doctor al sumidero,
	 * almacenando los turnos qe trabaja y sumando su sueldo por turno. 
	 * 
	 */
	private void interpretarDFS(int idNodo, ArrayList<nodoTurno> turnos, int idDoctor) throws IOException{
		if(idNodo != idSumidero){
			ArrayList<Integer> vecinos = graf.getOutNodes(idNodo);
		for(int vecino : vecinos){
			//CONTROL DE FLUJO
			int idArista = graf.getIDAresta(idNodo, vecino);
			if(graf.getFlujoAresta(idArista) > 0){
				Nodo veci = graf.getNode(vecino);
				//Si es tipo turno lo guardamos y sumamos su sueldo
				if(veci.getTipo().equals("Turno")){
					turnos.add((nodoTurno)(veci));
			
					mapSol.SumaSueldo(idDoctor,graf.getCosteAresta(idArista));
					
					}
					
				//llamada recursiva
				interpretarDFS(vecino, turnos,idDoctor);
				}
				
				
			}
			}
			
			
		}
		
	
	
	
	/*
	 * Comprueba que hay solucion, si no la hay,
	 * mientras va comprobando guarda los turnos que no han sido completamente cubiertos
	 * 
	 */
	private void haySolucion() throws IOException{
		int flujoTurnoASumidero = 0;
	
		ArrayList<Integer> vecinosSumidero = graf.getInNodes(idSumidero);
		for(int vecino:vecinosSumidero){	
			int idArista = graf.getIDAresta(vecino, idSumidero);
			flujoTurnoASumidero += graf.getCapacidadAresta(idArista);
			

			if(graf.getCapacidadAresta(idArista) > 0){
				turnosSinSol.add((nodoTurno)graf.getNode(vecino));
				numSinSol.add(graf.getCapacidadAresta(idArista));
			}


		}
		
		if(flujoTurnoASumidero > 0) sol = false;
		else sol = true;
	}
	
	public void  InterpretarGrafo() throws IOException{
		
		haySolucion();

		//Obtener id de los vecinos de la fuente
		//Los vecinos de la fuente siempre son doctores
		ArrayList<Integer> vecinos = graf.getOutNodes(idFuente);
		
		for(int vecino:vecinos){

			int idArista = graf.getIDAresta(idFuente, vecino);
			if(graf.getFlujoAresta(idArista) > 0){
				ArrayList<nodoTurno> turnos = new ArrayList<nodoTurno>();
				double sueldo = 0;
				
				nodoDoctor nD = (nodoDoctor)graf.getNode(vecino);
				int idDoc = nD.getIdDoc();
				
				interpretarDFS(vecino,turnos,idDoc);
				
				mapSol.addTurnos(idDoc,turnos);
					
					
				}
			
		}
		 
		
	
	}
	

		
	}

