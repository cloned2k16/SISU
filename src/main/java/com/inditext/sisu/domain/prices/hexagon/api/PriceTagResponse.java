package com.inditext.sisu.domain.prices.hexagon.api;

import com.inditext.sisu.domain.prices.hexagon.ddbb.PriceTagDbModel;

public class PriceTagResponse {
	private PriceTagDbModel 	found;
	private String 				message;  

	public PriceTagResponse(String msg) {
		message = msg;
		found= null;
	}
	
	public PriceTagResponse(PriceTagDbModel res) {
		found = res;
		message = ""+res;
	}

	public PriceTagDbModel getResponse() {
		return found;
	}
 
	public String getMessage() {
		return message;
	}

}
