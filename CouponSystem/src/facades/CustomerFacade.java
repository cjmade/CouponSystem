package facades;


import java.util.ArrayList;
import java.util.Collection;
import objects.CouponType;
import objects.Customer;
import objects.Coupon;
import dbAccess.CouponDBDAO;
import dbAccess.CustomerDBDAO;

public class CustomerFacade implements ClientFacade {
	// Save Customer Data
	private Customer cust = new Customer();
	// Create Data Base connections:
	// CustomerDBDAO is only used to read customers
	private CustomerDBDAO custDBDAO;
	// CouponDBDAO is only used to read coupons
	private CouponDBDAO coupDBDAO;

	// Constructor
	public CustomerFacade() {
		// Instantiate db connections
		try {
			custDBDAO = new CustomerDBDAO();
			coupDBDAO = new CouponDBDAO();
		} catch (Exception e) {
			System.out.println("DB connection error");
		}
	}

	// Purchase coupon
	// TODO
	// Need to add function to coupons

	@Override
	public ClientFacade login(String name, String password)
			throws InvalidLoginException {

		// Check if Customer with this name exists
		ArrayList<Customer> allCustomers = null;
		try {
			allCustomers = (ArrayList<Customer>) custDBDAO.getAllCustomers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Customer existingCoustomer : allCustomers) {
			// If such Customer exist and password is right - return
			// CustomerFacade
			if (existingCoustomer.getCustName().equals(name)
					&& existingCoustomer.getPassword().equals(password)) {
				// Store Customer data for a session
				cust.setId((new Customer(name)).getId());
				cust.setCustName(name);
				cust.setPassword(password);
				// Return facade
				return new CustomerFacade();
			}
		}
		// If such company wasn't found - throw exception
		throw new InvalidLoginException();
	}

	public void purchaseCoupon(Coupon coupon) {
		try {
			custDBDAO.purchaseCoupon(cust, coupon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getAllPurchasedCoupons() throws Exception {
		// save coupons here
		ArrayList<Coupon> coupons = null;
		// check if there are coupons on the customer db and prints the correct
		// massage
		if ((coupons = (ArrayList<Coupon>) custDBDAO.getCoupons(cust)) != null) {
			System.out.println(cust.getCustName() + " bought those coupons:");
			for (Coupon coupon : coupons) {
				System.out.println(coupon.toString());
			}
		} else {
			System.out.println(cust.getCustName() + "didn't buy nothing yet");
		}
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type)
			throws Exception {
		// get the list for all coupons from this type
		ArrayList<Coupon> AllCouponsByType = (ArrayList<Coupon>) coupDBDAO
				.getCouponByType(type);
		// get the list for all coupons for this customer
		ArrayList<Coupon> customerCoupons = (ArrayList<Coupon>) custDBDAO
				.getCoupons(cust);
		// new list for for all coupons from the same type for this customer
		ArrayList<Coupon> CouponsByType = new ArrayList<Coupon>();
		for (Coupon coupon : customerCoupons) {
			if (AllCouponsByType.contains(coupon)) {
				CouponsByType.add(coupon);
			}
		}
		System.out.println(CouponsByType.toString());
		return CouponsByType;
	}

	// Returns Collection of coupons purchased by customer
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price)
			throws Exception {
		// get the list for all coupons from this price
		ArrayList<Coupon> AllCouponsByPrice = (ArrayList<Coupon>) coupDBDAO
				.getCouponByPrice(price);
		// get the list for all coupons for this customer
		ArrayList<Coupon> customerCoupons = (ArrayList<Coupon>) custDBDAO
				.getCoupons(cust);
		// new list for for all coupons from the same price for this customer
		ArrayList<Coupon> CouponsByPrice = new ArrayList<Coupon>();
		for (Coupon coupon : customerCoupons) {
			if (AllCouponsByPrice.contains(coupon)) {
				CouponsByPrice.add(coupon);
			}
		}
		return CouponsByPrice;
	}
}
