package com.inditext.sisu;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class PricesLookupAppApplicationTests {

	private static final boolean 	TEST_SERVICE_USING_POST = true;
	
	private static final long 		CORRECT_BRAND_ID 		= 1;
	private static final long 		CORRECT_PROD_ID 		= 35455;

	private static final long 		WRONG_BRAND_ID 			= -1;
	private static final long 		WRONG_PROD_ID 			= -1;

	private static final String 	CURRENCY_STR 			= "EUR";

	private static final String 	CORRECT_DATE_TIME 		= "2020-06-14-00.00.00";


	@Autowired
	private MockMvc mockMvc;

	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	private void println(String output) {
	    System.out.println(output);
	}
	
	@BeforeEach
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@AfterEach
	public void tearDown() {
	    System.setOut(standardOut);
	}	
	

	
	@Test
	void contextLoads() throws Exception {
		assertNotNull(mockMvc);
		print();
		
        println("print out string");
		assertEquals("print out string", outputStreamCaptor.toString() 	.trim());
		
		String text = tapSystemOut(() -> {
	        println("!! Hello World !!");
	    });
		
		assertEquals("!! Hello World !!", text.trim());
	}

	@Test
	public void testEndpoint() throws Exception {

		this.mockMvc.perform(get("/testPriceTag").param("name", "spring"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response" ,is(nullValue())))
				.andExpect(jsonPath("$.message").value("Hello, spring!"));
	}

	private RequestBuilder restCallPriceTagOn(long brandId, long prodId, String dateStr) {
		String     restUrl  = String.format("/getPriceTagOn/%d/%d",brandId,prodId );
		if (!TEST_SERVICE_USING_POST) return get(restUrl).param("date", dateStr); 
		else return post(restUrl).contentType(MediaType.APPLICATION_JSON).content(" { \"date\" : \""+dateStr+"\" }");
			
		
	}
	
	@Test
	public void testWrongDate() throws Exception { 
		long       brandId	= CORRECT_BRAND_ID;
		long       prodId 	= CORRECT_PROD_ID;

		DateFormat format 	= DateFormat.getDateInstance();
		
		this.mockMvc.perform(restCallPriceTagOn(brandId,prodId,format.format(new Date())))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response" ,is(nullValue())))
				.andExpect(jsonPath("$.message"  ).value("null"))
				;
	}
	@Test
	public void testWrongBrand() throws Exception {
		long       brandId	= WRONG_BRAND_ID;
		long       prodId 	= CORRECT_PROD_ID;
		
		this.mockMvc.perform(restCallPriceTagOn(brandId,prodId,CORRECT_DATE_TIME))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response" ,is(nullValue())))
				.andExpect(jsonPath("$.message"  ).value("null"))
				;
	}
	@Test
	public void testWrongProduct() throws Exception {
		long       brandId	= CORRECT_BRAND_ID;
		long       prodId 	= WRONG_PROD_ID;
		
		this.mockMvc.perform(restCallPriceTagOn(brandId,prodId,CORRECT_DATE_TIME))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response" ,is(nullValue())))
				.andExpect(jsonPath("$.message"  ).value("null"))
				;
	}
	
	/// == common call ==================================================================
	private void testThis(long brandId, long prodId, String date_time, int listing, int priority, String currency, float price) throws Exception {

		this.mockMvc.perform(restCallPriceTagOn(brandId,prodId,date_time))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.response" ,is(notNullValue())))
		.andExpect(jsonPath("$.response.brand_id").exists())
		.andExpect(jsonPath("$.response.brand_id").value(brandId))
		.andExpect(jsonPath("$.response.prod_id").exists())
		.andExpect(jsonPath("$.response.prod_id").value	(prodId))
		.andExpect(jsonPath("$.response.price_list").exists())
		.andExpect(jsonPath("$.response.price_list").value	(listing))
		.andExpect(jsonPath("$.response.priority").exists())
		.andExpect(jsonPath("$.response.priority").value(priority))
		.andExpect(jsonPath("$.response.currency").exists())
		.andExpect(jsonPath("$.response.currency").value(currency))
		.andExpect(jsonPath("$.response.price").exists())
		.andExpect(jsonPath("$.response.price").value(price))
		;
	}
	/// == REQUIRED ======================================================================
	@Test
	public void _Test1() throws Exception {
		testThis(CORRECT_BRAND_ID,CORRECT_PROD_ID,"2020-06-14-10.00.00",1,0,CURRENCY_STR,35.5f);
		
	}
	@Test
	public void _Test2() throws Exception {
		testThis(CORRECT_BRAND_ID,CORRECT_PROD_ID,"2020-06-14-16.00.00",2,1,CURRENCY_STR,25.45f);
	}
	@Test
	public void _Test3() throws Exception {
		testThis(CORRECT_BRAND_ID,CORRECT_PROD_ID,"2020-06-14-21.00.00",1,0,CURRENCY_STR,35.5f);
	}
	@Test
	public void _Test4() throws Exception {
		testThis(CORRECT_BRAND_ID,CORRECT_PROD_ID,"2020-06-15-10.00.00",3,1,CURRENCY_STR,30.5f);
	}
	@Test
	public void _Test5() throws Exception {
		testThis(CORRECT_BRAND_ID,CORRECT_PROD_ID,"2020-06-16-21.00.00",4,1,CURRENCY_STR,38.95f);
	}
	

	
}
