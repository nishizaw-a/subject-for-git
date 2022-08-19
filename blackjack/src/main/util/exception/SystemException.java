package main.util.exception;

public class SystemException extends Exception{
	public SystemException(String msg) {
		super(msg);
	}

	public String getMessage() {
		return super.getMessage();
	}
}
