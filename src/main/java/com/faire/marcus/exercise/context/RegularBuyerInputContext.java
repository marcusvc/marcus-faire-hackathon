package com.faire.marcus.exercise.context;

import org.springframework.stereotype.Component;

@Component
public class RegularBuyerInputContext {
	
	private Integer minimumOrdersToTrigger;
	
	public void setMinimumOrdersToTrigger(Integer minimumOrdersToTrigger) {
		this.minimumOrdersToTrigger = minimumOrdersToTrigger;
	}
	
	public Integer getMinimumOrdersToTrigger() {
		return minimumOrdersToTrigger;
	}

}
