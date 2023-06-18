package tn.proosoftcloud.exceptions;

public class RoleNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RoleNotExistsException(String name) {
		super(name);
	}
	
}
