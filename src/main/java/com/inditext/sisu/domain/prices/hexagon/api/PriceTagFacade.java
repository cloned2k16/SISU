package com.inditext.sisu.domain.prices.hexagon.api;

import java.sql.Date;

import com.inditext.sisu.domain.prices.hexagon.ddbb.PriceTagDbModel;

import com.inditext.sisu.domain.prices.port.PriceTagRepository;
import com.inditext.sisu.domain.prices.port.PriceTagService;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;
import com.inditext.sisu.util.TimestampConverter;

public class PriceTagFacade {
	
	private ILogger log = Logger.getLogger(this);
	
	private final PriceTagService service;
	private final PriceTagRepository ddbb;
	

	public PriceTagFacade(PriceTagService priceTagService, PriceTagRepository ddbb) {
		log.debug("<iniT> %s : %s",priceTagService,ddbb);
        this.service = priceTagService;
        this.ddbb    = ddbb;
	}


	

	public PriceTagResponse get(PriceTagRequest request)  {
		log.info("get "+service+" :: "+request);
		
		String dateStr= request.getOnDate();
		long time = TimestampConverter.parseQueryDate(dateStr);
		Date onDate = new Date(time );
		
		PriceTagDbModel res = ddbb.getPriceAppliedOnDate(onDate, request.getBrandId(), request.getProductId());
		
		log.info("result: %s", res);
		return new PriceTagResponse(res);
	}


}
