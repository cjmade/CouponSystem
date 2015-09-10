package facades;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import objects.CouponType;
import objects.Customer;
import objects.Coupon;
import dbAccess.CouponDBDAO;
import dbAccess.CustomerDBDAO;
import exceptions.ClosedConnectionStatementCreationException;
import exceptions.ConnectionCloseException;
import exceptions.DatabaseAccessError;
import exceptions.WaitingForConnectionInterrupted;

public class CustomerFacade implements ClientFacade {
	// Save Customer Data
	private Customer cust;
	// Create Data Base connections:
	// CustomerDBDAO is only used to read customers
	private CustomerDBDAO custDBDAO;
	// CouponDBDAO is only used to read coupons
	private CouponDBDAO coupDBDAO;

	// Constructor
	public CustomerFacade() throws SQLException {
		// Instantiate db connections
		try {
			custDBDAO = new CustomerDBDAO();
			coupDBDAO = new CouponDBDAO();
			cust = new Customer();
		} catch (DatabaseAccessError e) {
			System.out.println(e.getMessage() + ", connection attempt failed");
		}
	}

	// Methods
	// Login
	@Override
	public ClientFacade login(String name, String password) {
		try {
			if (custDBDAO.login(name, password)) {
				this.cust = new Customer(name);
			}
		} catch (WaitingForConnectionInterrupted | ClosedConnectionStatementCreationException
				| ConnectionCloseException e) {
			System.out.println(e.getMessage() + ", login attempt failed");
		}
		return this;
	}

	// Purchase coupon
	public void purchaseCoupon(Coupon coupon) {
		try {
			custDBDAO.purchaseCoupon(cust, coupon);
		} catch (Exception e) {
			System.out.println(e.getMessage() + ", purchase failed");
		}
	}

	// Returns all coupons owned by the Customer
	public void getAllPurchasedCoupons() {
		// save coupons here
		ArrayList<Coupon> coupons = null;
		// check if there are coupons on the customer db and prints the correct
		// massage
		try {
			coupons = (ArrayList<Coupon>) custDBDAO.getCoupons(cust);
			if (coupons.isEmpty()) {
				System.out.println("You didn't buy any coupons yet");
				return;
			}
			System.out.println("You bought those coupons:");
			for (Coupon coupon : coupons) {
				System.out.println(coupon.toString());
			}
		} catch (WaitingForConnectionInterrupted | ClosedConnectionStatementCreationException
				| ConnectionCloseException e) {
			System.out.println(e.getMessage() + ", failed to get purchased coupons");
		}
	}

	// Returns all coupons of a certain type, purchased by the customer
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) {
		// get the list for all coupons from this type
		ArrayList<Coupon> CouponsByType = null;
		try {
			ArrayList<Coupon> AllCouponsByType = (ArrayList<Coupon>) coupDBDAO.getCouponByType(type);
			// get the list for all coupons for this customer
			cust = custDBDAO.getCustomer(cust.getCustName());
			ArrayList<Coupon> customerCoupons = (ArrayList<Coupon>) custDBDAO.getCoupons(cust);
			// new list for for all coupons from the same type for this customer
			CouponsByType = new ArrayList<Coupon>();
			for (Coupon coupon : customerCoupons) {

				if (AllCouponsByType.contains(coupon)) {
					CouponsByType.add(coupon);
				}
			}
		} catch (WaitingForConnectionInterrupted | ClosedConnectionStatementCreationException
				| ConnectionCloseException e) {
			System.out.println(e.getMessage() + ", failed to get coupons");
		}
		System.out.println(CouponsByType.toString());
		return CouponsByType;
	}

	// Returns Collection of coupons purchased by customer
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) {
		ArrayList<Coupon> CouponsByPrice = null;
		// get the list for all coupons from this price
		ArrayList<Coupon> AllCouponsByPrice;
		try {
			AllCouponsByPrice = (ArrayList<Coupon>) coupDBDAO.getCouponByPrice(price);
			// get the list for all coupons for this customer
			cust = custDBDAO.getCustomer(cust.getCustName());
			ArrayList<Coupon> customerCoupons = (ArrayList<Coupon>) custDBDAO.getCoupons(cust);
			// new list for for all coupons from the same price for this
			// customer
			CouponsByPrice = new ArrayList<Coupon>();
			for (Coupon coupon : customerCoupons) {
				if (AllCouponsByPrice.contains(coupon)) {
					CouponsByPrice.add(coupon);
				}
			}
		} catch (WaitingForConnectionInterrupted | ClosedConnectionStatementCreationException
				| ConnectionCloseException e) {
			System.out.println(e.getMessage() + ", failed to get coupons");
		}
		System.out.println(CouponsByPrice.toString());
		return CouponsByPrice;
	}
}
