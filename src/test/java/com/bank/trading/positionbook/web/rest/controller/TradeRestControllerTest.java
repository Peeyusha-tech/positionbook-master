package com.bank.trading.positionbook.web.rest.controller;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bank.trading.positionbook.TradeTypes;
import com.bank.trading.positionbook.model.Account;
import com.bank.trading.positionbook.model.AccountSummary;
import com.bank.trading.positionbook.model.PositionBook;
import com.bank.trading.positionbook.model.RequestModel;
import com.bank.trading.positionbook.model.Trade;
import com.bank.trading.positionbook.service.PositionBookService;

class TradeRestControllerTest {

	private static final String SEC1 = "SEC1";
	private static final String ACC1 = "ACC1";
	private PositionBook book = new PositionBook();
	private PositionBookService service = new PositionBookService();
	private TradeRestController controller = new TradeRestController();
	
	@Test
	void testGetAccountDetails() {
		service.setPositionBook(book);
		controller.setService(service);
		
		Trade buyTrade = new Trade(1, TradeTypes.BUY, ACC1, SEC1, 100);
		RequestModel requestModel = new RequestModel(singletonList(buyTrade));
		this.book.processTradeRequest(requestModel);
		assertThat(this.book.getSummary(ACC1).getTotalValueBySec(SEC1)).isNotNull().isNotZero().isEqualTo(100);
		
		ResponseEntity<Account> account = controller.getAccount(ACC1);
		assertThat(account.getStatusCode()).isEqualTo(HttpStatus.OK);
		Account body = account.getBody();
		assertThat(body.getAccountId()).isEqualTo(ACC1);
		assertThat(body.getSummary().getTotalValueBySec(SEC1)).isEqualTo(100);
		
	}
	
	
	@Test
	void testPositionBookWithDuplicateTrades() {

		controller.setService(service);
		
		// Setup the Trades
		Trade buyTrade1 = new Trade(21, TradeTypes.BUY, ACC1, SEC1, 100);
		Trade buyTrade2 = new Trade(22, TradeTypes.BUY, ACC1, SEC1, 5);
		// Setup the Trades
		Trade sellTrade1 = new Trade(23, TradeTypes.SELL, ACC1, SEC1, 50);
		Trade sellTrade2 = new Trade(23, TradeTypes.SELL, ACC1, SEC1, 5);
		
		// Setup the Trades
		Trade cancelTrade = new Trade(22, TradeTypes.CANCEL, ACC1, SEC1, 0);
		
		List<Trade> list = new ArrayList<>();
		list.add(buyTrade1);
		list.add(buyTrade2);
		list.add(sellTrade1);
		list.add(sellTrade2);
		list.add(cancelTrade);

		RequestModel requestModel = new RequestModel(list);
		this.controller.postTrade(requestModel);
		
		AccountSummary summary = this.service.getPositionBook().getSummary(ACC1);
		assertThat(summary.getAccount().getTradedSecurities()).isNotNull().isNotEmpty().hasSize(4);// Duplicate Trades not allowed
		assertThat(summary.getTotalValueBySec(SEC1)).isNotNull().isEqualTo(50);
	}

}
