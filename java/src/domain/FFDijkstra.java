package domain; 
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Stack;
import java.io.IOException;

/**
* @author Alex Morral 
*/


class arcP {
    public double coste;
    public int id;
}

public class FFDijkstra<T> extends FordFulkerson<T> {
	
	
	/**
	 * Creadora de la clase
	 * @param nodeInicial
	 * @param nodeDesti
	 * @param graf
	 * @return Una instancia de FFDijkstra
	 */
	public FFDijkstra(Integer nodeInicial, Integer nodeDesti, Graf<T> graf) {
		super(nodeInicial, nodeDesti, graf);
 	}
		
	/**
	 * @Pre: g es un grafo dirigido no vacío con pesos en las aristas.
	 * @param Grafo graf a aplicar el algoritmo.
	 * @return ArrayList con los id's de los nodos del camino de menor peso. Si no hay camino, ArrayList vacio.
	*/
	@Override
	public ArrayList<Integer> dameCamino(Graf<T> graf) throws IOException {	
		//dist[u] : la longitud del camino más corto desde s hasta t
		//pred[u] predecesor de u en este camino
		try {
			int size = graf.getNSize();
			ArrayList<Integer> camino = new ArrayList<Integer>();
			int[] pred = new int[size];
			for(int k = 0; k < size; k++){
		        pred[k] = -1;
			}
			double[] dist = new double[size];							
			for(int k = 0; k < size; k++){
			        dist[k] = Double.POSITIVE_INFINITY;
			}
			
			PriorityQueue<arcP> vertexQueue = new PriorityQueue<arcP>(1,new Comparator<arcP>() 
		            { public int compare(arcP p, arcP q)
		            {
			            if(p.coste > q.coste) return 1;
			            if(p.coste < q.coste) return -1;
		                return 0;
		            }
		            } ); 
			arcP p = new arcP();
			p.id = s;
			p.coste = 0.0;
			dist[s] = 0.0;
			vertexQueue.add(p); 												
			while (!vertexQueue.isEmpty()) {
				p = vertexQueue.poll();		 									
				ArrayList<Integer> neighbours = graf.getOutNodes(p.id); 			
				for(int neighbour : neighbours){								
					int arista = graf.getIDAresta(p.id, neighbour);				
					//System.out.printf("Nodo Princ: %d , Nodo vecino: %d, ID Arista: %d\n", p.id, neighbour, arista);
					int capacidadArista = graf.getCapacidadAresta(arista);
					double costeArista = graf.getCosteAresta(arista);
					if(capacidadArista > 0) {
						double coste = dist[p.id]+costeArista;
						if(coste < dist[neighbour]){
							dist[neighbour] = coste;
							pred[neighbour] = p.id;
							arcP q = new arcP();
							q.id = neighbour;
							q.coste = coste;
							vertexQueue.add(q);
						}
					}
				}
			
			}
			
			Stack<Integer> cam = new Stack<Integer>();
			int sig = t;
			if(size > 1 && pred[sig] != -1) { 
				while(sig != s) {
					cam.push(sig);
					sig = pred[sig];
				}
				cam.push(s);
				while(!cam.isEmpty()) {
					camino.add(cam.pop());
				}
			}
			return camino;
		}
		catch (IOException e) {
		    throw new IOException(e);
		}
	}
		

}