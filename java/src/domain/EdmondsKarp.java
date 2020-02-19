package domain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.util.Collections;

/**
 *
 * @author Mingjian 
 */
 public class EdmondsKarp<T> extends FordFulkerson<T>
 {

 	// nombre de nodes
 	private ArrayList<ArrayList<Integer>> camins;
 	private HashMap<Integer, Integer> fromNode;

 	/**
 	 *
 	 *
 	 *
 	 */
 	public EdmondsKarp(Integer nodeInicial, Integer nodeDesti, Graf<T> graf)
 	{
 		super(nodeInicial, nodeDesti, graf);
 		this.fromNode = new HashMap<Integer, Integer>();
 		this.camins = new ArrayList<ArrayList<Integer>>();
 	}

 	/**
 	 *
 	 *
 	 *
 	 */
 	public ArrayList<ArrayList<Integer>> retorna_camins(Graf<T> graf, Integer n) throws IOException
 	{
 		if(n < 1) throw new IOException("n ha de ser major que 0");

 		//Graf<T> rGraf = new Graf<T>();
 		//rGraf = graf.getGrafInicial(); 		
 		ArrayList<Integer> cami = new ArrayList<Integer>();
 		
 		Integer sumFlow = 0;
 		while(((cami = dameCamino(graf)).size() != 0)  && sumFlow < n)
 		{
 			
 	
 			Integer mfc = maxFlowCurrent(graf, fromNode);
 	
 			if(mfc <= n)
 			{
 				camins.add(cami);
 			
 				sumFlow += mfc;
 	
 			}
 		}

 		return camins;
 	}

 	/**
 	 *
 	 *
 	 */
 	private Integer maxFlowCurrent(Graf<T> graf, HashMap<Integer, Integer> fromNode) throws IOException
 	{
 		Integer path_flow = Integer.MAX_VALUE;

 		Integer u;
 		Integer idArestaUV;
 		Integer capacitatUV;
 		Double cost;

 		for (Integer v = t; v != s; v = fromNode.get(v))
        {
   
            u = fromNode.get(v);
      
            idArestaUV = graf.getIDAresta(u, v);

            capacitatUV = graf.getCapacidadAresta(idArestaUV);
         
            path_flow = Math.min(path_flow, capacitatUV);
        }
 		


        for (Integer v = t; v != s; v = fromNode.get(v))
        {
        	
            u = fromNode.get(v);
           
            idArestaUV = graf.getIDAresta(u, v);
           
            capacitatUV = graf.getCapacidadAresta(idArestaUV);
      
            capacitatUV -= path_flow;
            cost = graf.getCosteAresta(idArestaUV);
            graf.substituirAresta(idArestaUV, capacitatUV, cost);
  
        }

        maxFlow += path_flow;
        //System.out.println("maxFlow = " + maxFlow + "\n");
        return path_flow;

 	}

 	/**
 	 * 
 	 *
 	 *
 	 *
 	 */
 	@Override
 	public ArrayList<Integer> dameCamino(Graf<T> graf) throws IOException
 	{


 		Integer V = graf.getNSize();
 		//System.out.println("V = " +  V);
 		boolean visited[] = new boolean[V];
 		ArrayList<Integer> path = new ArrayList<Integer>();
 		//create a queue, enqueue the vertex and mark the vertex as visited
 		Queue<Integer> queue = new LinkedList<Integer>();
 		queue.add(s);
 		fromNode.put(s, -1);
 		Boolean found = false; 
 		while(! queue.isEmpty() && (! found))
 		{
 			//remove the vertex at the head
 			Integer head = queue.poll();
 			visited[head] = true;
 
 			if(head == t) found = true;
 			else 
 			{
	 			ArrayList<Integer> outArray = new ArrayList<Integer>();
	 			
	 			outArray = graf.getOutNodes(head);
	 			for(Integer i = 0; i < outArray.size(); ++i)
	 			{
	 	
	 				if(! visited[outArray.get(i)])
	 				{
		 			
		 				Integer idEdge = graf.getIDAresta(head, outArray.get(i));
		 				
		 				Integer flux = graf.getFlujoAresta(idEdge);
		 			
		 				Integer capacity = graf.getCapacidadAresta(idEdge);
		 				
		 				if((capacity > 0))
		 				{
		 					
		 					if(head == s) fromNode.put(outArray.get(i), s);
		 					else fromNode.put(outArray.get(i), head);
		 					
		 					queue.add(outArray.get(i));
		 					
		 				}
		 			}
	 			}
	 		}
 		}

 		if(found)
 		{
 			// empezamos a mirar por tail
 			Integer aux = t;
 		
 			path.add(aux);
	 		while(fromNode.get(aux) != -1)
	 		{
	 			
	 			aux = fromNode.get(aux); // obtiene el hijo
	 			path.add(aux);
	 	
	 		}

	 		Collections.reverse(path);
	 		
	 	}
	 	return path;
 	}

 }