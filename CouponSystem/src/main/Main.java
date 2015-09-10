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
		///Company comp= new Company();
		//comp.setId(4);
		//comp.setPassword("55");
		//comp.setCompName("shay");
		
		Coupon coup=new Coupon();
		coup.setEndDate(java.sql.Date.valueOf("2015-08-08"));
		coup.setAmount(4);
		coup.setId(7);
		coup.setMessage("this is a coupon");
		coup.setPrice(45.0);
		coup.setStartDate(java.sql.Date.valueOf("2015-08-08"));
		coup.setTitle("ababa");
		coup.setImage("jkggjghkj");
		coup.setType(CouponType.PETS);
		//System.out.println(coup.toString());
		//compFacade.createCoupon(coup);
		//coupDB.createCoupon(coup);
		CouponSystem coupon= CouponSystem.getInstance();
		//custFacade=(CustomerFacade) coupon.login("shayfass", "123456", "customer");
		//custFacade.getAllPurchasedCoupons();
		//custFacade.getAllPurchasedCouponsByPrice(33.0);
		//custFacade.getAllPurchasedCouponsByType(CouponType.FOOD);
		//custFacade.purchaseCoupon(coup);
		compFacade=(CompanyFacade) coupon.login("shayfass", "123456", "company");
		//compFacade.createCoupon(coup);
		compFacade.getCouponByType(CouponType.PETS);
		//compFacade.getCouponTillDate(date);
		//admin.login("admin", "1234");
		
		//compFacade.


		
		
		
	}
	}	
	

