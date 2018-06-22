package fr.pizzeria.exception;

public class UnknownCategorieException extends Exception {
	
	public UnknownCategorieException() {
	}
	
	public UnknownCategorieException(String msg) {
		super(msg);
	}

}
