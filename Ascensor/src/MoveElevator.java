public class MoveElevator implements Runnable {

	private Ascensor elevator;
	private Movimiento movimiento;

	public MoveElevator(Ascensor elevator) {
		super();
		this.elevator = elevator;
		

	}

	@Override
	public void run() {

		// ascensor.ir(pisoDestino);
		this.movimiento = elevator.getEstado().usarProximoMovimiento();
		boolean llegue = false;
		while (!llegue) {
			if (hayNuevaTarea(movimiento)) {
				System.out.println("Agrego como movimiento el actual que se aborta: " +movimiento);
				elevator.getEstado().agregarProximoMovimiento(movimiento);
				System.out.println("Tomo el nuevo movimiento y lo sigo procesando: " +movimiento);
				this.movimiento = elevator.getEstado().usarProximoMovimiento();
					
				//throw new TareaAbortadaException();
			}
			llegue = elevator.irPasoAPaso(movimiento.getPisoDestino());
		}
		
		System.out.println("--- Ascensor " + elevator.getId() + " llegó a destino al piso: "
				+ elevator.getEstado().getPisoActual());
		// this.pisoDestino =
		// this.ascensor.getEstado().usarProximoMovimiento().getPisoDestino();

	}

	private boolean hayNuevaTarea(Movimiento mov) {
		return (elevator.getEstado().getProximoMovimiento()!=null && !elevator.getEstado().getProximoMovimiento().equals(mov));
	}

}
