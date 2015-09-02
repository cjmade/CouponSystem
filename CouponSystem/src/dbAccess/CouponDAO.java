package dbAccess;

import java.sql.SQLException;
import java.util.Collection;


import exceptions.ObjectDontExistException;
import objects.Coupon;
import objects.CouponType;

public interface CouponDAO 
{
	void createCoupon(Coupon Coupon) throws SQLException, Exception;
	void removeCoupon(Coupon Coupon) throws SQLException, Exception;
	void updateCoupon(Coupon Coupon) throws SQLException, ObjectDontExistException, Exception;
	Coupon getCoupon(long id) throws SQLException, ObjectDontExistException, Exception;
	Collection<Coupon> getAllCoupons() throws SQLException, Exception;
	Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException, Exception;
	Collection<Coupon> getCouponByPrice(double price) throws SQLException, Exception;
	Collection<Coupon> getCouponTillDate(String date) throws Exception;
}
