package com.bank.trading.positionbook.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.bank.trading.positionbook.TradeTypes;

class TradedSecurityTest {
	private TradedSecurity sec1 = new TradedSecurity("ACC1",1,TradeTypes.BUY,"SEC1",100);
	private TradedSecurity sec2 = new TradedSecurity("ACC1",1,TradeTypes.BUY,"SEC1",50);
	private TradedSecurity sec3 = new TradedSecurity("ACC1",1,TradeTypes.SELL,"SEC1",50);
	private TradedSecurity sec4 = new TradedSecurity("ACC1",1,TradeTypes.BUY,"SEC2",50);
	
	@Test
	void testEquals() {
		assertThat(sec1).isEqualTo(sec2);
		assertThat(sec1).isNotEqualTo(sec3); // TRADE TYPE IS NOT SAME
		assertThat(sec1).isNotEqualTo(sec4); // SEC NAME IS NOT SAME
	}
	
	@Test
	void testEqualOther() {
		assertThat(sec1).isEqualTo(sec1); 
		assertThat(sec1).isNotEqualTo(null); 
		assertThat(sec1).isNotEqualTo(new ArrayList<>());
	}

}
