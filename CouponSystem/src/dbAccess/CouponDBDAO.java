package dbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


import exceptions.ObjectDontExistException;
import objects.Coupon;
import objects.CouponType;

public class CouponDBDAO implements CouponDAO {

	// Connection attributes
	ConnectionPool pool = ConnectionPool.getInstance();

	// Constructor, throws SQLException, on failed connection attempt
	public CouponDBDAO() throws SQLException {
	}

	// Adds coupon to a coupons list
	@Override
	public void createCoupon(Coupon coupon) throws Exception {
		Connection connection = pool.getConnection();
		Statement statement = connection.createStatement();
		// Prepare SQL message to insert new coupon
		String insertSQL = "INSERT INTO APP.COUPON (IMAGE,PRICE,MESSAGE,COUPON_TYPE,AMOUNT,END_DATE,START_DATE,TITLE,ID) VALUES ('"
				+ coupon.getImage()
				+ "', '"
				+ coupon.getPrice()
				+ "', '"
				+ coupon.getMessage()
				+ "', '"
				+ coupon.getType()
				+ "', '"
				+ coupon.getAmount()
				+ "', '"
				+ coupon.getEndDate()
				+ "', '"
				+ coupon.getStartDate()
				+ "', '"
				+ coupon.getTitle()
				+ "', '"
				+ coupon.getId() + "')";
		// Execute prepared Statement
		statement.execute(insertSQL);
		// LOG
		System.out.println(coupon.toString() + " was added to the table");
		statement.close();
		pool.returnConnection(connection);
	}

	@Override
	// Removes relevant rows from CUSTOMER_COUPON, COMPANY_COUPON as well as
	// Coupon itself
	public void removeCoupon(Coupon coupon) throws Exception {
		Connection connection = pool.getConnection();
		// Get coupon ID from DB
		String sqlRequest = "SELECT ID FROM APP.COUPON WHERE TITLE='"
				+ coupon.getTitle() + "'";
		Statement statement = connection.createStatement();
		ResultSet idFound = statement.executeQuery(sqlRequest);
		idFound.next();
		// coupon.setId(idFound.getLong("ID"));
		// Prepare message to remove from purchase history
		String removeSQL = "DELETE FROM APP.CUSTOMER_COUPON WHERE COUPON_ID ="
				+ coupon.getId();
		// Remove coupon from purchase history
		statement.execute(removeSQL);
		// Prepare message to remove from company's coupons
		removeSQL = "DELETE FROM APP.COMPANY_COUPON WHERE COUPON_ID="
				+ coupon.getId();
		// Remove coupon from company
		statement.execute(removeSQL);
		// Prepare SQL message to remove the Coupon
		removeSQL = "DELETE FROM APP.COUPON WHERE ID=" + coupon.getId();
		// Remove the Coupon himself
		statement.execute(removeSQL);
		System.out.println(coupon.toString() + " was deleted");
		idFound.close();
		statement.close();
		pool.returnConnection(connection);
	}

	@Override
	public void updateCoupon(Coupon coupon) throws Exception {
		Connection connection = pool.getConnection();
		// Statement statement = connection.createStatement();
		// Check that Coupon with that name exists
		// if (!statement.execute("SELECT ID FROM APP.COUPON WHERE ID = '"
		// + coupon.getId() + "'")) {
		// If such Coupon does not exist - throw exception
		// throw new ObjectDontExistException();
		// }
		// Prepare SQL message to remove the Coupon
		String updateSQL = "UPDATE APP.COUPON SET AMOUNT=?, ID=?,  MESSAGE=?, PRICE=?, TITLE=?, END_DATE=?, START_DATE=?, IMAGE=?,COUPON_TYPE=?  WHERE ID=?";
		PreparedStatement preparedStatement = connection
				.prepareStatement(updateSQL);
		preparedStatement.setInt(1, coupon.getAmount());
		preparedStatement.setLong(2, coupon.getId());
		preparedStatement.setString(3, coupon.getMessage());
		preparedStatement.setDouble(4, coupon.getPrice());
		preparedStatement.setString(5, coupon.getTitle());
		preparedStatement.setDate(6, (java.sql.Date) coupon.getEndDate());
		preparedStatement.setDate(7, (java.sql.Date) coupon.getStartDate());
		preparedStatement.setString(8, coupon.getImage());
		preparedStatement.setString(9, coupon.getType().name());
		preparedStatement.setLong(10, coupon.getId());
		
		// update the Coupon
		preparedStatement.execute();

		System.out.println(coupon.toString() + " was updated");
		// statement.close();
		pool.returnConnection(connection);
	}

