package com.rabo.pims.exception;

public class PersonAlreadyExistException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonAlreadyExistException(String msg) {
		super(msg);
	}

}
