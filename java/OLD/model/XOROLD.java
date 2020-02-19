/*package model;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import model.Restriccion;*/


/**
* Representa una restriccion de tipo XOR
* @author Axel Pelaez
*/
public class XOR extends Restriccion {

	private List<GregorianCalendar> listaXOR = new ArrayList<GregorianCalendar>();
	
	/* Constructora */
	
	
	public XOR(int id, List<GregorianCalendar> listaX) {
		super(id,"XOR");
		 listaXOR  = listaX;
	}

	

	/* Metodos públicos */

	//Consultoras
	/**
	*Consultora del identificador de la restriccion
	* @return La id de la restriccion
	* @override Restriccion
	*/
	public int getIdRestriccion() {
	    return idRestriccion ;
	}
	
	/**
	*Consultora del tipo de restriccion
	* @return El tipo de restriccion
	* @override Restriccion
	*/
	public String getTipo(){
		return tipo;
	}
	
	public List<GregorianCalendar> getListDates(){
		return listaXOR; 
	}
	
	//Modificadoras
	
	public void AddFecha(GregorianCalendar newFecha) {
		listaXOR.add(newFecha);
	}
	
	public void AddFecha(int d, int m, int a) {
		GregorianCalendar newFecha = new GregorianCalendar(a,m-1,d);
		listaXOR.add(newFecha);
	}

}