	@Override
	public Coupon getCoupon(long id) throws Exception {
		Connection connection = pool.getConnection();
		Statement statement = connection.createStatement();
		// Check that Coupon with that name exists
		if (!statement.execute("SELECT ID FROM APP.COUPON WHERE ID=" + id)) {
			// If such Coupon does not exist - throw exception
			throw new ObjectDontExistException();
		}
		// Prepare SQL message to get the Coupon by the id
		String sql = "SELECT * FROM APP.COUPON WHERE ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		// getting the values into a result set
		ResultSet rs = preparedStatement.executeQuery();
		Coupon coupon = new Coupon();
		rs.next();
		coupon.setAmount(rs.getInt("AMOUNT"));
		coupon.setId(rs.getLong("ID"));
		coupon.setImage(rs.getString("IMAGE"));
		coupon.setMessage(rs.getString("MESSAGE"));
		coupon.setPrice(rs.getDouble("PRICE"));
		coupon.setTitle(rs.getString("TITLE"));
		coupon.setEndDate(rs.getDate("END_DATE"));
		coupon.setStartDate(rs.getDate("START_DATE"));
		coupon.setType(CouponType.valueOf(rs.getString("COUPON_TYPE")));
		rs.close();
		statement.close();
		preparedStatement.close();
		pool.returnConnection(connection);
		return coupon;
	}

