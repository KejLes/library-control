package pio.daw;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    /**
     * Parse the arguments of the program to get the library registry file
     * path. Exits the program if the args are not correct or the file does
     * not exists.
     * @param args program args.
     * @return Path to file if exists.
     */
    public static Path getPathFromArgs(String[] args){
		if (args.length != 1)
		{
			System.err.println("Se espera un único argumento que es la ruta del texto");
			System.exit(0);
		}
        Path p = Paths.get(args[0]);
        return (p);
    }

    public static void main(String[] args) {
        Path p = getPathFromArgs(args);
		try (Scanner sc = new Scanner(p)){
			String str;
			while (sc.hasNext()) {
				str = sc.nextLine();
				System.out.println(str);
			}
		} catch (Exception e) {
			System.err.println("Fallo con Scanner y / o el archivo");
		}
    }
}
