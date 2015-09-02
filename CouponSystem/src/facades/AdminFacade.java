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
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Removes company and all its coupons, if company exists
	@SuppressWarnings("unused")
	private void removeCompany(Company company)
	{
		// Remove company
		try
		{
			compDBDAO.removeCompany(company);
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Update existing company
	@SuppressWarnings("unused")
	private void updateCompany(Company company) throws ObjectDontExistException
	{
		// update company
		try
		{
			compDBDAO.updateCompany(company);
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Find Company by id
	@SuppressWarnings("unused")
	private Company getCompany(int id)
	{
		try
		{
			return compDBDAO.getCompany(id);
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(ObjectDontExistException e) 
		{
			System.out.println("No such company");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if a company wasn't found
		return null;
	}
	// Returns Collection<Company> of all existing companies
	@SuppressWarnings("unused")
	private Collection<Company> getAllCompanies()
	{
		try
		{
			return compDBDAO.getAllCompanies();
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if there are no companies to return
		return null;
	}

	// Create new Customer
	@SuppressWarnings("unused")
	private void createCustomer(Customer newCustomer)
	{
		try
		{
			custDBDAO.createCustomer(newCustomer);
		}catch(SQLException e)
		{
			System.out.println("Customer already exist OR DB connection error");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Removes customer and all his coupons, if exists
	@SuppressWarnings("unused")
	private void removeCustomer(Customer customer)
	{
		// Remove customer
		try
		{
			custDBDAO.removeCustomer(customer);
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(NullPointerException e)
		{
			System.out.println("No such client found, nothing to remove");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Update existing customer
	@SuppressWarnings("unused")
	private void updateCustomer(Customer customer)
	{
		// if customer exists - update it
		try
		{
			custDBDAO.updateCustomer(customer);
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(ObjectDontExistException e)
		{
			System.out.println("No " + customer.getCustName() + " customer found, nothing to update");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Find Customer by id
	@SuppressWarnings("unused")
	private Customer getCustomer(int id)
	{
		try
		{
			return custDBDAO.getCustomer(id);
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// Returns Collection<Customer> of all existing customers
	@SuppressWarnings("unused")
	private Collection<Customer> getAllCustomers()
	{
		try
		{
			// Can be null!
			return custDBDAO.getAllCustomers();
		}catch(SQLException e)
		{
			System.out.println("DB connection error");
		}catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
