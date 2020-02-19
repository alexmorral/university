package drivers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Graf;


public class DriverGraf
{
	private static void introduccion()
	{
		System.out.println("------------------------");
        System.out.println("|    Driver de Graf    |");
        System.out.println("------------------------");
	}

	private static void menu()
	{
		System.out.println("Opcions:");
        System.out.println("1) Afegir node");
        System.out.println("2) Elimina node"); 
        System.out.println("3) Modifica node");
        System.out.println("4) Consulta id node");
        System.out.println("5) Afegir aresta");
        System.out.println("6) Elimina aresta");
        System.out.println("7) Modifica aresta");
        System.out.println("8) Consulta id d'aresta");
        System.out.println("9) Consulta cost d'aresta");
        System.out.println("10) Consulta capacitat d'aresta");
        System.out.println("11) Consultar flujo d'aresta");
        System.out.println("12) Introduir flujo a l'aresta");
        System.out.println("13) Consultar numero de nodes");
        System.out.println("14) Consultar numero d'arestes");
        System.out.println("15) Consultar si dos nodes son adjacents");
        System.out.println("16) Consultar els nodes als que puc anar des d'un node");
        System.out.println("17) Consultar els nodes desde els que puc accedir a un node");
        System.out.println("18) Veure tots els nodes i arestes actuals al graf");
        System.out.println("0) exit");
	}


	public static void main(String[] args) throws IOException 
	{
		introduccion();
		menu();
		Graf<Integer> g = new Graf<Integer>();
		
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);
        int option = Integer.parseInt(br.readLine());

