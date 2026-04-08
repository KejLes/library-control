package pio.daw.exceptions;

public class InvalidArgsLengthException extends Exception {
	public InvalidArgsLengthException(int argsLength) {
		super("Se esperaba 1 argumento, pero se recibieron: " + argsLength);
	}
}
