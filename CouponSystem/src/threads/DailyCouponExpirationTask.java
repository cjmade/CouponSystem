package threads;

import dbAccess.CouponDBDAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Thread;
import objects.Coupon;

public class DailyCouponExpirationTask implements Runnable {

	private long millis = 60 * 60 * 24 * 1000;
	private CouponDBDAO coupDBDAO=new CouponDBDAO();

	public DailyCouponExpirationTask() throws SQLException{
		
	}
	@Override
	public void run() {
		try {
			dailyCouponsExpirationTask();
			System.out.println("going to sleep");
			Thread.sleep(millis);
			System.out.println("wake up");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void dailyCouponsExpirationTask() throws Exception {
		ArrayList<Coupon> allCouponsFound = new ArrayList<Coupon>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(date));
		allCouponsFound = coupDBDAO.getCouponTillDate(sdf.format(date));
		System.out.println(allCouponsFound.toString());
		for (Coupon coupon : allCouponsFound) {
			coupDBDAO.removeCoupon(coupon);

		}
	}

	public void stopTask() {
		Thread.currentThread().interrupt();
	}

}
