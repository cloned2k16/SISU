# SISU Prices Lookup App

### compilacion y uso   

-  la Applicacion principal es la clase  
   ***com.inditex.sisu.PricesLookupAppApplication***  
  ejecutable como spring boot app dentro del IDE eclipse  
  asi como 
	   ***com.inditex.sisu.PricesLookupAppApplicationTests***  
ejeciuta los tests con run as Junit ..
  
  
- mientras mas simplemente desde la carpeta principal  

  se puede ejecutar 
	***gradle build***  
seguido de ***java -jar build\libs\PricesLookupApp-0.0.1-SNAPSHOT.jar***  
que ejucatar el paquete desde la misma linea de comandos  
y el servicio estara disponible en (eg.)  
 ***http://localhost:8080/getPriceTagOn/1/35455?date=14%20jul%202020***  
donde  1 y 35455 en el url representan respecticamente el codigo producto y el codigo brand  
mientra la fecha se pasa como parametro adicional y puede ser rapresentada en unos de los siguiente formatos reconocido  

		  "yyyy-MM-dd-HH.mm.ss"
		 ,"yyyy-MM-dd"              
		 ,"dd MMM yyyy - HH:mm:ss"
		 ,"dd MMM yyyy"

 
- el servicio tambien esta disponible bajo peticion POST ..   
quedando igual por lo que se refiere a la ruta URL pero pasando la fecha en formato JSON 
por ejemplo ..

	{ "date" : "2020-06-16-21.00.00" }
	
- finalmente en el caso de uso con base de dato externa se podra configurar  
el fichero ***application.yml*** de forma tal que la consulta se haga en el designado  
en todo el uso de bases de datos distintas   
precisaria adaptacion de los adaptadores correpondientes de la clase abstracta ***PriceTagRepository***
  

### descripcion implementacion

se ha usado una base de datos H2 en memoria como de especificaciones que se crea semi automaticamente con JPA y un fichero sql  
dejando pero abierta la posibilidad de configurar el micro servicio para operar atacando por JDBC una base de dato externa y real  
especificando los parametros en el fichero ***application.yml***  

~~~~
 ## external DDBB config
ddbbDriver:             "org.h2.Driver"
ddbbUrl:                "jdbc:h2:mem:chupeta"
ddbbUser:               "sa"
ddbbPass:               ""
useStoredProcedure:     false
~~~~

se puede ademas configurar el tipo de aceso a la base de datos pudiendo elegir usar procedimientos almacenados que seria aconsejable
aunque H2 no soporte plenamente esto se ha simulado con el uso de alias en su lugar   

se podria haber tambien optimizado la tabla añadiendo una tabla de mapeo valuta en lugar de usar una stringa como especificado 
pero esto ya no era tan relevante ya que la columna no es praticamente en uso .. 

organizado en una architectura hexagonal aun si solo implementa solo una parte, con diseño guiado por principio DDD
intentando mantener una elevada abstracion del conjunto aun si mejorable en un escenario mas realistico

el servicio REST esta disponible tanto para los metodos GET y POST 
y se puede testear selectivamente en ambos especificando cual  
en la constante **TEST_SERVICE_USING_POST** presente la clase misma que ejecuta los tests 

##### reference environment

como entorno de desarollo he utilizado 

- JavaSE 17
- Eclipse 2022-12
- spring boot 3.0.0
- spring data JPA
- H2 database
- spring web


#### especificaciones y requerimientos

En la base de datos de comercio electrónico de la compañía disponemos de la tabla PRICES  
 que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas.  
 A continuación se muestra un ejemplo de la tabla con los campos relevantes:
 
~~~~  
  BRAND_ID  START_DATE              END_DATE                            PRICE_LIST            PRODUCT_ID           PRIORITY                 PRICE            CURR
  1         2020-06-14-00.00.00     2020-12-31-23.59.59                 1                     35455                0                         35.50            EUR  
  1         2020-06-14-15.00.00     2020-06-14-18.30.00                 2                     35455                1                         25.45            EUR  
  1         2020-06-15-00.00.00     2020-06-15-11.00.00                 3                     35455                1                         30.50            EUR  
  1         2020-06-15-16.00.00     2020-12-31-23.59.59                 4                     35455                1                         38.95            EUR 
~~~~
Campos:  
 
BRAND_ID: foreign key de la cadena del grupo (1 = ZARA).  
START_DATE , END_DATE: rango de fechas en el que aplica el precio tarifa indicado.  
PRICE_LIST: Identificador de la tarifa de precios aplicable.  
PRODUCT_ID: Identificador código de producto.  
PRIORITY: Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico).  
PRICE: precio final de venta.  
CURR: iso de la moneda.  
 
Se pide:  
 
Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:  
 
Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.  
Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.  
 
Se debe utilizar una base de datos en memoria (tipo h2) e inicializar con los datos del ejemplo,  
(se pueden cambiar el nombre de los campos y añadir otros nuevos si se quiere, elegir el tipo de dato que se considere adecuado para los mismos).  
              
Desarrollar unos test al endpoint rest que  validen las siguientes peticiones al servicio con los datos del ejemplo:  
                                                                                       
  -          Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
  -          Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
  -          Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
  -          Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
  -          Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
 
 
Se valorará:
 
Diseño y construcción del servicio.  
Calidad de Código.  
Resultados correctos en los test.  



##### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web)
  
