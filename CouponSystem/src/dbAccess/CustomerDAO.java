package dbAccess;

import java.io.EOFException;
import java.sql.SQLException;
import java.util.Collection;

import exceptions.ObjectAlreadyExistException;
import exceptions.ObjectDontExistException;
import objects.Coupon;
import objects.Customer;


public interface CustomerDAO {

	 void createCustomer(Customer customer) throws SQLException, ObjectAlreadyExistException, Exception;
	 void removeCustomer(Customer customer) throws SQLException, ObjectDontExistException, Exception;
	 void updateCustomer(Customer customer) throws SQLException, ObjectDontExistException, Exception;
	 Customer getCustomer(long id) throws SQLException, ObjectDontExistException, Exception;
	 Collection<Customer> getAllCustomers() throws SQLException, Exception;
	 Collection<Coupon> getCoupons(Customer customer) throws SQLException, Exception;
	 boolean login(String custName,String password) throws SQLException, EOFException, Exception;
}
