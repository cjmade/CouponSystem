package objects;


import java.util.ArrayList;


public class Company {
	// Attributes
	private static long id;
	private String compName;
	private String password;
	private String email;
	private ArrayList<Coupon> coupons;

	// Constructors
	public Company() {
	}
	public Company(String compName)
	{
		setCompName(compName);
	}
	// Methods
	public long getId() {
		return id;
	}
	public void setId(long id) {
		if (id>0) {
			Company.id = id++;
		}
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		if (compName != null) {
			this.compName = compName;
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<Coupon> getCoupons()   {
		return coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	@Override
	public String toString() {
		return "Company ["
				+ "id=" + id 
				+ ", compName=" + compName 
				+ ", password=" + password 
				+ ", email=" + email 
				+ ", coupons=" + coupons 
				+ "]";
	}
}
