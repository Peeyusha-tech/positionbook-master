package com.bank.trading.positionbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestModel implements Serializable{
	
	private static final long serialVersionUID = 3338391832856261927L;

	/*
	 * List of Trades in the request
	 */
	private List<Trade> trades = new ArrayList<>();
}
