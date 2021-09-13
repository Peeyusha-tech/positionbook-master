package com.bank.trading.positionbook.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bank.trading.positionbook.TradeTypes;

public class AccountSummary {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountSummary.class);

	public AccountSummary(Account account) {
		this.account = account;
	}

	private Map<String, Integer> securityValueByNameMap = new ConcurrentHashMap<>();
	private Account account;

	public void print() {
		computeValues();
		for (Entry<String, Integer> entry : securityValueByNameMap.entrySet()) {
			LOGGER.info("{} {} {} ", getAccount().getAccountId(), entry.getKey(), entry.getValue());
			for (TradedSecurity tradedSecurity : getAccount().getTradedSecurities()) {
				if (tradedSecurity.getSecurityName().equals(entry.getKey())) {
					LOGGER.info("{}", tradedSecurity);
				}
			}
		}
	}

	private void computeValues() {
		this.securityValueByNameMap.clear();
		for (TradedSecurity tradedSecurity : getAccount().getTradedSecurities()) {
			String key = tradedSecurity.getSecurityName();
			if (!securityValueByNameMap.containsKey(key)) {
				securityValueByNameMap.put(tradedSecurity.getSecurityName(), 0);
			}
			Integer value = securityValueByNameMap.get(key);
			if (tradedSecurity.getTradeType().equals(TradeTypes.BUY)) {
				value += tradedSecurity.getTradedAmount();
			}
			if (tradedSecurity.getTradeType().equals(TradeTypes.SELL)) {
				value -= tradedSecurity.getTradedAmount();
			}
			if (tradedSecurity.getTradeType().equals(TradeTypes.CANCEL)) {
				TradedSecurity prevSecurity = findTradeById(tradedSecurity.getTradeId(), getAccount().getTradedSecurities()); // Find Prev Trade With Same id 
				Integer deductedValue = 0;
				if (prevSecurity.getTradeType().equals(TradeTypes.BUY)) {
					deductedValue -= prevSecurity.getTradedAmount(); // If the trade to be cancelled was buy then deduct the value
				}
				if (prevSecurity.getTradeType().equals(TradeTypes.SELL)) {
					deductedValue += prevSecurity.getTradedAmount(); // if the trade to be cancelled was sell then add the value
				}
				value += deductedValue;
			}
			securityValueByNameMap.put(key, value);
		}
	}

	private TradedSecurity findTradeById(Integer tradeId, Set<TradedSecurity> tradedSecurities) {
		TradedSecurity security = null;
		for (TradedSecurity tradedSecurity : tradedSecurities) {
			if (tradedSecurity.getTradeId().equals(tradeId)
					&& !tradedSecurity.getTradeType().equals(TradeTypes.CANCEL)) {
				security = tradedSecurity;
				break;
			}
		}
		return security;
	}

	public Integer getTotalValueBySec(String secName) {
		computeValues();
		if (!this.securityValueByNameMap.containsKey(secName)) {
			LOGGER.warn("The Account Does not contain Sec with name {}", secName);
			return 0;
		}
		return this.securityValueByNameMap.get(secName);
	}

	public Account getAccount() {
		return account;
	}
}
