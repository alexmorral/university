package drivers;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import domain.FFDijkstra;
import domain.Graf;

/**
 * 
 * @author Alex Morral
 *
 */

public class DriverDijkstra {
	
	private static void muestraOpciones() {
		System.out.print("¿Que desea hacer?\n\n");
		System.out.print("1: Añadir Nodo\n");
		System.out.print("2: Añadir Arista\n");
		System.out.print("3: Encontrar Camino mínimo (Dijkstra)\n");
		System.out.print("0: Salir\n");
	}

	
	public static void main(String[] args) throws IOException{
		Scanner teclado;
		teclado = new Scanner(System.in);

		int opcion = -1;
		
		Graf<Integer> g = new Graf<Integer>();
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader (isr);
		
	    muestraOpciones();
	    opcion = teclado.nextInt();
	    
	    
		while(opcion != 0) {
	        
			switch(opcion){
				case 1:
					System.out.println("Pon una ID para el nodo");
	    			Integer id_nodo = null;
	    			id_nodo = Integer.parseInt(br.readLine());
					try {
						g.afegirNode(id_nodo);
						System.out.println("Añadido Nodo " + g.getNode(id_nodo));
					}
					catch (IOException e) {
					    throw new IOException(e);
					}
					break;
					
				case 2:
					System.out.println("Pon una ID para el nodo FROM");
					Integer id_from = null;
	    			id_from = Integer.parseInt(br.readLine());
					System.out.println("Pon una ID para el nodo TO");
					Integer id_to = null;
	    			id_to = Integer.parseInt(br.readLine());
					System.out.println("Pon una capacidad para la arista");
					Integer capacidad = null;
	    			capacidad = Integer.parseInt(br.readLine());
					System.out.println("Pon un coste para la arista");
					Double coste = null;
	    			coste = Double.parseDouble(br.readLine());
	    			System.out.println("Pon un flujo para la arista");
					Integer flujo = null;
	    			flujo = Integer.parseInt(br.readLine());
					System.out.printf("id_from: %d, id_to: %d, capacidad: %d, coste: %f, flujo: %d \n", id_from, id_to, capacidad, coste, flujo);
					try {
						g.conectarNodes(id_from, id_to, capacidad, coste);
						int arista = g.getIDAresta(id_from, id_to);
						g.setFlujoAresta(arista, flujo);
						
					}
					catch (IOException e) {
					    throw new IOException(e);
					}
					break;
				
				case 3:
					FFDijkstra<Integer> dijk = new FFDijkstra<Integer>(0, g.getNSize()-1, g);
					ArrayList<Integer> camino = new ArrayList<Integer>();
					try {
						camino = dijk.dameCamino(g);
					}
					catch(IOException e) {
						throw new IOException(e);
					}
					for(int i = 0; i < camino.size(); ++i) System.out.printf("ID: " + camino.get(i) + " -> ");
					break;
				default:
			}
			
				
			muestraOpciones();
		    opcion = teclado.nextInt();
		}
		 teclado.close();
		System.out.print("PROGRAM EXIT");
	}
	
}
