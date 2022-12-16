package com.inditext.sisu.domain.prices.hexagon.ddbb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.inditext.sisu.domain.prices.port.PriceTagRepository;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;
import com.inditext.sisu.util.TimestampConverter;

public class PriceTagDbRepositoryWithSP 
extends PriceTagRepository 
{
	private  ILogger log = Logger.getLogger(this);
	public PriceTagDbRepositoryWithSP (){
	 log.info("<init>");
	}
	@Override
	public PriceTagDbModel getPriceAppliedOnDate(Date onDate, long productId, long brandId) {
		log.info("query DDBB %s prod:%d brand %d",onDate,productId,brandId);
		try {
			Class<?> dbDriver = Class.forName (JDBC_DRIVER );
 			log.debug("using driver %s",dbDriver);
 			  
 			Connection conn = DriverManager.getConnection (DB_URL , DB_USER, DB_PASS);
 			log.debug("have connection %s",conn);
 			Statement st = conn.createStatement();
			 
 			String when = TimestampConverter.format(onDate);
 			log.debug("when: %s", when);
 			 
 			ResultSet res = st.executeQuery("select * from getPriceOnDate('"+when+"',"+productId+","+brandId+");"); 

 			if (res.next())  return new PriceTagDbModel(res); 	// only first, which should be also limited to 1 by the query itself
 			  
		} 
		catch (ClassNotFoundException e) 	{ e.printStackTrace();	} 
		catch (SQLException e) 				{ e.printStackTrace(); 	}
		
		return null; // fall back result
	}

}
