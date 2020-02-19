package domain;


import java.io.IOException;
import java.util.ArrayList;


//@autor Toni Mart�nez

public class FordFulkerson<T> {	
	
	
	protected Graf<T> g;
	protected int s;
	protected int t;
	protected int maxFlow;
	
	//Afegides per donar informaci� extra. Del MaxFlow calculat per cada cam� el fluxe enviat
	//protected ArrayList<Pair<Integer,ArrayList<Integer>>> caminos;
	
	
	public FordFulkerson(int nodeInicial, int nodeDesti, Graf<T> graf){
		this.s = nodeInicial;
		this.t = nodeDesti;
		this.g = graf;
		this.maxFlow = 0;
		//caminos = new ArrayList<Pair<Integer,ArrayList<Integer>>>();
		
	}
	
	public FordFulkerson(){
		this.maxFlow = 0;
	//	caminos = new ArrayList<Pair<Integer,ArrayList<Integer>>>();
	}
	
	//Variable d'�s intern pel m�tode dameCamino amb la implementaci� DFS
	private boolean[] visited;
		
	public final Graf<T> findMaxFlow(Graf<T> graf, int init, int desti) throws IOException{
		this.g = graf;
		this.s = init;
		this.t = desti;
		//caminos = new ArrayList<Pair<Integer,ArrayList<Integer>>>();
		ArrayList<Integer> camino= new ArrayList<Integer>();
		do{
			visited = new boolean[graf.getNSize()];
			for (int i = 0; i < visited.length; i++) {
				if(i==0){
					visited[i]=true;
				}else{
					visited[i]=false;
				}
			}
			//S'obt� el cam� d'acord al graf en funci� de l'algoritme (DFS,BFS o Dijkstra).
			camino = dameCamino(graf);
			this.s = init;		//Es necesari perque el metode dameCamino es recursiu i utilitza aquesta variable.
			if(camino != null && camino.size()>0){
				//Es computa el graf residual. S'actualitzen les capacitats i el flux de cada aresta. 
				//S'afegeixen les "retro-arestes".
				computaGrafResidual(camino,graf);
			}
			
		}while(camino != null && camino.size()>0);
		
		//Es calcula el maxFlow
		this.maxFlow = 0;
		ArrayList<Integer> outs = graf.getOutNodes(init);
		for (Integer idNodeOut : outs) {
			maxFlow = maxFlow + graf.getFlujoAresta(graf.getIDAresta(init, idNodeOut));
		}
		return graf;
	}
	
	//M�tode que retorna un String amb una representaci� de la matriu 
	//de FLUX/CAPACITAT de cada aresta del graf
	public String printMatrix(Graf<T> graf) throws IOException{
		String toRet = "";
		int nodeSize = graf.getNSize();
		int idAresta;
		for (int i = 0; i < nodeSize; i++) {
			for(int j = 0; j < nodeSize; j++) {
				if(i!=j){
					idAresta = graf.getIDAresta(i, j);
				}else{
					idAresta=-1;
				}
				if(idAresta!=-1){
					toRet += "\t"+String.format("%02d", graf.getFlujoAresta(idAresta))+"/"+String.format("%02d",graf.getCapacidadAresta(idAresta));
				}else{
					toRet += "\t"+"00/00";
				}
			}
			toRet += ("\n");
		}		
		return toRet;
	}
	
	
	


