package com.revature.exceptions;

import java.util.ArrayList;
import java.util.List;

public class InvalidParameterException extends Exception {

	public List<String> message = new ArrayList<>();
	
	public InvalidParameterException() {
		super();
	}

	public InvalidParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidParameterException(String message) {
		super(message);
	}

	public InvalidParameterException(Throwable cause) {
		super(cause);
	}
	
}