	// Returns collection of all existing coupons
	@Override
	public Collection<Coupon> getAllCoupons() throws Exception {
		Connection connection = pool.getConnection();
		Statement statement = connection.createStatement();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		String sql = "SELECT * FROM APP.COUPON  ";
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			Coupon coupon = new Coupon();
			coupon.setAmount(rs.getInt("AMOUNT"));
			coupon.setType(CouponType.valueOf(rs.getString("COUPON_TYPE")));
			coupon.setEndDate(rs.getDate("END_DATE"));
			coupon.setId(rs.getLong("ID"));
			coupon.setImage(rs.getString("IMAGE"));
			coupon.setMessage(rs.getString("MESSAGE"));
			coupon.setPrice(rs.getDouble("PRICE"));
			coupon.setTitle(rs.getString("TITLE"));
			coupon.setStartDate(rs.getDate("START_DATE"));
			coupons.add(coupon);
			System.out.println(coupon.toString());
			coupons.add(coupon);
		}
		rs.close();
		statement.close();
		pool.returnConnection(connection);
		return coupons;
	}

	// Returns all existing coupons of a certain type
	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType)
			throws Exception {
		Connection connection = pool.getConnection();
		// Prepare ArrayList to return
		ArrayList<Coupon> allCouponsFound = new ArrayList<Coupon>();
		// Prepare sql request
		String sqlRequest = "SELECT * FROM APP.COUPON WHERE COUPON_TYPE='"
				+ couponType + "'";
		PreparedStatement statement = connection.prepareStatement(sqlRequest);
		// Get all coupons in a ResultSet
		ResultSet couponsFound = statement.executeQuery();
		// Move all coupons from ResultSet to an ArrayList
		while (couponsFound.next()) {
			// Prepare temp coupon
			Coupon tempCoupon = new Coupon();
			tempCoupon.setId(couponsFound.getLong("ID"));
			tempCoupon.setTitle(couponsFound.getString("TITLE"));
			tempCoupon.setStartDate(couponsFound.getDate("START_DATE"));
			tempCoupon.setEndDate(couponsFound.getDate("END_DATE"));
			tempCoupon.setAmount(couponsFound.getInt("AMOUNT"));
			tempCoupon.setType(CouponType.valueOf(couponsFound
					.getString("COUPON_TYPE")));
			tempCoupon.setMessage(couponsFound.getString("MESSAGE"));
			tempCoupon.setPrice(couponsFound.getDouble("PRICE"));
			// Add it to the Collection
			allCouponsFound.add(tempCoupon);
		}
		couponsFound.close();
		statement.close();
		pool.returnConnection(connection);
		// returns NULL, when no coupons found
		System.out.println(allCouponsFound.toString());
		return allCouponsFound;
	}

	// Returns all existing coupons of a certain price
	@Override
	public Collection<Coupon> getCouponByPrice(double price) throws Exception {
		Connection connection = pool.getConnection();
		// Prepare ArrayList to return
		ArrayList<Coupon> allCouponsFound = new ArrayList<Coupon>();
		// Prepare sql request
		String sqlRequest = "SELECT * FROM APP.COUPON WHERE PRICE=?";
		PreparedStatement statement = connection.prepareStatement(sqlRequest);
		statement.setDouble(1, price);
		// Get all coupons in a ResultSet
		ResultSet couponsFound = statement.executeQuery();
		// Move all coupons from ResultSet to an ArrayList
		while (couponsFound.next()) {
			// Prepare temp coupon
			Coupon tempCoupon = new Coupon();
			tempCoupon.setId(couponsFound.getLong("ID"));
			tempCoupon.setTitle(couponsFound.getString("TITLE"));
			tempCoupon.setStartDate(couponsFound.getDate("START_DATE"));
			tempCoupon.setEndDate(couponsFound.getDate("END_DATE"));
			tempCoupon.setAmount(couponsFound.getInt("AMOUNT"));
			tempCoupon.setType(CouponType.valueOf(couponsFound
					.getString("COUPON_TYPE")));
			tempCoupon.setMessage(couponsFound.getString("MESSAGE"));
			tempCoupon.setPrice(couponsFound.getDouble("PRICE"));
			// Add it to the Collection
			allCouponsFound.add(tempCoupon);
		}
		System.out.println(allCouponsFound.toString());
		couponsFound.close();
		statement.close();
		pool.returnConnection(connection);
		// returns NULL, when no coupons found
		return allCouponsFound;
	}

	// Returns all existing coupons of that their price is smaller than certain
	// price
	@Override
	public ArrayList<Coupon> getCouponTillDate(String date) throws Exception {
		Connection connection = pool.getConnection();
		// Prepare ArrayList to return
		ArrayList<Coupon> allCouponsFound = new ArrayList<Coupon>();
		// Prepare sql request
		String sqlRequest = "SELECT * FROM APP.COUPON WHERE END_DATE<=?";

		PreparedStatement statement = connection.prepareStatement(sqlRequest);

		statement.setDate(1, java.sql.Date.valueOf(date));
		// Get all coupons in a ResultSet
		ResultSet couponsFound = statement.executeQuery();
		// Move all coupons from ResultSet to an ArrayList
		while (couponsFound.next()) {
			// Prepare temp coupon
			Coupon tempCoupon = new Coupon();
			tempCoupon.setId(couponsFound.getLong("ID"));
			tempCoupon.setTitle(couponsFound.getString("TITLE"));
			tempCoupon.setStartDate(couponsFound.getDate("START_DATE"));
			tempCoupon.setEndDate(couponsFound.getDate("END_DATE"));
			tempCoupon.setAmount(couponsFound.getInt("AMOUNT"));
			tempCoupon.setType(CouponType.valueOf(couponsFound
					.getString("COUPON_TYPE")));
			tempCoupon.setMessage(couponsFound.getString("MESSAGE"));
			tempCoupon.setPrice(couponsFound.getDouble("PRICE"));
			// Add it to the Collection
			allCouponsFound.add(tempCoupon);
		}
		System.out.println(allCouponsFound.toString());
		couponsFound.close();
		statement.close();
		pool.returnConnection(connection);
		// returns NULL, when no coupons found
		return allCouponsFound;
	}
}
