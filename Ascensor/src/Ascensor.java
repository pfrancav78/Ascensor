public class Ascensor {

	// private Ejecutor ejecutor;
	private int id;
	private EstadoAscensor estadoAscensor = new EstadoAscensor(0, 0);
	private int max_pisos;

	public Ascensor(int id, int max_pisos) {
		this.id = id;
		this.max_pisos = max_pisos;
	}

	public boolean irPasoAPaso(int piso) {

		if (estadoAscensor.getPisoActual() != piso) {
			muevePiso(piso);
		}
		if (estadoAscensor.getPisoActual() == piso) {
			return true;
		} else return false;
		

		
	}
	
	public void ir(int piso) {
		while (estadoAscensor.getPisoActual() != piso) {
			muevePiso(piso);
		}
		if (estadoAscensor.getPisoActual() == piso) {
			estadoAscensor.stop();
		}
		System.out.println("--- Ascensor " + this.getId() + " llegó a destino al piso: " + estadoAscensor.getPisoActual());

	}

	private void muevePiso(int piso) {
		if (piso > this.max_pisos && piso < 0) {
			throw new RuntimeException("Piso fuera de rango");
		}


		if (estadoAscensor.getPisoActual() < piso) {
			estadoAscensor.up();
		} else {
			estadoAscensor.down();
		}
		try {
			Thread.sleep(1500);// 5 segundos movimiento entre piso y piso
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		estadoAscensor.setPisoActual(estadoAscensor.getPisoActual()
				+ estadoAscensor.getMovimientoActual());

	}

	public EstadoAscensor getEstado() {
		return this.estadoAscensor;
	}

	public int getId() {
		return this.id;
	}
	
	public int getMaxPiso(){
		return this.max_pisos;
	}

}
