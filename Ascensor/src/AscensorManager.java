import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AscensorManager {

	private static AscensorManager instance;
	private List<Ascensor> ascensores = new ArrayList<>();
	private List<ExecutorService> executors = new ArrayList<>();
	private Executor pool;

	private static final int MAX_PISOS = 5;

	private AscensorManager() {

		pool = Executors.newFixedThreadPool(5);
		ascensores.add(new Ascensor(1, MAX_PISOS));
		// ascensores.add(new Ascensor(2, MAX_PISOS));
		// ascensores.add(new Ascensor(3, MAX_PISOS));
		pool.execute(new PrintStatus(this));

		executors.add(Executors.newSingleThreadExecutor());
		// executors.add(Executors.newSingleThreadExecutor());
		// executors.add(Executors.newSingleThreadExecutor());
	}

	class PrintStatus implements Runnable {

		private AscensorManager ascensorManager;

		public PrintStatus(AscensorManager asc) {
			this.ascensorManager = asc;
		}

		@Override
		public void run() {
			while (true) {
				try {
					this.ascensorManager.printStatus();
					Thread.sleep(2000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * Devuelve un manager inicializado con los ascensores en el pool
	 * 
	 * @return
	 */
	public final static AscensorManager getInstance() {
		if (instance == null) {
			instance = new AscensorManager();

		}
		return instance;
	}

	public Ascensor getBestNextElevator(int pisoLlamado, int pisoDestino) {
		List<ElevatorStatusRow> infoElevadores = this.calculateFS(this.ascensores, pisoLlamado, pisoDestino);

		Collections.sort(infoElevadores);
		// for (ElevatorStatusRow row : infoElevadores) {
		// System.out.println(row);
		// }
		return infoElevadores.get(infoElevadores.size() - 1).getElevator();

	}

	private List<ElevatorStatusRow> calculateFS(List<Ascensor> ascensores, int pisoLlamado, int pisoDestino) {
		List<ElevatorStatusRow> lista = new ArrayList<>();
		int fs = 0;
		int direccionDestino, direccionLlamado = 0;

		// Direccion del destino, es decir si se va a ir hacia arriba o hacia
		// abajo
		direccionDestino = (pisoDestino > pisoLlamado) ? 1 : -1;
		// System.out.println("Direccion Destino :" + direccionDestino);

		for (Ascensor ascensor : ascensores) {

			direccionLlamado = (new Integer(pisoLlamado).compareTo(ascensor.getEstado().getPisoActual()));
			// System.out.println("Ascensor: " + ascensor.getId() + "mov " +
			// ascensor.getEstado().getMovimientoActual()
			// + " Direccion Llamado :" + direccionLlamado);

			if (ascensor.getEstado().isAvailable()) {
				if (direccionLlamado == 0) {
					// Caso 1
					fs = ascensor.getMaxPiso() * 2;
				} else
					// Caso 2 fs=distancia
					fs = ascensor.getMaxPiso() - Math.abs(pisoLlamado - ascensor.getEstado().getPisoActual());
			} else if (ascensor.getEstado().getMovimientoActual() != direccionLlamado) {
				// Caso 3
				fs = -1;
			} else if (direccionLlamado == direccionDestino) {
				// Caso 4
				fs = 1 + ascensor.getMaxPiso() - Math.abs(pisoLlamado - ascensor.getEstado().getPisoActual());
			} else {
				// Caso 5
				fs = -20;
			}
			lista.add(new ElevatorStatusRow(ascensor, fs));
		}
		return lista;

	}

	public void ejecutarPedido(int pisoPedido, int pisoDestino) {

		Ascensor elevator = getBestNextElevator(pisoPedido, pisoDestino);
		int direccion = new Integer(pisoDestino).compareTo(elevator.getEstado().getPisoActual());
		Executor service = executors.get(elevator.getId() - 1);

		if (elevator.getEstado().getPisoActual() != pisoPedido) {
			elevator.getEstado().agregarProximoMovimiento(new Movimiento(direccion, pisoPedido));
			service.execute(new MoveElevator(elevator));
			System.out.println("Ascensor " + elevator.getId() + " dirigirse al piso" + pisoPedido);
		}
		elevator.getEstado().agregarProximoMovimiento(new Movimiento(direccion, pisoDestino));
		service.execute(new MoveElevator(elevator));
		System.out.println("Ascensor " + elevator.getId() + " dirigirse al piso" + pisoDestino);

	}

	public void printStatus() {

		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Ascensor ascensor : ascensores) {
			System.out.println("Ascensor " + ascensor.getId() + "Piso: " + ascensor.getEstado().getPisoActual()
					+ " Estado: " + ascensor.getEstado().getMovimientoActual());

		}
		System.out.println("----------------");
	}

}
