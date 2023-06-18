package tn.proosoftcloud.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(String email) {
		super(email);
	}

}
