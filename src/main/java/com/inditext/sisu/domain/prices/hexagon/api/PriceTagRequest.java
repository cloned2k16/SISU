package com.inditext.sisu.domain.prices.hexagon.api;

//import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;

public class PriceTagRequest {
	private ILogger log = Logger.getLogger(this);

	 private final String  onDate;
	 private final Long    productId;
	 private final Long    brandId;

	 PriceTagRequest ( 		final String dateStr
			 		 ,  	final long   productId
			 		 , 		final long   brandId){
		log.info("constructor: "+productId+":"+brandId);
		this.onDate		= dateStr;
		this.productId 	= productId;
		this.brandId 	= brandId;
		 
	 }

	public String getOnDate() {
		return onDate;
	}

	public Long getProductId() {
		return productId;
	}

	public Long getBrandId() {
		return brandId;
	}
	
	@Override
	public String toString () {
		return String.format("%s, %d, %d, %s", getClass().getName(),brandId,productId,onDate);
	}
	
	
}
