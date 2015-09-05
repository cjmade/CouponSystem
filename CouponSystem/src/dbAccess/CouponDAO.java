package dbAccess;

import java.util.Collection;

import exceptions.ClosedConnectionStatementCreationException;
import exceptions.ConnectionCloseException;
import exceptions.ConnectionReceivedAfterWaiting;
import exceptions.FailedToCreateCouponException;
import objects.Coupon;
import objects.CouponType;

public interface CouponDAO 
{
	void createCoupon(Coupon Coupon) throws ConnectionReceivedAfterWaiting, ConnectionCloseException, FailedToCreateCouponException;
	void removeCoupon(Coupon Coupon) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException  ;
	void updateCoupon(Coupon Coupon) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException  ;
	Coupon getCoupon(long id) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException  ;
	Collection<Coupon> getAllCoupons() throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException  ;
	Collection<Coupon> getCouponByType(CouponType couponType) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException ;
	Collection<Coupon> getCouponByPrice(double price) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException  ;
	Collection<Coupon> getCouponTillDate(String date) throws ConnectionReceivedAfterWaiting, ClosedConnectionStatementCreationException, ConnectionCloseException ;
}
