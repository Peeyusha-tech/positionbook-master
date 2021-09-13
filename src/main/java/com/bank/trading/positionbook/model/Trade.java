package com.bank.trading.positionbook.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trade implements Serializable {

	private static final long serialVersionUID = -6089070337204176999L;
	
	private Integer id;
	private String tradeType; // Not validating as it may be something else like recon
	private String account;
	private String securityName;
	private Integer tradedAmount;

}
