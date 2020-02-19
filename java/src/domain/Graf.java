package domain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

// @autor Sergi Soriano 

public class Graf<T>
{
  	static class Aresta
  	{
	    public int capacidad;
	    public Double coste;
	    public int flujo;
	    
	    public Aresta()
	    {
	    	this.capacidad = 0;
			this.coste = 0.0;			// per defecte es 0
			this.flujo = 0;
	    }

	    public Aresta(Integer capacidad, Double coste)
	    {
			this.capacidad = capacidad;
			this.coste = coste;             // per defecte es 0
			this.flujo = 0;
	    }
  	}

// ########## parametres publics ##############

// ########## parametres privats ###############

	private ArrayList<ArrayList<Integer>> matriuAdj;
	private ArrayList<T> nodes;
	private ArrayList<Aresta> arestes;

// ########## funcions publiques ##############
	
	// ########## constructor de la classe ########

	public Graf(){		
		this.matriuAdj = new ArrayList<ArrayList<Integer>>();
		//this.inArestes.put(0, new ArrayList<T>());
		this.nodes = new ArrayList<T>();
		this.arestes = new ArrayList<Aresta>();
	}

	// ########## getters #########################

    // retorna la id del node, IOException si no existeix.
    public int getNodeId(T node) throws IOException {
    	if(! existeNode(node)) 
    		throw new IOException("El node no existeix.");
		return this.nodes.indexOf(node);
    }

    //Retorna el node T que tenim associat al vector amb la id del parametre
    public T getNode(int id) throws IOException {
    	if(! existeixIdNode(id))
    		throw new IOException("El node amb aquest ID no existeix.");
    	return this.nodes.get(id);
    } 
    
   //Retorna els ids que estan com a origen per arribar al node id    ######com puc arribar a id#######
	public ArrayList<Integer> getInNodes(int id) throws IOException {
		if(! existeixIdNode(id))
    		throw new IOException("El node amb aquest ID no existeix.");
		ArrayList<Integer> in = new ArrayList<>();
    	Integer i;
		for(i=0; i< this.matriuAdj.size(); ++i){
			if((!i.equals(id)) && adjacents(i, id)){
				in.add(i);
			}
		}
		return in;
    }

	//Retorna els ids que estan com a destí del node id		#####com puc sortir de id######
    public ArrayList<Integer> getOutNodes(int id) throws IOException {
    	if(! existeixIdNode(id))
    		throw new IOException("El node amb aquest ID no existeix.");
		ArrayList<Integer> in = new ArrayList<>();
    	Integer i;
		for(i=0; i<this.matriuAdj.size(); ++i){
			if((!i.equals(id)) && adjacents(id, i)){
				in.add(i);
			}
		}
		return in;
	}
    
    // retorna el número de nodes ( 0 si no hi ha cap).
    public int getNSize() {
		return this.nodes.size();
    }
    
    // retorna el número d'arestes ( 0 si no hi ha cap).
    public int getASize() {
		return this.arestes.size();
    }
	
    // retorna el id de l'aresta IOException si no hi ha cap.
    public int getIDAresta(int id_from, int id_to) throws IOException {
 		try{
 			Boolean ok = adjacents(id_from,id_to);
 		}
 		catch(IOException e){
 			throw new IOException(e);
 		}
    	return this.matriuAdj.get(id_from).get(id_to);
    }
    
    // retorna la capacidad de l'aresta. IOException si no hi ha cap. 
    public int getCapacidadAresta(int id) throws IOException {
    	if(! existeixIdAresta(id))  		
    		throw new IOException("L'aresta amb aquest id no existeix.");
		return this.arestes.get(id).capacidad;
    }

	// retorna el cost de l'aresta. IOException si no hi ha cap. 
    public Double getCosteAresta(int id) throws IOException {
    	if(! existeixIdAresta(id))
    		throw new IOException("L'aresta amb aquest id no existeix.");
		return this.arestes.get(id).coste;
    }
    
	// retorna el flujo de l'aresta. IOException si no hi ha cap. 
    public int getFlujoAresta(int id) throws IOException {
    	if(! existeixIdAresta(id))
    		throw new IOException("L'aresta amb aquest id no existeix.");
		return this.arestes.get(id).flujo;
    }
    
	// retorna true si existeix aresta de id_from a id_to. Altramente, false
    public boolean adjacents(int id_from, int id_to) throws IOException {
    	if(! existeixIdNode(id_from))
 			throw new IOException("El node amb aquest id d'origen no existeix");
 		if(! existeixIdNode(id_to))
 			throw new IOException("El node amb aquest id de destí no existeix");	    	
    	if(id_from == id_to)
    		throw new IOException("inID i outID s'han de ser diferent");
    	if (this.matriuAdj.get(id_from).get(id_to).equals(-1))
    		return false;
    	return true;
    }
    // ########## setters #########################

