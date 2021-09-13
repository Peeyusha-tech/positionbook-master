package com.bank.trading.positionbook.model;

import java.util.HashSet;
import java.util.Set;

public class Account {

	private String accountId;
	private Set<TradedSecurity> tradedSecurities = new HashSet<>();
	private AccountSummary summary;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public AccountSummary getSummary() {
		return summary;
	}

	public void setSummary(AccountSummary summary) {
		this.summary = summary;
	}

	public Set<TradedSecurity> getTradedSecurities() {
		return tradedSecurities;
	}
}
