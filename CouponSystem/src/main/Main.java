package main;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import objects.*;
import threads.DailyCouponExpirationTask;
import dbAccess.*; 
import facades.*;

// comment
public class Main
{
	//Connection connection=new Connection();
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception
	{	
		// Connection required
		CouponDBDAO coupDB = new CouponDBDAO();
		CustomerFacade testFacade = new CustomerFacade();
		
		// Login
		testFacade.login("Shay", "123456");
		
		// Prepare some constants
		double price = 1.0;
		long couponID = 2L;
		Coupon coupon = coupDB.getCoupon(couponID);
		
		// Check pre-manipulation status
		System.out.println(coupon.toString());
System.out.println("-nothing-");
		testFacade.getAllPurchasedCoupons();
System.out.println("-update-");	
/*		// Manipulate
		testFacade.purchaseCoupon(coupon);*/
		
	}
	}	
	

