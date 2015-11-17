import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.opencsv.CSVReader;

public class Ejecutor {

	public static void main(String[] args) throws IOException,
			InterruptedException {

		int portNumber = 9000;
		AscensorManager ascensorManager = AscensorManager.getInstance();

		inputFromFile(ascensorManager);
		listenSocket(portNumber, ascensorManager);

	}

	private static void inputFromFile(AscensorManager ascensorManager)
			throws FileNotFoundException, IOException {
		long miliseconds;
		long currentMilliseconds = -1;
		int pisoOrigen;
		int pisoDestino;
		CSVReader reader = new CSVReader(new FileReader(
				"/home/pablo/simulacion.csv"));
		String[] nextLine;

		long start_time = System.currentTimeMillis();

		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			miliseconds = new Long(nextLine[0]).longValue();
			pisoOrigen = new Integer(nextLine[1]).intValue();
			pisoDestino = new Integer(nextLine[2]).intValue();

			while (currentMilliseconds < miliseconds) {
				currentMilliseconds = System.currentTimeMillis() - start_time;
			}

			System.out.println(miliseconds + ", " + pisoOrigen + ", "
					+ pisoDestino);
			ascensorManager.ejecutarPedido(pisoOrigen, pisoDestino);

		}
		reader.close();
	}

	private static void listenSocket(int portNumber,
			AscensorManager ascensorManager) throws IOException {
		ServerSocket serverSocket = new ServerSocket(portNumber);
		while (true) {
			try (Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(
							clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {

					ascensorManager.ejecutarPedido(0,
							(new Integer(inputLine).intValue()));
				}
			} catch (IOException e) {
				System.out
						.println("Exception caught when trying to listen on port "
								+ portNumber + " or listening for a connection");
				System.out.println(e.getMessage());
			}

		}
	}

	// public List<String> readLargeFileLines(String name) {
	// try {
	// BufferedReader reader = Files.newBufferedReader(FileSystems
	// .getDefault().getPath(".", name), Charset.defaultCharset());
	//
	// List<String> lines = new ArrayList<>();
	// String line = null;
	// while ((line = reader.readLine()) != null)
	// lines.add(line);
	//
	// return lines;
	// } catch (IOException ioe) {
	// ioe.printStackTrace();
	// }
	// return null;
	// }

	// //Get scanner instance
	// Scanner scanner = new Scanner(new File("/home/pablo/simulacion.csv"));
	//
	// //Set the delimiter used in file
	// scanner.useDelimiter(",");
	//
	// //Get all tokens and store them in some data structure
	// //I am just printing them
	// while (scanner.hasNext())
	// {
	// System.out.print(scanner.next() + "|");
	// }
	//
	// //Do not forget to close the scanner
	// scanner.close();
	//

}
