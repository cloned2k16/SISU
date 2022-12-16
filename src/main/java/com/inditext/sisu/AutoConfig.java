package com.inditext.sisu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.inditext.sisu.domain.prices.hexagon.api.PriceTagFacade;
import com.inditext.sisu.domain.prices.hexagon.ddbb.PriceTagDbRepository;
import com.inditext.sisu.domain.prices.hexagon.ddbb.PriceTagDbRepositoryWithSP;
import com.inditext.sisu.domain.prices.model.PriceTag;
import com.inditext.sisu.domain.prices.port.PriceTagRepository;
import com.inditext.sisu.domain.prices.port.PriceTagService;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;

@Configuration
public class AutoConfig {
	
	private static ILogger log = Logger.getLogger("== AutoConfig -");
	
	private static boolean useStoredProcedure;
	 
	static { log.debug("static <init>"); }
	 

	@Bean
	@Scope("request")
	PriceTagFacade  getPriceTagFacade() {
		PriceTagService service = new PriceTagService();  
		log.debug("getPriceTagFacade using %s",service );
		PriceTagRepository 	ddbb ;
		if (useStoredProcedure) ddbb= new PriceTagDbRepositoryWithSP();
		else                    ddbb= new PriceTagDbRepository();
		
		PriceTagFacade 		bean = service.newFacade(ddbb);
		return bean;
	}
	 
	@Bean 
	PriceTag		getPriceTagBean() {

		PriceTag bean = new PriceTag();
		log.debug("getPriceTagBean .. %s",bean );
		return bean;
	}

	public static void configure(boolean useSP) {
		log.info("using stored procedure: %s", useSP);
		useStoredProcedure=useSP;
	}
    
}
