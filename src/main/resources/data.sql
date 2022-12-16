
/* reference testing rows */
insert into price_tags
					( 	BRAND_ID
					,	START_DATE
					,	END_DATE
					, 	PRICE_LIST
					,	PROD_ID
					,	PRIORITY
					,	PRICE
					,	CURRENCY
					)
					VALUES  (  1
					,       PARSEDATETIME('2020-06-14-00.00.00', 'yyyy-MM-dd-HH.mm.ss')
					,   	PARSEDATETIME('2020-12-31-23.59.59','yyyy-MM-dd-HH.mm.ss') 
					,       1 
					,       35455   
					,       0 
					,       35.50  
					,       'EUR');

insert into price_tags
					( 	BRAND_ID
					,	START_DATE
					,	END_DATE
					, 	PRICE_LIST
					,	PROD_ID
					,	PRIORITY
					,	PRICE
					,	CURRENCY
					)
					VALUES  (  1
					,       PARSEDATETIME('2020-06-14-15.00.00', 'yyyy-MM-dd-HH.mm.ss')
					,   	PARSEDATETIME('2020-06-14-18.30.00','yyyy-MM-dd-HH.mm.ss') 
					,       2 
					,       35455   
					,       1 
					,       25.45 
					,       'EUR');
					
insert into price_tags
					( 	BRAND_ID
					,	START_DATE
					,	END_DATE
					, 	PRICE_LIST
					,	PROD_ID
					,	PRIORITY
					,	PRICE
					,	CURRENCY
					)
					VALUES  (  1
					,       PARSEDATETIME('2020-06-15-00.00.00', 'yyyy-MM-dd-HH.mm.ss')
					,   	PARSEDATETIME('2020-06-15-11.00.00','yyyy-MM-dd-HH.mm.ss') 
					,       3 
					,       35455   
					,       1 
					,       30.50 
					,       'EUR');
					
insert into price_tags
					( 	BRAND_ID
					,	START_DATE
					,	END_DATE
					, 	PRICE_LIST
					,	PROD_ID
					,	PRIORITY
					,	PRICE
					,	CURRENCY
					)
					VALUES  (  1
					,       PARSEDATETIME('2020-06-15-16.00.00', 'yyyy-MM-dd-HH.mm.ss')
					,   	PARSEDATETIME('2020-12-31-23.59.59','yyyy-MM-dd-HH.mm.ss') 
					,       4 
					,       35455   
					,       1 
					,       38.95 
					,       'EUR');
									

/* testing stuff */					
CREATE VIEW TEST_VIEW0 AS SELECT * FROM price_tags WHERE PRIORITY = 0;
CREATE VIEW TEST_VIEW1 AS SELECT * FROM price_tags WHERE PRIORITY = 1;


/* stored procedure (H2 way) */
CREATE ALIAS getPriceOnDate AS '@CODE
java.sql.ResultSet getPriceOnDate(java.sql.Connection con,String onDate,long prodId, long brandId) throws Exception {
 java.sql.ResultSet rs = con.createStatement().executeQuery(" SELECT * FROM price_tags where brand_id = "+brandId
 					 										+" and   prod_id  = "+prodId
								 					 		+" and   START_DATE <= PARSEDATETIME( ''"+onDate+"'' , ''yyyy-MM-dd-HH.mm.ss'')"
 					 										+" and   END_DATE   >= PARSEDATETIME( ''"+onDate+"'' , ''yyyy-MM-dd-HH.mm.ss'')"
 					 										+" order by PRIORITY desc"
							  								+" limit 1");
 return rs;
}
';



