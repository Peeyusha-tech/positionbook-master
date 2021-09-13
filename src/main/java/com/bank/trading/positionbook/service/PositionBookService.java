package com.bank.trading.positionbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.trading.positionbook.model.PositionBook;

@Service
public class PositionBookService {

	@Autowired
	private PositionBook positionBook = new PositionBook();

	public PositionBook getPositionBook() {
		return positionBook;
	}

	public void setPositionBook(PositionBook positionBook) {
		this.positionBook = positionBook;
	}
	
}
