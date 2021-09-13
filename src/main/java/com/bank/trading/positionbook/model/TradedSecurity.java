package com.bank.trading.positionbook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class TradedSecurity {

	@Override
	public String toString() {
		return String.format("[%s, %s, %s, %s, %d]", this.getTradeId(), this.getTradeType(), this.getAccount(), this.getSecurityName(), this.getTradedAmount());
	}

	private String account;
	private Integer tradeId;
	private String tradeType;
	private String securityName;
	private Integer tradedAmount;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((securityName == null) ? 0 : securityName.hashCode());
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		result = prime * result + ((tradeType == null) ? 0 : tradeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradedSecurity other = (TradedSecurity) obj;
		if (securityName == null) {
			if (other.securityName != null)
				return false;
		} else if (!securityName.equals(other.securityName))
			return false;
		if (tradeId == null) {
			if (other.tradeId != null)
				return false;
		} else if (!tradeId.equals(other.tradeId))
			return false;
		if (tradeType == null) {
			if (other.tradeType != null)
				return false;
		} else if (!tradeType.equals(other.tradeType))
			return false;
		return true;
	}
}
