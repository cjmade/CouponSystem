package exceptions;

public class WaitingForConnectionInterrupted extends Exception
{
	public WaitingForConnectionInterrupted()
	{
		super("Connection received after waiting");
	}
}
