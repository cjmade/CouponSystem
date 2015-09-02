package objects;

import java.util.ArrayList;


public class Customer {
	// Customer Attributes
	// id is assigned automatically at writing to db
	private long id;
	private String custName = null;
	private String password = null;
	private ArrayList<Coupon> coupons = null;
	
	// Constructor
	public Customer(){
	}
	public Customer(String custName)
	{
		this.setCustName(custName);
	}
	// Methods
	public void setId(long id)
	{
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	@Override
	public String toString() {
		return "Customer ["
				+ "id=" + id 
				+ ", custName=" + custName 
				+ ", password="	+ password 
				+ ", coupons=" + coupons 
				+ "]";
	}
}
