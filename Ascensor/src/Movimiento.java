/**
 * Representa un simple movimiento a traves de una direccion ascendento o
 * descendente y un piso destiono
 * 
 * @author pablo
 * 
 */
public class Movimiento {

	private int direccion;
	private int pisoDestino;

	
	
	public Movimiento(int direccion, int pisoDestino) {
		super();
		this.direccion = direccion;
		this.pisoDestino = pisoDestino;
	}
	public int getDireccion() {
		return direccion;
	}
	public int getPisoDestino() {
		return pisoDestino;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Direccion: "+direccion + " pisoDestino: "+ pisoDestino;
	}

}
