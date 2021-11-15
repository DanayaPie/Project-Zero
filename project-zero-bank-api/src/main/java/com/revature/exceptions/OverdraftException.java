package com.revature.exceptions;

public class OverdraftException extends Exception {

	public OverdraftException() {
		super();

	}

	public OverdraftException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public OverdraftException(String message, Throwable cause) {
		super(message, cause);

	}

	public OverdraftException(String message) {
		super(message);

	}

	public OverdraftException(Throwable cause) {
		super(cause);

	}

}
