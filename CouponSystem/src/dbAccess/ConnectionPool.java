package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

import exceptions.GetConnectionWaitInterupted;


public class ConnectionPool  {
	// Singleton object
	private static ConnectionPool pool;
	// Connection parameters
	String url = "jdbc:derby://localhost:1527/Coupondb";
	String username = "admin";
	String password = "admin";
	// Collections of connections
	private HashSet<Connection> newConnections = null;
	private HashSet<Connection> usedConnections = null;
	// Constructor
	private ConnectionPool() throws SQLException {
		// Initialize connection collections
		newConnections = new HashSet<Connection>();
		usedConnections = new HashSet<Connection>();
		// Fill the newConnections with appropriate number of connections
		for (int i = 0; i < 5; i++) 
		{
			newConnections.add(DriverManager.getConnection(url, username, password));
		}
	}
	// Methods
	public static ConnectionPool getInstance() throws SQLException {
		if  (pool == null) {
			pool = new ConnectionPool();
		}
		return pool;
	}
	// Returns a connection, as soon as there is at least one available
	public  synchronized Connection getConnection() throws GetConnectionWaitInterupted {
		// While there are no available connections - wait
		while (newConnections.iterator().hasNext()==false) {
			try {
				wait();
			}catch (InterruptedException e) {
				throw new GetConnectionWaitInterupted();
			}
		}
		// If there is a connection available return it
		Connection conn = newConnections.iterator().next();
		newConnections.remove(conn);
		usedConnections.add(conn);
		return conn;
	}
	// Return connection to the pool
	public synchronized void returnConnection(Connection connection) {
		newConnections.add(connection);
		usedConnections.remove(connection);
		notifyAll();	
	}
	// Closes and returns all connection to the pool
	public void closeAllConnections() throws InterruptedException, SQLException {

		while(usedConnections.iterator().hasNext()){
			usedConnections.iterator().next().close();
		}
		while(newConnections.iterator().hasNext()){
			newConnections.iterator().next().close();
		}
	}


}
