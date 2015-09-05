package dbAccess;


import java.util.Collection;

import exceptions.ClosedConnectionStatementCreationException;
import exceptions.ConnectionCloseException;
import exceptions.ConnectionReceivedAfterWaiting;
import exceptions.FailedToCreateCustomerException;
import objects.Coupon;
import objects.Customer;


public interface CustomerDAO {

	 void createCustomer(Customer customer) throws ConnectionReceivedAfterWaiting, FailedToCreateCustomerException, ConnectionCloseException;
	 void removeCustomer(Customer customer) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	 void updateCustomer(Customer customer) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	 Customer getCustomer(long id) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	 Collection<Customer> getAllCustomers() throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	 Collection<Coupon> getCoupons(Customer customer) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
	 boolean login(String custName,String password) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException;
}
