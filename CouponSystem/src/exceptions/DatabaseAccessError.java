package exceptions;

public class DatabaseAccessError extends Exception
{
	public DatabaseAccessError()
	{
		super("Failed to establish connection to DB");
	}
}
