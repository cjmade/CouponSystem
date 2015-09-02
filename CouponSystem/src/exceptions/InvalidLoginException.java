package exceptions;

public class InvalidLoginException extends Exception
{
	public InvalidLoginException()
	{
		super("Invalid username/password combination");
	}
}
