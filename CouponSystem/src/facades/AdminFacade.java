package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dbAccess.*;
import objects.*;
import exceptions.*;

public class AdminFacade implements ClientFacade
{
	// Create Data Base connections:
	// CompanyDBDAO to manipulate companies
	private CompanyDBDAO compDBDAO;
	// CustomerDBDAO to manipulate customers
	private CustomerDBDAO custDBDAO;
	
	// Constructor
	public AdminFacade() throws DatabaseAccessError
	{
		// Instantiate db connections
		try
		{
			compDBDAO = new CompanyDBDAO();
			custDBDAO = new CustomerDBDAO();
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	/// Methods
	// Login method, on successful login returns ClientFacade object
	@Override
	public ClientFacade login(String name, String password) throws DatabaseAccessError
	{
		// If username/password wrong - throw exception
		if(name != "admin" || password != "1234")
		{
			System.out.println("Wrong user/passowrd combination, try again");
			return null;
		}
		// else - create and return new Facade Object
		return new AdminFacade();
	}
	// Create new company
	@SuppressWarnings("unused")
	private void createCompany(Company newCompany)
	{
		try	{
			compDBDAO.createCompany(newCompany);
		}catch(WaitingForConnectionInterrupted | FailedToCreateCompanyException e)	{
			System.out.print(e.getMessage() + ", company wasn't created");
		}
	}
	// Removes company and all its coupons, if company exists
	@SuppressWarnings("unused")
	private void removeCompany(Company company)
	{
		try	{
			compDBDAO.removeCompany(company);
		}catch(WaitingForConnectionInterrupted | ConnectionCloseException 
				| ClosedConnectionStatementCreationException e)	{
			System.out.print(e.getMessage() + ", company wasn't removed");
		}
	}
	// Update existing company
	@SuppressWarnings("unused")
	private void updateCompany(Company company)
	{
		try	{
			compDBDAO.updateCompany(company);
		}catch(NothingToUpdateException | WaitingForConnectionInterrupted 
				| ClosedConnectionStatementCreationException | UpdateDidNotExecuteException e)	{
			System.out.print(e.getMessage() + ", company wasn't updated");
		}
	}
	// Find Company by id
	@SuppressWarnings("unused")
	private Company getCompany(int id)
	{
		Company company = null;
		try	{
			company = compDBDAO.getCompany(id);
		}catch(WaitingForConnectionInterrupted 
				| ClosedConnectionStatementCreationException | ConnectionCloseException e)	{
			System.out.print(e.getMessage() + ", failed to get company");
		}
		return company;
	}
	// Returns Collection<Company> of all existing companies
	@SuppressWarnings("unused")
	private Collection<Company> getAllCompanies()
	{
		ArrayList<Company> allCompanies = null;
		try	{
			allCompanies = (ArrayList<Company>)compDBDAO.getAllCompanies();
		}catch(WaitingForConnectionInterrupted 
				| ClosedConnectionStatementCreationException | ConnectionCloseException e)	{
			System.out.print(e.getMessage() + ", failed to get companies collection");
		}
		return allCompanies;
	}
	// Create new Customer
	@SuppressWarnings("unused")
	private void createCustomer(Customer newCustomer)
	{
		try	{
			custDBDAO.createCustomer(newCustomer);
		}catch(WaitingForConnectionInterrupted
				| FailedToCreateCustomerException | ConnectionCloseException e)	{
			System.out.print(e.getMessage() + ", failed to create customer");
		}

	}
	// Removes customer and all his coupons, if exists
	@SuppressWarnings("unused")
	private void removeCustomer(Customer customer)
	{
		try	{
			custDBDAO.removeCustomer(customer);
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", failed to remove customer");
		}
	}
	// Update existing customer
	@SuppressWarnings("unused")
	private void updateCustomer(Customer customer)
	{
		try	{
			custDBDAO.updateCustomer(customer);
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", failed to update customer");
		}
	}
	// Find Customer by id
	@SuppressWarnings("unused")
	private Customer getCustomer(int id)
	{
		Customer customer = null;
		try	{
			customer = custDBDAO.getCustomer(id);
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + "failed to get customer");
		}
		return customer;
	}
	// Returns Collection<Customer> of all existing customers
	@SuppressWarnings("unused")
	private Collection<Customer> getAllCustomers()
	{
		ArrayList<Customer> allCustomers = null;
		try
		{
			allCustomers = (ArrayList<Customer>) custDBDAO.getAllCustomers();
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)
		{
			System.out.println(e.getMessage() + "failed to get customers collection");
		}
		return allCustomers;
	}
}
