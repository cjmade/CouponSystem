package dbAccess;

import objects.Company;
import objects.Coupon;
import objects.CouponType;
import exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CompanyDBDAO implements CompanyDAO {
	// Connection attributes
	ConnectionPool pool = ConnectionPool.getInstance();

	// Constructor, throws SQLException, on failed connection attempt
	public CompanyDBDAO() throws SQLException {
	}
	// Creates new company, with unique name
	@Override
	public void createCompany(Company company) throws FailedToCreateCompanyException  {
		// Get connection
		Connection connection;
		try
		{
			connection = pool.getConnection();
		}catch(GetConnectionWaitInteruptedException e1)
		{
			//TODO
			throw new ConnectionReceivedAfterWaiting();
		}

		// Prepare SQL message to insert new company
		String insertSQL = "INSERT INTO APP.Company "
				+ "(EMAIL, PASSWORD, COMP_NAME, ID) VALUES" + "(?,?,?,?)";
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, company.getEmail());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getCompName());
			preparedStatement.setLong(4, company.getId());
			// Execute prepared Statement
			preparedStatement.executeUpdate();
			// Close statement connection
			preparedStatement.close();
		}catch(SQLException e)
		{
			throw new FailedToCreateCompanyException();
		}finally
		{
			// close connection
			pool.returnConnection(connection);
		}
		System.out.println(company.toString() + " was added to the table");
	}

	@Override
	public void removeCompany(Company company) throws SQLException, Exception {
		Connection connection = pool.getConnection();
		// Get company ID from DB
		String sqlRequest = "SELECT ID FROM APP.COMPANY WHERE COMP_NAME='"
				+ company.getCompName() + "'";
		Statement statement = connection.createStatement();
		ResultSet idFound = statement.executeQuery(sqlRequest);
		idFound.next();
		company.setId(idFound.getLong("ID"));
		// Prepare message to remove purchase history
		sqlRequest = "DELETE FROM APP.COMPANY_COUPON WHERE COMP_ID = "
				+ company.getId();
		// Remove all customer purchase history
		statement.execute(sqlRequest);
		// Prepare SQL message to remove the company
		sqlRequest = "DELETE FROM APP.COMPANY WHERE ID=" + company.getId();
		// Remove the company himself
		statement.execute(sqlRequest);
		System.out.println(company.toString() + " was deleted");
		idFound.close();
		statement.close();
		pool.returnConnection(connection);
	}

	@Override
	public void updateCompany(Company company) throws SQLException, Exception {
		Connection connection = pool.getConnection();
		Statement statement = connection.createStatement();
		{
			// Check that COMPANY with that name exists
			if (!statement
					.execute("SELECT COMP_NAME FROM APP.COMPANY WHERE COMP_NAME = '"
							+ company.getCompName() + "'")) {
				// If such company does not exist - throw exception
				throw new ObjectDontExistException();
			}
			// Prepare SQL message to remove the company
			String updateSQL = "UPDATE APP.COMPANY SET" + " COMP_NAME='"
					+ company.getCompName() + "', PASSWORD='"
					+ company.getPassword() + "', EMAIL='" + company.getEmail()
					+ "' WHERE ID=" + company.getId();
			// Remove the company
			statement.execute(updateSQL);

			System.out.println(company.toString() + " was updated");
			statement.close();
			pool.returnConnection(connection);
		}

	}

	@Override
	public Company getCompany(long id) throws SQLException, Exception {
		Connection connection = pool.getConnection();
		// Prepare SQL message to get the company by the id
		String sql = "SELECT * FROM APP.COMPANY WHERE ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		// getting the values into a result set
		ResultSet rs = preparedStatement.executeQuery();
		Company company = new Company();
		rs.next();
		company.setId(rs.getLong("ID"));
		company.setCompName(rs.getString("COMP_NAME"));
		company.setPassword(rs.getString("PASSWORD"));
		company.setEmail(rs.getString("EMAIL"));
		System.out.println(company.toString());
		rs.close();
		preparedStatement.close();
		pool.returnConnection(connection);
		return company;
	}

	@Override
	public Collection<Company> getAllCompanies() throws SQLException, Exception {
		Connection connection = pool.getConnection();
		// Find all companies IN DATABASE
		String sql = "SELECT * FROM APP.COMPANY ";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();
		ArrayList<Company> companies = new ArrayList<Company>();
		while (rs.next()) {
			Company company = new Company();
			company.setId(rs.getLong("ID"));
			company.setCompName(rs.getString("COMP_NAME"));
			company.setPassword(rs.getString("PASSWORD"));
			company.setEmail(rs.getString("EMAIL"));
			companies.add(company);
			System.out.println(company.toString());
		}
		rs.close();
		statement.close();
		pool.returnConnection(connection);
		return companies;
	}

	@Override
	public Collection<Coupon> getCoupons(Company company) throws SQLException,
			Exception {
		Connection connection = pool.getConnection();
		Statement statement = connection.createStatement();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		// If company was received without ID - set ID

		if (company.getId() == 0) {
			// Get company ID ResultSet
			ResultSet companySetFound = statement
					.executeQuery("SELECT ID, COMP_NAME, PASSWORD FROM APP.COMPANY WHERE COMP_NAME='"
							+ company.getCompName() + "'");
			companySetFound.next();
			company.setId(companySetFound.getLong("ID"));
			companySetFound.close();
		}

		// Find company by ID in JOIN TABLE IN DATABASE
		String sql = "SELECT * FROM (APP.COMPANY_COUPON inner join APP.COUPON on APP.COUPON.ID = APP.COMPANY_COUPON.COUPON_ID) "
				+ "WHERE COMP_ID=" + company.getId();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Coupon coupon = new Coupon();
			coupon.setAmount(rs.getInt("AMOUNT"));
			coupon.setEndDate(rs.getDate("END_DATE"));
			coupon.setId(rs.getLong("ID"));
			coupon.setImage(rs.getString("IMAGE"));
			coupon.setMessage(rs.getString("MESSAGE"));
			coupon.setPrice(rs.getDouble("PRICE"));
			coupon.setTitle(rs.getString("TITLE"));
			coupon.setStartDate(rs.getDate("START_DATE"));
			coupon.setType(CouponType.valueOf(rs.getString("COUPON_TYPE")));
			coupons.add(coupon);
			System.out.println(coupon.toString());
			coupons.add(coupon);

		}

		rs.close();
		statement.close();
		pool.returnConnection(connection);
		return coupons;
	}

	@Override
	public boolean login(String compName, String password) throws SQLException,
			Exception {
		Connection connection = pool.getConnection();
		Statement statement = connection.createStatement();
		// Find company by NAME in DATABASE
		ResultSet companyFound = statement
				.executeQuery("SELECT COMP_NAME,PASSWORD "
						+ "FROM APP.COMPANY WHERE COMP_NAME='" + compName + "'");
		// If company wasn't found - next() will throw EOFException
		companyFound.next();
		// Check the password, return true on success
		if (companyFound.getString("PASSWORD").equals(password)) {
			pool.returnConnection(connection);
			companyFound.close();
			statement.close();
			return true;
		}
		// if user was found, but password was wrong
		companyFound.close();
		statement.close();
		pool.returnConnection(connection);
		return false;
	}
}
