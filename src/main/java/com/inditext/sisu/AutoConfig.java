package com.inditext.sisu;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.inditext.sisu.domain.prices.hexagon.api.PriceTagFacade;
import com.inditext.sisu.domain.prices.hexagon.ddbb.PriceTagDbRepository;
import com.inditext.sisu.domain.prices.hexagon.ddbb.PriceTagDbRepositoryWithSP;
import com.inditext.sisu.domain.prices.model.PriceTag;
import com.inditext.sisu.domain.prices.port.PriceTagRepository;
import com.inditext.sisu.domain.prices.port.PriceTagService;
import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
@EnableWebMvc
public class AutoConfig{// extends WebMvcConfigurationSupport {

	
	@SuppressWarnings("unused")
	private static final String BASE_PACKAGE = "com.inditext.sisu.domain.prices.hexagon.api";

	private static ILogger log = Logger.getLogger("== AutoConfig -");
	
	private static boolean useStoredProcedure;
	 
	static { log.debug("static <init>"); }

	
    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("SISU-public")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SISU PriceTAGs API")
                .description("A simple service ..")
                .version("v1.2.3")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("SpringShop Wiki Documentation")
                .url("https://springshop.wiki.github.org/docs"));
    }
	
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
