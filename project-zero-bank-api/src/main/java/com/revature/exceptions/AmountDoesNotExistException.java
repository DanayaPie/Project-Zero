package com.revature.exceptions;

public class AmountDoesNotExistException extends Exception{

	public AmountDoesNotExistException() {
		super();

	}

	public AmountDoesNotExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public AmountDoesNotExistException(String message, Throwable cause) {
		super(message, cause);

	}

	public AmountDoesNotExistException(String message) {
		super(message);

	}

	public AmountDoesNotExistException(Throwable cause) {
		super(cause);

	}

}
