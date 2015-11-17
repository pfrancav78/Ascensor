/**
 * Es una fila de informacion de status. Es usada para calculos por el
 * AscensorManager
 * 
 * @author pablo
 * 
 */
public class ElevatorStatusRow implements Comparable<ElevatorStatusRow> {

	private Ascensor elevator;
	private int fs;

	public ElevatorStatusRow(Ascensor elevator, int fs) {
		this.elevator = elevator;
		this.fs = fs;
	}

	public Ascensor getElevator() {
		return elevator;
	}

	public int getFs() {
		return fs;
	}

	@Override
	public String toString() {
		return "Ascensor : " + this.elevator.getId() + " FS: " + this.fs;
	}

	@Override
	public int compareTo(ElevatorStatusRow o) {
		return new Integer(this.fs).compareTo(new Integer(o.fs));
	}

}
