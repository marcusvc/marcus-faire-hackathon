package com.faire.marcus.exercise.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.faire.marcus.exercise.client.FaireClient;
import com.faire.marcus.exercise.context.MonthsInputContext;
import com.faire.marcus.exercise.context.RegularBuyerInputContext;
import com.faire.marcus.exercise.model.Address;
import com.faire.marcus.exercise.model.Order;
import com.faire.marcus.exercise.model.Orders;

@RunWith(SpringRunner.class)
public class BuyingReminderControllerTest {

	@MockBean
	private BuyingReminderController buyingReminderController;

	@MockBean
	private FaireClient faireClientMock;
	
	@MockBean
	private MonthsInputContext monthsInputContextMock;
	
	@MockBean
	private RegularBuyerInputContext regularBuyerInputContextMock;

	@Before
	public void setUp() throws Exception {
		buyingReminderController = new BuyingReminderController(faireClientMock, monthsInputContextMock, regularBuyerInputContextMock);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTwoCompaniesTwoOrdersEachOneCompanyReturnScenario() {
		Orders orders = buildTwoCompaniesTwoOrdersEachOneCompanyReturnScenario();
		
		Mockito.when(faireClientMock.consumesAllOrders()).thenReturn(orders);
		Mockito.when(monthsInputContextMock.getMinimumMonthsToTrigger()).thenReturn(2);
		Mockito.when(regularBuyerInputContextMock.getMinimumOrdersToTrigger()).thenReturn(2);
		
		List<String> reminders = buyingReminderController.collectReminders();
		assertNotNull(reminders);
		assertEquals(1, reminders.size());
		assertEquals("FAIRE", reminders.get(0));
	}
	
	@Test
	public void testTwoCompaniesTwoOrdersEachTwoCompanyReturnScenario() {
		Orders orders = buildTwoCompaniesTwoOrdersEachTwoCompanyReturnScenario();
		
		Mockito.when(faireClientMock.consumesAllOrders()).thenReturn(orders);
		Mockito.when(monthsInputContextMock.getMinimumMonthsToTrigger()).thenReturn(2);
		Mockito.when(regularBuyerInputContextMock.getMinimumOrdersToTrigger()).thenReturn(2);
		
		List<String> reminders = buyingReminderController.collectReminders();
		assertNotNull(reminders);
		assertEquals(2, reminders.size());
		assertTrue(reminders.contains("FAIRE"));
		assertTrue(reminders.contains("VANHACK"));
	}
	
	@Test
	public void testTwoCompaniesOneOrderEachNoCompanyReturnScenario() {
		Orders orders = buildTwoCompaniesOneOrderEachNoCompanyReturnScenario();
		
		Mockito.when(faireClientMock.consumesAllOrders()).thenReturn(orders);
		Mockito.when(monthsInputContextMock.getMinimumMonthsToTrigger()).thenReturn(2);
		Mockito.when(regularBuyerInputContextMock.getMinimumOrdersToTrigger()).thenReturn(2);
		
		List<String> reminders = buyingReminderController.collectReminders();
		assertNotNull(reminders);
		assertEquals(0, reminders.size());
	}

	private Orders buildTwoCompaniesTwoOrdersEachOneCompanyReturnScenario() {
		Orders orders = new Orders();

		Address addressFaire = buildAddress("FAIRE");
		Address addressVanhack = buildAddress("VANHACK");

		//has more than 1 order and the last one was more than 2 months ago
		Order orderFaire1 = buildOrder("20180324T234500.000Z", addressFaire);
		Order orderFaire2 = buildOrder("20181224T234500.000Z", addressFaire);

		//has more than 1 order and the last one was not more than 2 months ago
		Order orderVanhack1 = buildOrder("20180324T234500.000Z", addressVanhack);
		Order orderVanhack2 = buildOrder("20190224T234500.000Z", addressVanhack);
		
		List<Order> ordersList = Arrays.asList(orderFaire1, orderVanhack2, orderFaire2, orderVanhack1);
		
		orders.setOrders(ordersList);

		return orders;
	}
	
	private Orders buildTwoCompaniesTwoOrdersEachTwoCompanyReturnScenario() {
		Orders orders = new Orders();

		Address addressFaire = buildAddress("FAIRE");
		Address addressVanhack = buildAddress("VANHACK");

		//has more than 1 order and the last one was more than 2 months ago
		Order orderFaire1 = buildOrder("20180324T234500.000Z", addressFaire);
		Order orderFaire2 = buildOrder("20181224T234500.000Z", addressFaire);

		//has more than 1 order and the last one was not more than 2 months ago
		Order orderVanhack1 = buildOrder("20180324T234500.000Z", addressVanhack);
		Order orderVanhack2 = buildOrder("20180224T234500.000Z", addressVanhack);
		
		List<Order> ordersList = Arrays.asList(orderFaire1, orderVanhack2, orderFaire2, orderVanhack1);
		
		orders.setOrders(ordersList);

		return orders;
	}
	
	private Orders buildTwoCompaniesOneOrderEachNoCompanyReturnScenario() {
		Orders orders = new Orders();

		Address addressFaire = buildAddress("FAIRE");
		Address addressVanhack = buildAddress("VANHACK");

		//has more than 1 order and the last one was more than 2 months ago
		Order orderFaire1 = buildOrder("20180124T234500.000Z", addressFaire);

		//has more than 1 order and the last one was not more than 2 months ago
		Order orderVanhack1 = buildOrder("20180224T234500.000Z", addressVanhack);
		
		List<Order> ordersList = Arrays.asList(orderFaire1, orderVanhack1);
		
		orders.setOrders(ordersList);

		return orders;
	}

	private Order buildOrder(String updateAt, Address address) {
		Order orderFaire1 = new Order();
		orderFaire1.setUpdatedAt(updateAt);
		orderFaire1.setAddress(address);
		return orderFaire1;
	}

	private Address buildAddress(String companyName) {
		Address addressFaire = new Address();
		addressFaire.setCompanyName(companyName);
		return addressFaire;
	}

}
