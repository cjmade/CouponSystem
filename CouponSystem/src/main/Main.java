package main;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import objects.*;
import system.CouponSystem;
import threads.DailyCouponExpirationTask;
import dbAccess.*; 
import facades.*;

// comment
public class Main
{
	
	
	public static void main(String[] args) throws Exception
	{	
		CouponDBDAO coupDB = new CouponDBDAO();
		CustomerDBDAO custDB=new CustomerDBDAO();
		CompanyDBDAO compDB=new CompanyDBDAO();
		
		CustomerFacade custFacade = new CustomerFacade();
		CompanyFacade compFacade=new CompanyFacade();
		AdminFacade admin=new AdminFacade();
		
		Customer cust=new Customer("Shay");
		//cust.setId(1);
		Company comp= new Company();
		Coupon coup=new Coupon();
		
		
		CouponSystem coupon= CouponSystem.getInstance();
		custFacade=(CustomerFacade) coupon.login("Shay", "123456", "customer");
		//custFacade.getAllPurchasedCoupons();
		//custFacade.getAllPurchasedCouponsByPrice(45.0);
		custFacade.getAllPurchasedCouponsByType(CouponType.FOOD);
		//System.out.println(custDB.getCustomer("Shay").toString());
		//System.out.println(custDB.getCoupons(cust).toString());


		
		
		
	}
	}	
	

