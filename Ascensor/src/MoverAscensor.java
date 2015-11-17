/**
 * @deprecated
 * 
 * @author pablo
 * 
 */
public class MoverAscensor implements Runnable {

	public MoverAscensor(Ascensor ascensor, int pisoDestino) {
		super();
		this.ascensor = ascensor;
		this.pisoDestino = pisoDestino;

	}

	private Ascensor ascensor;
	private int pisoDestino;

	@Override
	public void run() {
		int direccion = new Integer(pisoDestino).compareTo(ascensor.getEstado().getPisoActual());
		Movimiento movimiento = new Movimiento(direccion, pisoDestino);
		this.ascensor.getEstado().agregarProximoMovimiento(movimiento);

		// ascensor.ir(pisoDestino);
		boolean llegue = false;
		while (!llegue) {
			if (hayNuevaTarea(movimiento)) {
				throw new TareaAbortadaException();
			}
			llegue = ascensor.irPasoAPaso(pisoDestino);
		}
		System.out.println("--- Ascensor " + ascensor.getId() + " llegó a destino al piso: "
				+ ascensor.getEstado().getPisoActual());
		this.pisoDestino = this.ascensor.getEstado().usarProximoMovimiento().getPisoDestino();

	}

	private boolean hayNuevaTarea(Movimiento mov) {
		return !ascensor.getEstado().getProximoMovimiento().equals(mov);
	}

}