	//Dado un camino entre el punto init y desti actualiza la variable graf con
	//los nuevos flujos, capacidades y las retroaristas necesarias.
	private void computaGrafResidual(ArrayList<Integer> camino, Graf<T> graf) throws IOException {
		int caminoLength, i, maxflowCamino, j, idArestaIJ, idArestaJI;
		caminoLength = camino.size();
		//Calculamos el m�ximo flujo que puede pasar por el camino.
		maxflowCamino = computaMaxFlowCamino(camino, graf);
		
		//A�adimos el camino y su maxFlow en la variable caminos
		//caminos.add(new Pair<Integer, ArrayList<Integer>>(maxflowCamino,camino));
		
		//Para cada arista del camino
		for (int k = 0; k < caminoLength -1; k++) {
			i= camino.get(k);
			j= camino.get(k+1);
			
			//La arista
			idArestaIJ = graf.getIDAresta(i, j);
			//La retro-arista
			idArestaJI = graf.getIDAresta(j, i);
			//Si no existe la retro-arista, entonces la creamos.
			if(idArestaJI==-1){
				graf.conectarNodes(j, i, 0, 0.0);
				idArestaJI = graf.getIDAresta(j, i);
			}
			
			//Se actualizan los coeficientes de capacidades y flujos de la aresta y la retro-aresta
			graf.substituirAresta(idArestaIJ, graf.getCapacidadAresta(idArestaIJ)-maxflowCamino, graf.getCosteAresta(idArestaIJ));
			graf.setFlujoAresta(idArestaIJ, graf.getFlujoAresta(idArestaIJ)+maxflowCamino);
			graf.substituirAresta(idArestaJI, graf.getCapacidadAresta(idArestaJI)+maxflowCamino, graf.getCosteAresta(idArestaJI));
			graf.setFlujoAresta(idArestaJI, graf.getFlujoAresta(idArestaJI)-maxflowCamino);

		}
	}






	//Dado un camino que se pasa por parametro, se calcula el maximo flujo que puede pasar por sus nodos teniendo en cuenta 
	//capacidades y flujos ya existentes en el grafo que se pasa por parametro.
	private int computaMaxFlowCamino(ArrayList<Integer> camino, Graf<T> graf) throws IOException {
		int maxFlowCamino = Integer.MAX_VALUE;
		for (int i = 0; i < camino.size()-1; i++) {
			int idNode = camino.get(i);
			int idNodeSiguiente = camino.get(i+1);
			maxFlowCamino = Math.min(graf.getCapacidadAresta(graf.getIDAresta(idNode,idNodeSiguiente)), maxFlowCamino);
		}
		return maxFlowCamino;
	}


	//M�todo a sobreescribir por las clases que heredan de FordFoulkerson
	//Encuentra un camino siguiendo un algoritmo (DFS en el caso de la clase FordFoulkerson)
	//teniendo en cuenta las capacidades y los flujos del grafo que se le pasa por parametro.
	//Esta implementaci�n no tiene en cuenta los costes.
	public ArrayList<Integer> dameCamino(Graf<T> graf) throws IOException {		
		//Encuentra, de todos los nodos que no estan saturados (Capacidad > 0) del grafo,
		//un camino haciendo una busqueda dfs de forma recursiva.
		ArrayList<Integer> toReturn = null;
		if(s == t){
			//S'ha trobat el sumidero. Come�a el backtracking.
			toReturn = new ArrayList<Integer>();
			toReturn.add(s);
			return toReturn;
		}
		
		ArrayList<Integer> helper = null; 
		ArrayList<Integer> outs = graf.getOutNodes(s);
		int i = 0;
		int initBUp;
		int idAresta;
		boolean found = false;
		while (i < outs.size() && !found) {
			idAresta = graf.getIDAresta(s, outs.get(i));
			if(graf.getCapacidadAresta(idAresta)>0){
				//El node no est� saturat (Capacitat de la aresta > 0)
				initBUp = s;
				s = outs.get(i);
				if(!visited[s]){
					visited[s]=true;
					helper = dameCamino(graf);
				}
				s = initBUp;
				if(helper != null){
					//Es fa backtracking nom�s si s'ha trobat el sumidero. Es van afegint les variables
					//al arrayList a retornar de manera ordenada. 
					found = true;
					toReturn = new ArrayList<Integer>();
					toReturn.add(s);
					toReturn.addAll(helper);
				}
				
				helper = null;
			}
			i++;
		}
		
		//Si no s'ha trobat el sumidero la variable toReturn ser� null
		return toReturn;
	}


	public int getMaxFlow() {
		return maxFlow;
	}
	
	public Graf<T> getGrafInicial() {
		return g;
	}
	
	public int getNodeDesti(){
		return t;
	};
	public int setNodeDesti(int desti){
		t=desti;
		return t;
	};
	public int getNodeOrigen(){
		return t;
	};
	public int setNodeOrigen(int origen){
		s = origen;
		return s;
	};
	
	public void setGrafInicial(Graf<T> gInicial){
		g = gInicial;
	}

//	public ArrayList<Pair<Integer, ArrayList<Integer>>> getCaminos() {
//		return caminos;
//	}
	
	

}
