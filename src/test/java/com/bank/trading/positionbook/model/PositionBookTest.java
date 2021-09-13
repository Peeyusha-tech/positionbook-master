package com.bank.trading.positionbook.model;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bank.trading.positionbook.TradeTypes;

class PositionBookTest {

	private PositionBook book = new PositionBook();

	@Test
	void testCanCreatePositionBook() {
		assertThat(this.book).isNotNull();
	}

	@Test
	void testRecordZeroTradeInPositionBook() {
		assertThat(this.book).isNotNull();
		this.book.processTradeRequest(new RequestModel());
	}

	@Test
	void testRecordTradeInPositionBookWithBuy() {
		assertThat(this.book).isNotNull();

		Trade buyTrade = new Trade(1, TradeTypes.BUY, "ACC1", "SEC1", 100);
		RequestModel requestModel = new RequestModel(singletonList(buyTrade));
		this.book.processTradeRequest(requestModel);
		assertThat(this.book.getSummary("ACC1").getTotalValueBySec("SEC1")).isNotNull().isNotZero().isEqualTo(100);
		assertThat(this.book.getSummary("ACC1").getTotalValueBySec("SEC100")).isNotNull().isZero();
		this.book.printSummary("ACC1");
	}

	@Test
	void testRecordTradeInPositionBookWithMultiBuyAndOneSell() {
		assertThat(this.book).isNotNull();

		// Setup the Trades
		Trade buyTrade1 = new Trade(1, TradeTypes.BUY, "ACC2", "SEC1", 100);
		Trade buyTrade2 = new Trade(2, TradeTypes.BUY, "ACC2", "SEC1", 100);
		Trade sellTrade = new Trade(3, TradeTypes.SELL, "ACC2", "SEC1", 50);
		List<Trade> list = new ArrayList<>();
		list.add(buyTrade1);
		list.add(buyTrade2);
		list.add(sellTrade);

		RequestModel requestModel = new RequestModel(list);

		this.book.processTradeRequest(requestModel);
		assertThat(this.book.getSummary("ACC2").getTotalValueBySec("SEC1")).isNotNull().isNotZero().isEqualTo(150);
		this.book.printSummary("ACC2");
	}

	@Test
	void testRecordTradeInPositionBookWithMultiSecuties() {
		assertThat(this.book).isNotNull();

		// Setup the Trades
		Trade buyTrade1 = new Trade(1, TradeTypes.BUY, "ACC1", "SEC1", 12);
		Trade buyTrade2 = new Trade(2, TradeTypes.BUY, "ACC1", "SECXYZ", 50);
		Trade buyTrade3 = new Trade(3, TradeTypes.BUY, "ACC2", "SECXYZ", 33);
		Trade buyTrade4 = new Trade(4, TradeTypes.BUY, "ACC1", "SEC1", 20);
		List<Trade> list = new ArrayList<>();
		list.add(buyTrade1);
		list.add(buyTrade2);
		list.add(buyTrade3);
		list.add(buyTrade4);

		RequestModel requestModel = new RequestModel(list);

		this.book.processTradeRequest(requestModel);
		this.book.printSummary();
		assertThat(this.book.getSummary("ACC1").getTotalValueBySec("SEC1")).isNotNull().isNotZero().isEqualTo(32);
		assertThat(this.book.getSummary("ACC1").getTotalValueBySec("SECXYZ")).isNotNull().isNotZero().isEqualTo(50);
		assertThat(this.book.getSummary("ACC2").getTotalValueBySec("SECXYZ")).isNotNull().isNotZero().isEqualTo(33);
	}

	@Test
	void testPrintSummaryAllAccouts() {
		assertThat(this.book).isNotNull();
		this.book.printSummary();
	}

	@Test
	void testPrintSummaryInvalidAccout() {
		assertThat(this.book).isNotNull();
		assertThatThrownBy(() -> {
			this.book.getSummary("INVALID");
		}).isInstanceOf(RuntimeException.class);
	}

	@Test
	void testPositionBookWithBuySellAndCancel() {
		assertThat(this.book).isNotNull();

		// Setup the Trades
		Trade buyTrade1 = new Trade(21, TradeTypes.BUY, "ACC1", "SEC1", 100);
		Trade buyTrade2 = new Trade(21, TradeTypes.CANCEL, "ACC1", "SEC1", 0);
		Trade buyTrade3 = new Trade(22, TradeTypes.BUY, "ACC1", "SEC1", 5);
		
		Trade buyTrade4 = new Trade(31, TradeTypes.BUY, "ACC2", "SECXYZ", 100);
		Trade buyTrade5 = new Trade(31, TradeTypes.CANCEL, "ACC2", "SECXYZ", 0);
		Trade buyTrade6 = new Trade(32, TradeTypes.BUY, "ACC2", "SECXYZ", 5);
		List<Trade> list = new ArrayList<>();
		list.add(buyTrade1);
		list.add(buyTrade2);
		list.add(buyTrade3);
		list.add(buyTrade4);
		list.add(buyTrade5);
		list.add(buyTrade6);

		RequestModel requestModel = new RequestModel(list);

		this.book.processTradeRequest(requestModel);
		this.book.printSummary();
		assertThat(this.book.getSummary("ACC1").getTotalValueBySec("SEC1")).isNotNull().isNotZero().isEqualTo(5);
		assertThat(this.book.getSummary("ACC2").getTotalValueBySec("SECXYZ")).isNotNull().isNotZero().isEqualTo(5);
	}
	
	
	@Test
	void testPositionBookWithDuplicateTrades() {
		assertThat(this.book).isNotNull();

		// Setup the Trades
		Trade buyTrade1 = new Trade(21, TradeTypes.BUY, "ACC1", "SEC1", 100);
		Trade buyTrade2 = new Trade(22, TradeTypes.BUY, "ACC1", "SEC1", 5);
		// Setup the Trades
		Trade sellTrade1 = new Trade(23, TradeTypes.SELL, "ACC1", "SEC1", 50);
		Trade sellTrade2 = new Trade(23, TradeTypes.SELL, "ACC1", "SEC1", 5);
		
		// Setup the Trades
		Trade cancelTrade = new Trade(22, TradeTypes.CANCEL, "ACC1", "SEC1", 0);
		
		List<Trade> list = new ArrayList<>();
		list.add(buyTrade1);
		list.add(buyTrade2);
		list.add(sellTrade1);
		list.add(sellTrade2);
		list.add(cancelTrade);

		RequestModel requestModel = new RequestModel(list);

		this.book.processTradeRequest(requestModel);
		this.book.printSummary();
		AccountSummary summary = this.book.getSummary("ACC1");
		assertThat(summary.getAccount().getTradedSecurities()).isNotNull().isNotEmpty().hasSize(4);// Duplicate Trades not allowed
		assertThat(summary.getTotalValueBySec("SEC1")).isNotNull().isEqualTo(50);
	}
}
