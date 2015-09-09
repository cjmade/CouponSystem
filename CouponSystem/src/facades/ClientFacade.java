package facades;

import java.sql.SQLException;

public interface ClientFacade
{
	// Abstract method login
	// On successful login should return ClientFacade object
	public ClientFacade login(String name, String password) throws SQLException;
}
