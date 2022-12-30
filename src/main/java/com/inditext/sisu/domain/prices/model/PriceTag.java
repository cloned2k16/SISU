package com.inditext.sisu.domain.prices.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="price_tags") 
public class PriceTag {
	
	@Id
	@Column(name="price_list")
	protected  long				price_list;

	@Column(name = "start_date", 	columnDefinition = "TIMESTAMP")
	protected LocalDateTime 	start_date;
	@Column(name = "end_date",   	columnDefinition = "TIMESTAMP")
	protected LocalDateTime 	end_date;
	
	protected  long   			brand_id;
	protected  long 			prod_id;
	protected  long 			priority;
	protected  float  			price;
	protected  String      		currency;
	

		public PriceTag			() {
			this.price_list 	= -1;
			this.brand_id 		=  0;
			this.prod_id 		=  0;
			this.priority 		= -1;
			this.price 			=  0;
			this.currency 		= "";
		}

		public long getProd_id() {
			return prod_id;
		}

		public long getPriority() {
			return priority;
		}

		public float getPrice() {
			return price;
		}

		public String getCurrency() {
			return currency;
		}

		public Long getBrand_id() {
			return brand_id;
		}

}
