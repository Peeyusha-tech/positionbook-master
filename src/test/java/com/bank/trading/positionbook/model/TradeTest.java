package com.bank.trading.positionbook.model;

import static com.bank.trading.positionbook.TradeTypes.BUY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.bank.trading.positionbook.TradeTypes;

class TradeTest {
	private Trade trade = new Trade();
	
	@Test
	void testCanCreateBlankTrade() {
		assertThat(trade).isNotNull();
	}

	@Test
	void testCanCreateBuyTrade() {
		this.trade = new Trade(1,BUY,"ACC1","SEC1",100); 
		assertThat(trade.getTradeType()).isNotNull().isEqualTo(TradeTypes.BUY);
	}
	
	@Test
	void testCanCreateSellTrade() {
		this.trade = new Trade();
		this.trade.setId(2);
		this.trade.setTradeType(TradeTypes.SELL);
		this.trade.setAccount("ACC2");
		this.trade.setSecurityName("SEC2");
		this.trade.setTradedAmount(50);
		
		assertThat(trade.getId()).isNotNull().isEqualTo(2);
		assertThat(trade.getTradeType()).isNotNull().isEqualTo(TradeTypes.SELL);
		assertThat(trade.getAccount()).isNotNull().isEqualTo("ACC2");
		assertThat(trade.getSecurityName()).isNotNull().isEqualTo("SEC2");
		assertThat(trade.getTradedAmount()).isNotNull().isEqualTo(50);
	}

}
