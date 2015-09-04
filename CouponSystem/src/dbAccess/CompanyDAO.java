package dbAccess;

import java.sql.SQLException;
import java.util.Collection;

import exceptions.ObjectDontExistException;
import objects.Company;
import objects.Coupon;

public interface CompanyDAO {
	
	void createCompany(Company company) throws  Exception;
	void removeCompany(Company company) throws  Exception;
	void updateCompany(Company company) throws SQLException, Exception;
	Company getCompany(long id) throws SQLException, Exception;
	Collection<Company> getAllCompanies() throws SQLException, Exception;
	Collection<Coupon> getCoupons(Company company) throws SQLException, Exception;
	 boolean login(String compName,String password) throws SQLException, Exception;

}
