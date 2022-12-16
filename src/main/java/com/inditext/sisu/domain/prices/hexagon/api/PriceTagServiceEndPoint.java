package com.inditext.sisu.domain.prices.hexagon.api;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;


@RestController
@Scope("request")
public class PriceTagServiceEndPoint {

	private ILogger log = Logger.getLogger(this);
	
	private final PriceTagFacade facade;
	
	private PriceTagServiceEndPoint(PriceTagFacade facade) {
    	log.info("new Endpoint: "+facade);
        this.facade = facade;
    }
	
	@GetMapping("/testPriceTag")
	PriceTagResponse test		(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new PriceTagResponse(String.format("Hello, %s!", name));
	}
	
	@GetMapping("/getPriceTagOn/{brand}/{product}")
	PriceTagResponse get( 
			@PathVariable("brand")   final Long   brandId
   		   ,@PathVariable("product") final Long   productId 
   		   ,@RequestParam("date")    final String dateStr ) {
		
		log.info("GET request: %d : %d : %s",brandId,productId,dateStr);
		
		PriceTagResponse res = null;
		try {
			res= facade.get(new PriceTagRequest(dateStr,productId,brandId));
		}
		catch ( Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
    
	private static class PostRequestWrapper {
		public String date;
	}
	
	@PostMapping("/getPriceTagOn/{brand}/{product}")
	PriceTagResponse post( 
			@PathVariable("brand")   final Long   brandId
   		   ,@PathVariable("product") final Long   productId
   		   ,@RequestBody PostRequestWrapper postArgs) { 
   		   
		log.info("POST request: %d : %d : %s",brandId,productId,postArgs.date);
		
		PriceTagResponse res = null;
		try {
			res= facade.get(new PriceTagRequest(postArgs.date,productId,brandId));
		}
		catch ( Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
}
