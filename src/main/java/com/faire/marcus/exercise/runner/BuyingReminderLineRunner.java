package com.faire.marcus.exercise.runner;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.faire.marcus.exercise.context.MonthsInputContext;
import com.faire.marcus.exercise.context.RegularBuyerInputContext;
import com.faire.marcus.exercise.context.TokenInputContext;
import com.faire.marcus.exercise.controller.BuyingReminderController;

@Component
public class BuyingReminderLineRunner implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(BuyingReminderLineRunner.class);
	
	private TokenInputContext inputContext;
	private MonthsInputContext monthsInputContext;
	private	RegularBuyerInputContext regularBuyerInputContext;
	private BuyingReminderController buyingReminderController;
	
	public BuyingReminderLineRunner(TokenInputContext inputContext, MonthsInputContext monthsInputContext,
			RegularBuyerInputContext regularBuyerInputContext, BuyingReminderController buyingReminderController) {
		this.inputContext = inputContext;
		this.monthsInputContext = monthsInputContext;
		this.regularBuyerInputContext = regularBuyerInputContext;
		this.buyingReminderController = buyingReminderController;
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.debug("Command line running");
		if (args != null && args.length == 3) {
			inputContext.setToken(args[0]);
			monthsInputContext.setMinimumMonthsToTrigger(Integer.valueOf(args[1]));
			regularBuyerInputContext.setMinimumOrdersToTrigger(Integer.valueOf(args[2]));
			printCompaniesThatMustBeReminded(buyingReminderController.collectReminders());
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Expecting 3 parameters, access token, minimun months without buying and minimum orders to be considered a regular buyer. Input not acceptable: {}", Arrays.deepToString(args));
			}
		}
	}
	
	private void printCompaniesThatMustBeReminded(List<String> companyNames) {
		LOG.debug("Companies that must be reminded:");
		companyNames.stream().forEach(LOG::debug);
	}

}
