package com.bitoasis.assignment.exception;

public class UnableToFetchTicker extends Exception {

	private static final long serialVersionUID = -6484384325812950614L;

	public UnableToFetchTicker() {
	}
	
	public UnableToFetchTicker(String message) {
		super(message);
	}

}
