package dbAccess;

import java.sql.SQLException;
import java.util.Collection;

import exceptions.ClosedConnectionStatementCreationException;
import exceptions.ConnectionCloseException;
import exceptions.ConnectionReceivedAfterWaiting;
import exceptions.FailedToCreateCompanyException;
import exceptions.NothingToUpdateException;
import exceptions.UpdateDidNotExecuteException;
import objects.Company;
import objects.Coupon;

public interface CompanyDAO {
	
	void createCompany(Company company) throws ConnectionReceivedAfterWaiting, FailedToCreateCompanyException;
	void removeCompany(Company company) throws SQLException, Exception;
	void updateCompany(Company company) throws NothingToUpdateException, ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, UpdateDidNotExecuteException;
	Company getCompany(long id) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	Collection<Company> getAllCompanies() throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	Collection<Coupon> getCoupons(Company company) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	boolean login(String compName,String password) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;

}