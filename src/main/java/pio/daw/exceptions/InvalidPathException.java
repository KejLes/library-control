package pio.daw.exceptions;

public class InvalidPathException extends Exception{
	public InvalidPathException(String path) {
		super("El archivo no existe o la ruta es inválida: " + path);
	}
}
