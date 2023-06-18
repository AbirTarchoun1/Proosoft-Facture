package tn.proosoftcloud.exceptions;

public class RoleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RoleAlreadyExistsException(String name) {
		super(name);
	}
	
}
