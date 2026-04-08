package pio.daw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import pio.daw.exceptions.InvalidArgsLengthException;
import pio.daw.exceptions.InvalidPathException;

public class App {
	/**
	 * Parse the arguments of the program to get the library registry file
	 * path. Exits the program if the args are not correct or the file does
	 * not exists.
	 *
	 * @param args program args.
	 * @return Path to file if exists.
	 */

	public static Path getPathFromArgs(String[] args)
			throws InvalidArgsLengthException, InvalidPathException {

		if (args.length != 1)
			throw new InvalidArgsLengthException(args.length);
		Path p = Paths.get(args[0]);
		if (!Files.exists(p))
			throw new InvalidPathException(args[0]);
		return (p);
	}

	public static void main(String[] args) {
		try {
            Path p = getPathFromArgs(args);
            Controlable controler = Library.fromFile(p);
            controler.printResume();
        } catch (InvalidArgsLengthException e) {
            System.out.println("Error de argumentos: " + e.getMessage());
            System.exit(1);
        } catch (InvalidPathException e) {
            System.out.println("Error de ruta: " + e.getMessage());
            System.exit(1);
        }
	}
}
