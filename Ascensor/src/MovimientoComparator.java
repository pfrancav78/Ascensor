import java.util.Comparator;

public class MovimientoComparator implements Comparator<Movimiento> {

	private final int direccion;

	public MovimientoComparator(int direccion) {
		this.direccion = direccion;
	}

	@Override
	public int compare(Movimiento o1, Movimiento o2) {
		// TODO Auto-generated method stub
		return (new Integer(o1.getPisoDestino()).compareTo(o2.getPisoDestino())) * direccion;
	}

}
