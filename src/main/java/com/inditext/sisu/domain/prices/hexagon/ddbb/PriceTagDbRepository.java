package com.inditext.sisu.domain.prices.hexagon.ddbb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.inditext.sisu.domain.prices.port.PriceTagRepository;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;
import com.inditext.sisu.util.TimestampConverter;



public class PriceTagDbRepository extends PriceTagRepository {

	private ILogger log = Logger.getLogger(this);
	
	/**
	 * return
	 *  either a PriceTagDbModel object as result or null 
	 */
	@Override
	public PriceTagDbModel getPriceAppliedOnDate(Date onDate, long productId, long brandId)  {
		log.info("query DDBB %s prod:%d brand %d",onDate,productId,brandId);
		Connection conn = null;
		try {
			Class<?> dbDriver = Class.forName (JDBC_DRIVER );
 			log.debug("using driver %s",dbDriver);
 			  
 			conn = DriverManager.getConnection (DB_URL , DB_USER, DB_PASS);
 			log.debug("have connection %s",conn);
 			 
 			String query = "select * from price_tags "
 					 		+" where brand_id = ?"
 					 		+" and   prod_id  = ?"
 					 		+" and   START_DATE <= PARSEDATETIME( ? , 'yyyy-MM-dd-HH.mm.ss')"
 					 		+" and   END_DATE   >= PARSEDATETIME( ? , 'yyyy-MM-dd-HH.mm.ss')"
 					 		+" order by PRIORITY desc"
 					 		+" limit 1";
 			 
 			PreparedStatement  st = conn.prepareStatement(query);
 			 
 			String when = TimestampConverter.format(onDate);
 			st.setLong(1, brandId );
 			st.setLong(2, productId );
 			log.info("when: %s", when);
 			st.setString(3, when );
 			st.setString(4, when );
 			 
 			ResultSet res = st.executeQuery(); 

 			PriceTagDbModel result = null;
 			
			if (res.next()) result = new PriceTagDbModel(res); 	// only first, which should be also limited to 1 by the query itself
 			//conn.close();
 			
			return result;
 			  
		} 
		catch (ClassNotFoundException e) 	{ e.printStackTrace();	} 
		catch (SQLException e) 				{ e.printStackTrace(); 	}
		finally {
			if (conn!=null)
				try {
					log.info("closing connection %s",conn);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
 
		
		return null; // fall back result
	}



}
