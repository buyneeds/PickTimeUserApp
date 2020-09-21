package com.picktime.exception;


public class UserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserException(Exception e) {
        super(e);
    }
	
	public void writeException(Exception e){
		System.err.println("Exception occured.."+e.getMessage());
	}
}
