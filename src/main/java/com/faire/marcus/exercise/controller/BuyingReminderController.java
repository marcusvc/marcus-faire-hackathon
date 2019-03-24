package com.faire.marcus.exercise.controller;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.faire.marcus.exercise.client.FaireClient;
import com.faire.marcus.exercise.context.MonthsInputContext;
import com.faire.marcus.exercise.context.RegularBuyerInputContext;
import com.faire.marcus.exercise.model.Order;
import com.faire.marcus.exercise.model.Orders;
import com.faire.marcus.exercise.util.ZoneDateTimeUtils;

@Component
public class BuyingReminderController {
	
	private FaireClient faireClient;
	private MonthsInputContext monthsInputContext;
	private RegularBuyerInputContext regularBuyerInputContext;
	
	public BuyingReminderController(FaireClient faireClient, MonthsInputContext monthsInputContext,
			RegularBuyerInputContext regularBuyerInputContext) {
		this.faireClient = faireClient;
		this.monthsInputContext = monthsInputContext;
		this.regularBuyerInputContext = regularBuyerInputContext;
	}
	
	public List<String> collectReminders() {
		Orders orders = faireClient.consumesAllOrders();
		Map<String, List<Order>> companyOrderMap = mapCompanyOrder(orders);
		return companyOrderMap.entrySet().stream()
				.filter(this::isRegularBuyer) // Filter regular buyers
				.filter(this::hasBeenAway)
				.flatMap(entry -> Stream.<String>of(entry.getKey()))
				.collect(Collectors.toList());
	}
	
	private Map<String, List<Order>> mapCompanyOrder(Orders orders) {
		Map<String, List<Order>> companyOrderMap = new HashMap<>();
		orders.getOrders().stream()
		//.filter(order -> "DELIVERED".equals(order.getState())) // Commented just to increase orders amount
		.forEach(order -> {
			if (!companyOrderMap.containsKey(order.getAddress().getCompanyName())) {
				companyOrderMap.put(order.getAddress().getCompanyName(), new ArrayList<>());
			}
			companyOrderMap.get(order.getAddress().getCompanyName()).add(order);
		});
		return companyOrderMap;
	}
	
	private boolean isRegularBuyer(Map.Entry<String, List<Order>> entry) {
		return entry.getValue().size() >= regularBuyerInputContext.getMinimumOrdersToTrigger();
	}
	
	private boolean hasBeenAway(Map.Entry<String, List<Order>> entry) {
		Optional<Order> lastOrder = getLastOrder(entry.getValue());
		if (lastOrder.isPresent()) {
			LocalDateTime lastOrderUpdatedAt = ZoneDateTimeUtils.toLocalDate(lastOrder.get().getUpdatedAt());
			LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
			Period period = Period.between(lastOrderUpdatedAt.toLocalDate().withDayOfMonth(1), now.toLocalDate().withDayOfMonth(1));
			return period.getMonths() >= monthsInputContext.getMinimumMonthsToTrigger() || period.getYears() > 0;
		}
		return false;
	}

	private Optional<Order> getLastOrder(List<Order> orders) {
		return orders.stream().max(Comparator.comparing(this::orderUpdatedDate));
	}
	
	private LocalDateTime orderUpdatedDate(Order order) {
		return ZoneDateTimeUtils.toLocalDate(order.getUpdatedAt());
	}

}
