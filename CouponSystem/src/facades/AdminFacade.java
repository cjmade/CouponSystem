package facades;

import java.sql.SQLException;
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
	public AdminFacade()
	{
		// Instantiate db connections
		try
		{
			compDBDAO = new CompanyDBDAO();
			custDBDAO = new CustomerDBDAO();
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}
	}
	
	/// Methods

	// Login method, on successful login returns ClientFacade object
	@Override
	public ClientFacade login(String name, String password)
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
		try
		{
			compDBDAO.createCompany(newCompany);
		}catch(WaitingForConnectionInterrupted | FailedToCreateCompanyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Removes company and all its coupons, if company exists
	@SuppressWarnings("unused")
	private void removeCompany(Company company)
	{
		compDBDAO.removeCompany(company);

	}
	// Update existing company
	@SuppressWarnings("unused")
	private void updateCompany(Company company) throws ObjectDontExistException
	{
		compDBDAO.updateCompany(company);
	}
	// Find Company by id
	@SuppressWarnings("unused")
	private Company getCompany(int id)
	{
		return compDBDAO.getCompany(id);
	}
	// Returns Collection<Company> of all existing companies
	@SuppressWarnings("unused")
	private Collection<Company> getAllCompanies()
	{
		return compDBDAO.getAllCompanies();
	}
	// Create new Customer
	@SuppressWarnings("unused")
	private void createCustomer(Customer newCustomer)
	{
		custDBDAO.createCustomer(newCustomer);
	}
	// Removes customer and all his coupons, if exists
	@SuppressWarnings("unused")
	private void removeCustomer(Customer customer)
	{
		custDBDAO.removeCustomer(customer);
	}
	// Update existing customer
	@SuppressWarnings("unused")
	private void updateCustomer(Customer customer)
	{
		custDBDAO.updateCustomer(customer);
	}
	// Find Customer by id
	@SuppressWarnings("unused")
	private Customer getCustomer(int id)
	{
		return custDBDAO.getCustomer(id);
	}
	// Returns Collection<Customer> of all existing customers
	@SuppressWarnings("unused")
	private Collection<Customer> getAllCustomers()
	{
		// Can be null!
		return custDBDAO.getAllCustomers();
	}
}
