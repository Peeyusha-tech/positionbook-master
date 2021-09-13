package com.bank.trading.positionbook.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PositionBook implements Serializable {

	private static final long serialVersionUID = 7016250068090326365L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PositionBook.class);

	private Map<String, Account> accountMap = new ConcurrentHashMap<>();

	public void processTradeRequest(RequestModel requestModel) {

		if (null == requestModel.getTrades() || requestModel.getTrades().isEmpty()) {
			LOGGER.warn("The Trades count was {}. This cannot be empty", requestModel.getTrades());
			return;
		}
		List<Trade> trades = requestModel.getTrades();
		for (Trade trade : trades) {

			// Get All the trade details from Request

			String accountId = trade.getAccount();
			String securityName = trade.getSecurityName();
			Integer tradedAmount = trade.getTradedAmount();
			Integer tradeId = trade.getId();
			String tradeType = trade.getTradeType();

			// Post it to the account
			if (!accountMap.containsKey(accountId)) {
				// If Account does not exist then create one
				accountMap.put(accountId, new Account());
			}
			Account account = accountMap.get(accountId);
			account.getTradedSecurities().add(new TradedSecurity(accountId, tradeId, tradeType, securityName, tradedAmount));
			account.setAccountId(accountId);
			account.setSummary(new AccountSummary(account));
		}
	}

	public void printSummary(String account) {
		if (!accountMap.containsKey(account)) {
			throw new RuntimeException("Account number " + account + " not found in the book");
		}
		accountMap.get(account).getSummary().print();
	}
	
	public AccountSummary getSummary(String account) {
		if (!accountMap.containsKey(account)) {
			throw new RuntimeException("Account number " + account + " not found in the book");
		}
		return accountMap.get(account).getSummary();
	}

	public void printSummary() {
		for (Entry<String, Account> entry : accountMap.entrySet()) {
			printSummary(entry.getKey());
		}
	}

}
