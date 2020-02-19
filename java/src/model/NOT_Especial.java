package model;
import model.Restriccion;

/**
* Representa una Restriccion 
* @author Axel Pelaez
*/
public class  NOT_Especial extends Restriccion{

	private String especial; 
	
	/**
	* Crea una Restriccion del tipo NOT_Especial
	*  @param id: identificador de la restriccion, 
	* 		  esp: evento especial en el cual dicho dia no se quiere trabajar
	* @override Restriccion
	*/
	public NOT_Especial(int id, String esp) {
		super(id,"NOT_Especial");
		especial = esp;
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
	
	/**
	*Consultora del evento especial que no se quiere trabajar
	* @return El evento especial
	* @override Restriccion
	*/
	public String getEspecial(){
		return especial;
	}
	
	
}