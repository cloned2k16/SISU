package com.inditext.sisu.domain.prices.port;


import org.springframework.stereotype.Service;

import com.inditext.sisu.domain.prices.hexagon.api.PriceTagFacade;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;


@Service
public class PriceTagService {
	
	private ILogger log = Logger.getLogger(this);

	public PriceTagFacade newFacade(PriceTagRepository ddbb) {
		log.debug("newFacade --->> %s",ddbb);
		PriceTagFacade facade= new PriceTagFacade(this,ddbb);
		log.debug("newFacade: %s",facade);
		return facade;
	}
		
}
