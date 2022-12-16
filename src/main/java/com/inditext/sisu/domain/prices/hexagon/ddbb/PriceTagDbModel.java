package com.inditext.sisu.domain.prices.hexagon.ddbb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.inditext.sisu.domain.prices.model.PriceTag;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;

public class PriceTagDbModel extends PriceTag {
	
	private ILogger log = Logger.getLogger(this);
	
	
	public PriceTagDbModel (ResultSet rs) {
		try {
			price_list	= rs.getLong		("PRICE_LIST");
            start_date	= rs.getTimestamp	("START_DATE").toLocalDateTime();
            end_date	= rs.getTimestamp	("END_DATE").toLocalDateTime();
            priority    = rs.getLong		("PRIORITY");
            price 		= rs.getFloat		("PRICE");
            brand_id	= rs.getLong		("BRAND_ID");
            prod_id		= rs.getLong		("PROD_ID");
            currency	= rs.getString		("CURRENCY");
            
			log.debug("row: %s, %s, %s, %s", price_list,start_date,end_date,priority);
		} 
		catch (SQLException e) {
			start_date 	=
			end_date 	= null;
			brand_id	=
			prod_id     =
			price_list	=
			priority	=-1;
			price		=0f;
			currency    ="";
			
			e.printStackTrace();
		}
		
	}
	
	public long getPrice_list() {
		return price_list;
	}

	public LocalDateTime getStartDate() {
		return start_date;
	}
	public LocalDateTime getEndDate() {
		return end_date;
	}

	@Override
	public String toString () {
		return String.format("%s, %d, %d, %s, %s : %s", "PriceTadDbModel",brand_id,prod_id,price,start_date,end_date);
	}
}
