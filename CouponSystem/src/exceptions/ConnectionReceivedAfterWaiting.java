package exceptions;

public class ConnectionReceivedAfterWaiting extends Exception
{
	public ConnectionReceivedAfterWaiting()
	{
		super("Connection received after waiting");
	}
}
