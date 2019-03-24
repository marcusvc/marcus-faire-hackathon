package com.faire.marcus.exercise.context;

import org.springframework.stereotype.Component;

@Component
public class MonthsInputContext {
	
	private Integer minimumMonthsToTrigger;
	
	public void setMinimumMonthsToTrigger(Integer minimumMonthsToTrigger) {
		this.minimumMonthsToTrigger = minimumMonthsToTrigger;
	}
	
	public Integer getMinimumMonthsToTrigger() {
		return minimumMonthsToTrigger;
	}

}
