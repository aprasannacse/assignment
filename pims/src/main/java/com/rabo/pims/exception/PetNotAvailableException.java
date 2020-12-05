package com.rabo.pims.exception;

public class PetNotAvailableException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PetNotAvailableException(String msg) {
		super(msg);
	}
}
