package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import objects.*;
import dbAccess.*;
import exceptions.*;

@SuppressWarnings("unused")
public class CompanyFacade implements ClientFacade {
	// Prepare company to store for a session
	private Company currentCompany = new Company();
	// CouponDBDAO to manipulate coupons
	private CouponDBDAO coupDBDAO;
	// CompanyDBDAO to add to COMPANY_COUPON
	private CompanyDBDAO compDBDAO;

	// Constructor
	public CompanyFacade() throws DatabaseAccessError 
	{
		// Instantiate db connections
		try
		{
			coupDBDAO = new CouponDBDAO();
			compDBDAO = new CompanyDBDAO();
		}catch(SQLException e)
		{
			System.out.println(e.getMessage() + ", failed to connect");
		}
	}
	// Methods
	// Login method, on successful login returns ClientFacade object
	// or throws an exception
	@Override
	public ClientFacade login(String name, String password) throws DatabaseAccessError {
		// TODO
		// Check if company with this name exists
		ArrayList<Company> allCompanies = null;
		try	{
			allCompanies = (ArrayList<Company>) compDBDAO.getAllCompanies();
			// Look for login information in DB
			for (Company existingCompany : allCompanies) {
				// If such company exist and password is right - return CompanyFacade
				if (existingCompany.getCompName().equals(name)
						&& existingCompany.getPassword().equals(password)) 
				{
					// Store Company data for a session
					currentCompany.setId((new Company(name)).getId());
					currentCompany.setCompName(name);
					currentCompany.setPassword(password);
					break;
				}
			}
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", login attempt failed because of error");
		}
		// Return facade or null
		return this;
	}
	// Create new coupon
	public void createCoupon(Coupon newCoupon) throws ConnectionCloseException, ClosedConnectionStatementCreationException, SQLException
	{
		try	{
			currentCompany = compDBDAO.getCompany(currentCompany.getCompName());
			
			coupDBDAO.createCoupon(newCoupon);
			Coupon coup=new Coupon();
			coup=coupDBDAO.getCoupon(newCoupon.getTitle());
			compDBDAO.addCoupon(currentCompany, coup);
		}catch(WaitingForConnectionInterrupted | FailedToCreateCouponException e)	{
			System.out.println(e.getMessage() + ", failed to create coupon");
		}
		
	}
	// Removes coupon, if it exists
	public void removeCoupon(Coupon coupon)
	{
		try	{
			coupDBDAO.removeCoupon(coupon);
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", failed to remove coupon");
		}
	}
	// Update existing coupon
	public void updateCoupon(Coupon coupon)
	{
		try	{
			coupDBDAO.updateCoupon(coupon);
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", failed to update coupon");
		}
	}
	// Find Coupon by id, in company's coupons
	public Coupon getCoupon(int id) 
	{
		Coupon coupon = null;
		try	{
			coupon = coupDBDAO.getCoupon(id);
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)		{
			System.out.println(e.getMessage() + ", failed to update coupon");
		}
		return coupon;
	}
	// Returns Collection<Coupon> of all existing coupons of the company
	public Collection<Coupon> getAllCoupons() 
	{
		ArrayList<Coupon> allCoupons = null;
		try	{
			currentCompany = compDBDAO.getCompany(currentCompany.getCompName());
			allCoupons = (ArrayList<Coupon>) compDBDAO.getCoupons(currentCompany);
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", failed to get coupons");
		}
		return allCoupons;
	}
	// Returns Collection<Coupon> of all existing coupons of the company of a certain type
	public Collection<Coupon> getCouponByType(CouponType type) 
	{
		ArrayList<Coupon> CouponsByType = null;
		try	{
			currentCompany = compDBDAO.getCompany(currentCompany.getCompName());
			ArrayList<Coupon> AllCouponsByType = (ArrayList<Coupon>) coupDBDAO.getCouponByType(type);
			ArrayList<Coupon> companyCoupons = (ArrayList<Coupon>) currentCompany.getCoupons();
			CouponsByType = new ArrayList<Coupon>();
			for (Coupon coupon : companyCoupons) {
				if (AllCouponsByType.contains(coupon)) {
					CouponsByType.add(coupon);
				}
			}
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", failed to get coupons collection");
		}
		return CouponsByType;
	}
	// Returns Collection<Coupon> of all existing coupons of the company valid until date
	public ArrayList<Coupon> getCouponTillDate(String date) 
	{
		ArrayList<Coupon> CouponsByDate = null;
		try
		{
			currentCompany = compDBDAO.getCompany(currentCompany.getCompName());
			ArrayList<Coupon> AllCouponsByDate = (ArrayList<Coupon>) coupDBDAO.getCouponTillDate(date);
			ArrayList<Coupon> companyCoupons = (ArrayList<Coupon>) currentCompany.getCoupons();
			CouponsByDate = new ArrayList<Coupon>();
			for (Coupon coupon : companyCoupons) 
			{
				if (AllCouponsByDate.contains(coupon)) 
				{
					CouponsByDate.add(coupon);
				}
			}
		}catch(WaitingForConnectionInterrupted
				| ClosedConnectionStatementCreationException
				| ConnectionCloseException e)	{
			System.out.println(e.getMessage() + ", failed to get coupons");
		}
		return CouponsByDate;
	}
}
