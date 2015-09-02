package facades;

import java.sql.SQLException;
import java.util.ArrayList;

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
	public CompanyFacade() throws SQLException {
		// Instantiate db connections
		coupDBDAO = new CouponDBDAO();
		compDBDAO = new CompanyDBDAO();
	}

	// Methods

	// Login method, on successful login returns ClientFacade object
	// or throws an exception
	@Override
	public ClientFacade login(String name, String password)
			throws InvalidLoginException, SQLException {
		// Check if company with this name exists
		ArrayList<Company> allCompanies = null;
		try {
			allCompanies = (ArrayList<Company>) compDBDAO.getAllCompanies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Company existingCompany : allCompanies) {
			// If such company exist and password is right - return
			// CompanyFacade
			if (existingCompany.getCompName().equals(name)
					&& existingCompany.getPassword().equals(password)) {
				// Store Company data for a session
				currentCompany.setId((new Company(name)).getId());
				currentCompany.setCompName(name);
				currentCompany.setPassword(password);
				// Return facade
				return new CompanyFacade();
			}
		}
		// If such company wasn't found - throw exception
		throw new InvalidLoginException();
	}

	// Create new coupon
	private void createCoupon(Coupon newCoupon) throws Exception {
		try {
			// add coupon to COUPON list
			coupDBDAO.createCoupon(newCoupon);
			// add coupon to COMPANY_COUPON
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Removes coupon, if it exists
	private void removeCoupon(Coupon coupon) throws Exception {
		boolean couponExist = false;
		// Create new AllayList of all existing coupons
		ArrayList<Coupon> allCoupons = getAllCoupons();
		// Check that newCoupon does not yet exist BY NAME
		for (Coupon existingCoupon : allCoupons) {
			if (existingCoupon.getTitle().equals(coupon.getTitle())) {
				couponExist = true;
				break;
			}
		}
		// if Coupon exists - remove coupons, remove coupon
		if (couponExist) {
			// TODO remove coupon from every customer, that bought it
			// Remove coupon
			coupDBDAO.removeCoupon(coupon);
		} else {
			// throw exception
			throw new ObjectDontExistException();
		}
	}

	// Update existing coupon
	private void updateCoupon(Coupon coupon) throws Exception {
		boolean couponExist = false;
		// Create new AllayList of all existing coupons
		ArrayList<Coupon> allCoupons = getAllCoupons();
		// Check that newCoupon does not yet exist BY NAME
		for (Coupon existingCoupon : allCoupons) {
			if (existingCoupon.getTitle().equals(coupon.getTitle())) {
				couponExist = true;
				break;
			}
		}
		// if Coupon exists - update it
		if (couponExist)
			coupDBDAO.updateCoupon(coupon);
		else {
			// throw exception
			throw new ObjectDontExistException();
		}
	}

	// Find Coupon by id, in company's coupons
	private Coupon getCoupon(int id) throws Exception {
		return coupDBDAO.getCoupon(id);
	}

	// Returns Collection<Coupon> of all existing coupons
	private ArrayList<Coupon> getAllCoupons() throws Exception {
		return (ArrayList<Coupon>) compDBDAO.getCoupons(currentCompany);
	}

	public ArrayList<Coupon> getCouponByType(CouponType type) throws Exception {
		ArrayList<Coupon> AllCouponsByType = (ArrayList<Coupon>) coupDBDAO
				.getCouponByType(type);
		ArrayList<Coupon> companyCoupons = currentCompany.getCoupons();
		ArrayList<Coupon> CouponsByType = new ArrayList<Coupon>();
		for (Coupon coupon : companyCoupons) {
			if (AllCouponsByType.contains(coupon)) {
				CouponsByType.add(coupon);
			}
		}
		return CouponsByType;

	}

/*	this seems to be unneeded
 * public ArrayList<Coupon> getCouponTillPrice(double price) throws Exception {
		ArrayList<Coupon> AllCouponsByPrice = (ArrayList<Coupon>) coupDBDAO
				.getCouponTillPrice(price);
		ArrayList<Coupon> companyCoupons = currentCompany.getCoupons();
		ArrayList<Coupon> CouponsByPrice = new ArrayList<Coupon>();
		for (Coupon coupon : companyCoupons) {
			if (AllCouponsByPrice.contains(coupon)) {
				CouponsByPrice.add(coupon);
			}
		}
		return CouponsByPrice;

	}*/

	public ArrayList<Coupon> getCouponTillDate(String date) throws Exception {
		ArrayList<Coupon> AllCouponsByDate = (ArrayList<Coupon>) coupDBDAO
				.getCouponTillDate(date);
		ArrayList<Coupon> companyCoupons = currentCompany.getCoupons();
		ArrayList<Coupon> CouponsByDate = new ArrayList<Coupon>();
		for (Coupon coupon : companyCoupons) {
			if (AllCouponsByDate.contains(coupon)) {
				CouponsByDate.add(coupon);
			}
		}
		return CouponsByDate;

	}
}
