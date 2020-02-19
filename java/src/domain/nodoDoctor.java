package domain;

public class nodoDoctor extends Nodo {
	private int idDoctor;
	
	public nodoDoctor() {}
	
	public nodoDoctor(int id, String tipo, int idDoctor) {
		super(id,tipo);
		this.idDoctor=idDoctor;
	}
	
	public int getIdDoc() {
		return idDoctor;
	}
	
	public void setidDoctor(int idDoctor) {
		this.idDoctor = idDoctor;
	}

}
