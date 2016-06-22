package org.smart4j.chapter2.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.database.DBOperationAdapter;
import org.smart4j.chapter2.model.Customer;

public class CustomerService {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerService.class);

	private DBOperationAdapter dboperator = DBOperationAdapter.getInstance();

	/**
	 * 
	 * @param Keyword
	 * @return
	 */
	public List<Customer> getCustomerList(String... Keyword) {
		List<Customer> cusList = new ArrayList<Customer>();
		try {
			String querysql = "select * from customer";
			ResultSet rs = dboperator.executeQuery(querysql);
			while (rs.next()) {
				Customer ct = new Customer();
				ct.setId(rs.getLong("id"));
				ct.setName(rs.getString("name"));
				ct.setContact(rs.getString("contact"));
				ct.setEmail(rs.getString("email"));
				ct.setTelephone(rs.getString("telephone"));
				ct.setRemark(rs.getString("remark"));
				cusList.add(ct);
			}
		} catch (Exception e) {
			LOGGER.error("getCustomerList sql failure!", e);
		}
		return cusList;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Customer getCustomer(Long id) {
		return null;
	}

	/**
	 * 
	 * @param fieldMap
	 * @return
	 */
	public boolean createCustomer(Map<String, Object> fieldMap) {
		return false;

	}

	/**
	 * 
	 * @param fieldMap
	 * @return
	 */
	public boolean updateCustomer(Map<String, Object> fieldMap) {
		return false;

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteCustomer(long id) {
		return false;

	}
}
