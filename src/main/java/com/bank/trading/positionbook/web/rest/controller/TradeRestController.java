package com.bank.trading.positionbook.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.trading.positionbook.model.Account;
import com.bank.trading.positionbook.model.RequestModel;
import com.bank.trading.positionbook.service.PositionBookService;

@RestController
public class TradeRestController {

	@Autowired
	private PositionBookService service = new PositionBookService();

	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable String id) {
			return new ResponseEntity<>(service.getPositionBook().getSummary(id).getAccount(), HttpStatus.OK);
	}
	
	
	@PostMapping("/trade")
	public ResponseEntity<Void> postTrade( @RequestBody RequestModel requestModel) {
			service.getPositionBook().processTradeRequest(requestModel);
			return ResponseEntity.ok().build();
	}

	@RequestMapping("/hello")
	public String index() {
		return "Spring Boot Trade Position Book!!";
	}

	public void setService(PositionBookService service) {
		this.service = service;
	}
}