        while(option != 0)
        {
        	switch(option)
        	{
        		case 1:
        			Integer nom = null;
        			System.out.println("Introdueix un numero pel node");
        			nom = Integer.parseInt(br.readLine());
        			try {
        				g.afegirNode(nom);
        				System.out.println("S'ha afegit correctament");
        			} catch(IOException e) {
        				System.out.println(e);
        			}
        			break;
        		case 2:
        			System.out.println("Introdueix el id de la ciutat");
        			Integer id = Integer.parseInt(br.readLine());
        			try {
        				g.removeNode(id);
        			} catch(IOException e) {
        				System.out.println(e);
        			}
        			
        			break;
        		case 3:
        			System.out.println("Introdueix el id de la ciutat a modificar");
        			id = Integer.parseInt(br.readLine());
        			try {
        				g.removeNode(id);
        			} catch(IOException e) {
        				System.out.println(e);
        			}
        			System.out.println("Introdueix un numero nou pel node");
        			nom = Integer.parseInt(br.readLine());
        			try{
        				g.substituirNode(id, nom);
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 4:
        			System.out.println("Vols saber el seu id o el node?\n1)id \n2)node");
        			Integer opcio = Integer.parseInt(br.readLine());
        			switch(opcio){
        			case 1:
    					System.out.println("Introdueix el node per coneixer el seu id");
    					nom = Integer.parseInt(br.readLine());
    					try{
    						System.out.printf("El id del node es: %d\n", g.getNodeId(nom));
            			}catch(IOException e){
            				System.out.println(e);
            			}
    					break;
        				case 2:
        					System.out.println("Introdueix el id del node que vols coneixer");
        					id = Integer.parseInt(br.readLine());
        					try{
        						System.out.printf("El node es: %d\n", g.getNode(id));
                			}catch(IOException e){
                				System.out.println(e);
                			}
        					break;
        			}
        			break;
        		case 5:
        			System.out.println("Introdueix la id del node origen");
        			Integer id1 = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix la id del node destí");
        			Integer id2 = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix capacitat de la aresta");
        			Integer cap = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix cost de la aresta");
        			Double cost = Double.parseDouble(br.readLine());
        			try{
    					g.conectarNodes(id1, id2, cap, cost);
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 6:
        			System.out.println("Introdueix la id del node origen");
        			id1 = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix la id del node destí");
        			id2 = Integer.parseInt(br.readLine());
        			try{
        				g.removeAresta(id1, id2);
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 7:
        			System.out.println("Introdueix la id del node origen");
        			id1 = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix la id del node destí");
        			id2 = Integer.parseInt(br.readLine());
        			
        			System.out.println("Introdueix nova capacitat");
        			cap = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix nou cost");
        			cost = Double.parseDouble(br.readLine());
        			try {
        				id = g.getIDAresta(id1, id2);
        			}catch(IOException e){
        				System.out.println(e);
        				break;
        			}
        			try {
        				g.substituirAresta(id, cap, cost);
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 8:
        			System.out.println("Introdueix la id del node origen");
        			id1 = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix la id del node destí");
        			id2 = Integer.parseInt(br.readLine());
        			try {
        				System.out.println(g.getIDAresta(id1, id2));
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 9:
        			System.out.println("Introdueix la id de l'aresta per coneixer el cost associat");
        			id1 = Integer.parseInt(br.readLine());
        			try {
        				System.out.println(g.getCosteAresta(id1));
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 10:
        			System.out.println("Introdueix la id de l'aresta per coneixer la capacitat associada");
        			id1 = Integer.parseInt(br.readLine());
        			try {
        				System.out.println(g.getCapacidadAresta(id1));
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 11:
        			System.out.println("Introdueix la id de l'aresta per coneixer el flujo associat");
        			id1 = Integer.parseInt(br.readLine());
        			try {
        				System.out.println(g.getFlujoAresta(id1));
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 12:
        			System.out.println("Introdueix la id de l'aresta per introduir el flujo");
        			id1 = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix el nou flujo");
        			Integer flujo = Integer.parseInt(br.readLine());        			
        			try {
            			g.setFlujoAresta(id1, flujo);
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 13:
        			System.out.printf("El número de nodes total del graf és: %d\n", g.getNSize());
        			break;
        		case 14:
        			System.out.printf("El número d'arestes total del graf és: %d\n", g.getASize());
        			break;
        		case 15:
        			System.out.println("Introdueix la id del node origen");
        			id1 = Integer.parseInt(br.readLine());
        			System.out.println("Introdueix la id del node destí");
        			id2 = Integer.parseInt(br.readLine());
        			try {
            			if(g.adjacents(id1, id2)) System.out.println("Són adjacents");
            			else System.out.println("No són adjacents");
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			break;
        		case 16:
        			System.out.println("Introdueix la id del node origen per saber els possibles destins");
        			id = Integer.parseInt(br.readLine());
        			ArrayList<Integer> llista = new ArrayList<Integer>();
        			try {
        				llista = g.getOutNodes(id);
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			Integer i, mida;
        			mida = llista.size();
        			if(mida.equals(0)) {
        				System.out.println("No hi ha cap node on poder anar des d'aquest node");
        			}
        			for(i=0; i<mida; ++i) System.out.println(llista.get(i));
        			break;
        		case 17:
        			System.out.println("Introdueix la id del node per saber desde quins nodes s'hi pot accedir-hi");
        			id = Integer.parseInt(br.readLine());
        			llista = new ArrayList<Integer>();
        			try {
        				llista = g.getInNodes(id);
        			}catch(IOException e){
        				System.out.println(e);
        			}
        			mida = llista.size();
        			if(mida.equals(0)) {
        				System.out.println("No hi ha cap node on poder anar des d'aquest node");
        			}
        			for(i=0; i<mida; ++i) System.out.println(llista.get(i));
        			break;
        		case 18: 
        			System.out.println("Nodes:");
        			int j;
        			for (j=0; j<g.getNSize(); ++j) System.out.println(g.getNode(j));
        			System.out.printf("\n");
        			System.out.println("Arestes:");
        			for(i=0; i<g.getNSize(); ++i){
        				for (j=0; j<g.getNSize(); ++j) {
        					if(i != j && g.getIDAresta(i, j) != -1) System.out.println(g.getIDAresta(i, j));
        				}
        			}
        			System.out.printf("\n");
        			break;
        		default: 
                    System.out.println("Opció no implementada");
        	}
        	menu();
            option = Integer.parseInt(br.readLine());
        }
	}
}