    // fa un replace del node id. Si la id no és vàlida salta un error.
    public void substituirNode (int id, T node) throws IOException {
		try{
			this.nodes.set(id,node);
		} catch (IndexOutOfBoundsException e){
			throw new IOException(e);
		}
    }
    
    // modifica els paràmetres capacidad i cost de l'aresta id. Si la id no és vàlida salta un error.
    public void substituirAresta (int id, int capacidad, Double coste) throws IOException {
      	if(! existeixIdAresta(id))
			throw new IOException("Id d'aresta no vàlid");
  		arestes.get(id).capacidad = capacidad;
        arestes.get(id).coste = coste;
    }
    
	// modifica el parametre de flujo de l'aresta. Si la id no és vàlida salta un error.
    public void setFlujoAresta(int id, int flujo) throws IOException {
    	if(! existeixIdAresta(id))
			throw new IOException("Id d'aresta no vàlid");
  		arestes.get(id).flujo = flujo;
    }
	
	// Afegeix l’objecte node al Graf. Si el node ja existeix saltarà una IOException
    public void afegirNode(T node)throws IOException {
    	if(existeNode(node))
    		throw new IOException("El node ja existeix.");
    	this.matriuAdj.add(new ArrayList<Integer>());
    	this.nodes.add(node);
    	Integer size = nodes.size();
    	for(int i = 0; i < size; ++i) {
    		int dif = size - this.matriuAdj.get(i).size();
    		for(int j = 0; j < dif; j++) {
    			this.matriuAdj.get(i).add(-1);
    		}
    	}
    }
    
	//Afegeix una aresta al graf amb origen al node id_from i destí id_to amb la capacitat i cost 
	//que ens passen per paràmetres. Si les ids no són vàlides o l’aresta ja existeix saltarà una IOException
    public void conectarNodes(int id_from, int id_to, int capacidad, Double coste)throws IOException {
    	if(! adjacents(id_from, id_to)){
	    	Aresta foo = new Aresta (capacidad, coste);
			this.arestes.add(foo);
			Integer idAresta = this.arestes.indexOf(foo);
			this.matriuAdj.get(id_from).set(id_to, idAresta);
    	}
    	else throw new IOException("La aresta ja existeix.");
	}
    
	//Borra el node id del graf. Si id no és vàlid saltarà una IOException.
    public void removeNode(int id) throws IOException {
    	if(! existeixIdNode(id))
 			throw new IOException("El node amb aquest id no existeix");
    	Integer i;
 		for(i = 0; i < this.matriuAdj.size(); ++i){
 			if(!i.equals(id)){
 				removeAresta(i, id);
 				removeAresta(id, i);
 			}
 		}
 		for(i = 0; i < this.matriuAdj.size(); ++i){
 			this.matriuAdj.get(i).remove(id); 
 		}
		this.matriuAdj.remove(id);
 		this.nodes.remove(id);
    }
    
	//Borra l’aresta amb origen node id_from i destí node id_to del Graf. Si l’aresta no existeix o els ids no són vàlids saltarà un error IOException.
    public void removeAresta(int id_from, int id_to) throws IOException {
    	if(existeixIdNode(id_from) && existeixIdNode(id_to)) {
	    	int aresta = this.matriuAdj.get(id_from).get(id_to);
	    	if(existeixIdAresta(aresta)){
	    		this.matriuAdj.get(id_from).set(id_to, -1);
	    		arreglarMatriu(aresta);
	    		arestes.remove(aresta); 
	    	}
    	}
    }

// ######## funcions privades #################
    
    private boolean existeixIdNode(int id) {
    	return (id >= 0 && id < nodes.size());
    }

    private boolean existeixIdAresta(int id) {
    	return (id >= 0 && id < arestes.size());
    }

    private boolean existeNode(T n) {
    	return this.nodes.contains(n);
    }
    
    private void arreglarMatriu(int id_aresta){
    	Integer i, j;
    	for(i=0; i<this.matriuAdj.size(); ++i){
    		for(j=0; j<this.matriuAdj.size(); ++j){
    			if(this.matriuAdj.get(i).get(j) > id_aresta){
    				int id = this.matriuAdj.get(i).get(j);
    				this.matriuAdj.get(i).set(j, id-1);
    			}
    		}
    	}
    }
    
}
