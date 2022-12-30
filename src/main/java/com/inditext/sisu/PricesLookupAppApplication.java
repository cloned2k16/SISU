package com.inditext.sisu;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.inditext.sisu.domain.prices.port.PriceTagRepository;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;

@SpringBootApplication
public class PricesLookupAppApplication implements CommandLineRunner {

	private  ILogger log = Logger.getLogger("== The App ==");
	
	@Autowired
    private YAMLConfig myConfig;
	
	public static void main(String[] args) {
		var app = new SpringApplication(PricesLookupAppApplication.class);   			// JDK 11
        app.run();
	}
	
	@Override
	public void run(String... args) throws Exception {
		var formatted = Stream.of(args).collect(Collectors.joining(",","[","]")); 		// JDK 11
		log.debug("run .. %s",formatted);
		log.info("name: " + myConfig.getName());
		
		PriceTagRepository.configure(myConfig); // not necessarily we have to use same in memory DB 
		AutoConfig.configure(myConfig.isUseStoredProcedure());
	}
	

}
