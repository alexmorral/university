package domain;

public class Nodo {
	
	protected int id;
	protected String tipo;
	
	public Nodo() {}
	
	public Nodo(int id, String tipo) {
		this.id =id;
		this.tipo = tipo;
		
	}
	
	public int getId() {
		return id;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
