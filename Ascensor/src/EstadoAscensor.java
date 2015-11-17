import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstadoAscensor {
	private static final int STOP = 0;
	private static final int UP = 1;
	private static final int DOWN = -1;

	private int pisoActual = 0;
	private int movimientoActual = STOP;
	private List<Movimiento> movimientos = new ArrayList<>();

	public synchronized Movimiento usarProximoMovimiento() {
		Movimiento mov = movimientos.get(0);
		movimientos.remove(0);
		return mov;
	}

	public synchronized Movimiento getProximoMovimiento() {
		return (movimientos.isEmpty() ? null: movimientos.get(0));
	}

	/**
	 * Agrega un piso Destino a la lista de pisos que de debe recorrer el
	 * ascensor Si un destino ya existe, no lo vuelve a agregar La lista de
	 * destinos esta ordenada
	 * 
	 * @param pisoDestino
	 */
	public synchronized void agregarProximoMovimiento(Movimiento mov) {
		System.out.println("**** Nuevo mov *****");
		System.out.println(mov);

		this.movimientos.add(mov);
		Collections.sort(movimientos, new MovimientoComparator(mov.getDireccion()));
		System.out.println("**** movs *******");
		System.out.println(movimientos);
	}

	public EstadoAscensor(int pisoActual, int movimientoActual) {
		super();
		this.pisoActual = pisoActual;
		this.setMovimientoActual(movimientoActual);
	}

	public void up() {
		this.setMovimientoActual(UP);
	}

	public void down() {
		this.setMovimientoActual(DOWN);

	}

	public void stop() {
		this.setMovimientoActual(STOP);
	}

	public void setPisoActual(int piso) {
		this.pisoActual = piso;
	}

	public int getPisoActual() {
		return this.pisoActual;
	}

	public int getMovimientoActual() {
		return movimientoActual;
	}

	public void setMovimientoActual(int movimientoActual) {
		this.movimientoActual = movimientoActual;
	}

	/**
	 * 
	 * @return true if the elevator is stopped
	 */
	public boolean isAvailable() {
		return (this.getMovimientoActual() == STOP);
	}
}
