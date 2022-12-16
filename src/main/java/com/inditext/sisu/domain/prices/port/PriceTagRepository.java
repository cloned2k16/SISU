package com.inditext.sisu.domain.prices.port;

import java.sql.Date;

import com.inditext.sisu.YAMLConfig;
import com.inditext.sisu.domain.prices.hexagon.ddbb.PriceTagDbModel;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;


public abstract class PriceTagRepository {

	public abstract PriceTagDbModel getPriceAppliedOnDate		(Date onDate, long productId , long brandId);

	private static ILogger log = Logger.getLogger(PriceTagRepository.class);

	protected static String JDBC_DRIVER 	;
	protected static String DB_URL 		;
	protected static String DB_USER 		;
	protected static String DB_PASS 		;
	
	public static void configure(YAMLConfig config){
		log.info("configure: %s", config); 
		JDBC_DRIVER = config.getDdbbDriver();
		DB_URL		= config.getDdbbUrl();
		DB_USER     = config.getDdbbUser();
		DB_PASS     = config.getDdbbPass();
		log.debug("driver: %s", JDBC_DRIVER);
		log.debug("url   : %s", DB_URL);
	}
	
}
