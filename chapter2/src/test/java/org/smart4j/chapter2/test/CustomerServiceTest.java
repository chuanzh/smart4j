package org.smart4j.chapter2.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

public class CustomerServiceTest {

	private final CustomerService customerService;

	public CustomerServiceTest() {
		customerService = new CustomerService();
	}

	@Before
	public void init() {

	}

	@SuppressWarnings("deprecation")
	@Test
	public void getCustomerListTest() throws Exception {
		List<Customer> cl = customerService.getCustomerList();
		Assert.assertEquals(2, cl.size());
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void getCustomerTest() throws Exception {
		long id = 1;
		Customer s = customerService.getCustomer(id);
		Assert.assertNotNull(s);
	}

	/**
	 * 
	 * @param fieldMap
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void createCustomerTest() throws Exception  {
		Map<String,Object> fm= new HashMap<String,Object>();
		fm.put("name", "customer100");
		fm.put("contact", "John");
		fm.put("telephone", "12457896321");
		boolean r=customerService.createCustomer(fm);
		Assert.assertTrue(r);

	}

	/**
	 * 
	 * @param fieldMap
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void updateCustomerTest() throws Exception   {
		Map<String,Object> fm= new HashMap<String,Object>();
		fm.put("contact", "Eric");
		boolean r=customerService.updateCustomer(fm);
		Assert.assertTrue(r);
	}

	/**
	 * 
	 * @param fieldMap
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void deleteCustomerTest() throws Exception  {
		long id =1;
		boolean r=customerService.deleteCustomer(id);
		Assert.assertTrue(r);
	}
}
