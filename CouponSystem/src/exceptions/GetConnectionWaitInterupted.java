package exceptions;

public class GetConnectionWaitInterupted extends Exception
{
	public GetConnectionWaitInterupted()
	{
		super("Waiting for a connection interupted");
	}
}
