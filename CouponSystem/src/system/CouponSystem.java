package system;

import java.sql.SQLException;

import dbAccess.ConnectionPool;
import threads.DailyCouponExpirationTask;
import exceptions.InvalidLoginException;
import facades.*;

public class CouponSystem {
	// Singleton
	private static CouponSystem system;
	private static ConnectionPool pool;
	private CustomerFacade cust = new CustomerFacade();
	private CompanyFacade comp = new CompanyFacade();
	private AdminFacade admin = new AdminFacade();
	 DailyCouponExpirationTask thread=new  DailyCouponExpirationTask();
	Thread task=new Thread(thread);
	// method getINstance
	public static CouponSystem getInstance() throws SQLException {
		if (system == null) {
			system = new CouponSystem();
		}
		return system;
	}

	private CouponSystem() throws SQLException {

	}

	public ClientFacade login(String name, String password, String ClientType)
			throws InvalidLoginException, SQLException {
		if (ClientType == "customer") {
			return cust.login(name, password);
		} else if (ClientType == "company") {
			return comp.login(name, password);
		} else
			return admin.login(name, password);

	}
	public void shutDown() throws InterruptedException, SQLException{
		pool.closeAllConnections();
		task.join();
		thread.stopTask();
		
	}
}
