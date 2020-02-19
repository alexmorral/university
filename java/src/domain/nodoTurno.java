package domain;
import java.util.*;

public class nodoTurno extends Nodo {
	private GregorianCalendar fecha;
	private String tipoTurno;
	
	public nodoTurno() {}
	
	public nodoTurno(int id, String tipo, GregorianCalendar fecha, String tipoTurno) {
		super(id,tipo);
		this.fecha=fecha;
		this.tipoTurno=tipoTurno;
	}
	
	public GregorianCalendar getFecha() {
		return fecha;
	}
	
	public String getTipoTurno() {
		return tipoTurno;
	}
	
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}
	
	public void setTipoTurno(String tipoTurno) {
		this.tipoTurno = tipoTurno;
	}
}
