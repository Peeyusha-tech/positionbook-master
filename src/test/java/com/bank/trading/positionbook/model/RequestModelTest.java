package com.bank.trading.positionbook.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.bank.trading.positionbook.TradeTypes;

class RequestModelTest {

	private RequestModel model = new RequestModel();
	
	@Test
	void testCanCreateRequest() {
		assertThat(model).isNotNull();
	}

	@Test
	void testCanCreateRequestWithTrade() {
		model = new RequestModel();
		model.getTrades().add(new Trade(4, TradeTypes.BUY, "ACC1", "SEC1", 100));
		assertThat(model).isNotNull();
		assertThat(model.getTrades()).isNotNull().hasSize(1);
		assertThat(model.getTrades().get(0).getId()).isNotNull().isEqualTo(4);
		
	}
	@Test
	void testCanCreateRequestTradesInOneGo() {
		Trade trade = new Trade(4, TradeTypes.BUY, "ACC1", "SEC1", 100);
		model = new RequestModel(Collections.singletonList(trade));
		assertThat(model).isNotNull();
		assertThat(model.getTrades()).isNotNull().hasSize(1);
		assertThat(model.getTrades().get(0).getId()).isNotNull().isEqualTo(4);
		
		model.setTrades(null);
		assertThat(model.getTrades()).isNull();
	}

}
