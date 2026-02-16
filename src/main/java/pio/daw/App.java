package pio.daw;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    /**
     * Parse the arguments of the program to get the library registry file
     * path. Exits the program if the args are not correct or the file does
     * not exists.
     * @param args program args.
     * @return Path to file if exists.
     */
    public static Path getPathFromArgs(String[] args){
        Path p = Paths.get(args[0]);
        return (p);
    }

    public static void main(String[] args) {
		if(args.length != 1)
			throw Exception;
        Path p = getPathFromArgs(args);
        Controlable controler = Library.fromFile(p);
        controler.printResume();
    }
}
