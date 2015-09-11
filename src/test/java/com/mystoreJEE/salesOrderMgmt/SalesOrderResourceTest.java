package com.mystoreJEE.salesOrderMgmt;

import static org.testng.AssertJUnit.assertEquals;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import business.salesOrderMgmt.entity.SalesOrder;

public class SalesOrderResourceTest {
	
	public SalesOrder salesOrder;
	
	@BeforeSuite(timeOut=10000)
	public void setUp() {
	}

	@BeforeClass
	public static void initSalesOrderService() {
	}


	@Test
	public void testSubmitSalesOrder() {
		/* Jersey client to send a POST request with JSON data */

		// Create the SalesOrder entity
		this.salesOrder = new SalesOrder();
		salesOrder.setPromoCode("Test");
		// Set non-relational properties
		salesOrder.setSalesOrderStatus("New");
		salesOrder.setBillToFirstName("Duke");
		salesOrder.setBillToLastName("Developer");
		salesOrder.setBillToStreetNumber("12345");
		salesOrder.setBillToStreetName("Technology Row");
		salesOrder.setBillToCity("Golden");
		salesOrder.setBillToState("CO");
		salesOrder.setBillToZipCode(80401);
		// Set Ship To properties
		salesOrder.setShipToStreetNumber("12345");
		salesOrder.setShipToStreetName("Technology Row");
		salesOrder.setShipToCity("Golden");
		salesOrder.setShipToState("CO");
		salesOrder.setShipToZipCode(80401);		
		// Billing properties
		salesOrder.setNameOnCreditCard("Duke Developer");
		salesOrder.setCreditCardType("Visa");
		salesOrder.setCreditCardNumber("123456789012");
		salesOrder.setCreditCardCsv(123);
		salesOrder.setCreditCardExpiryMonth("01");
		salesOrder.setCreditCardExpiryYear("2017");
		salesOrder.setEmailAddress("ddeveloper@techexpertconsulting.com");		
		salesOrder.setSalesOrderDate(new Timestamp(new Date().getTime()));
		
		// Build Jersey client calls
		Response response = null;
		try {
			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget resourceTarget = client.target(getBaseURI()).path("new");
			System.out.println("WebTarget " + resourceTarget.toString());	
			response = resourceTarget.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(salesOrder, MediaType.APPLICATION_JSON), Response.class);			
			response.bufferEntity();
			this.salesOrder = response.readEntity(SalesOrder.class);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

	}

	@Test(dependsOnMethods={"testSubmitSalesOrder"})
	public void testReviewSalesOrder() {
		/* Test using the Jersey client */
		Response response = null;
		SalesOrder fetched = null;
		
		try {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		System.out.println("Unit test salesOrder Uid: " + salesOrder.getSalesOrderUid());
		WebTarget webTarget = client.target(getBaseURI())
					.path(Integer.toString(salesOrder.getSalesOrderUid()));					
		System.out.println("WebTarget " + webTarget.toString());	
		Invocation getSalesOrder = webTarget.request().buildGet();
		response = getSalesOrder.invoke();
		response.bufferEntity();
		fetched = response.readEntity(SalesOrder.class);
		System.out.println("JerseyClient response SalesOrder has " + fetched.getLineItems().size() + " line items" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(Status.FOUND.getStatusCode(), response.getStatus());

	}	

	public static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/mystoreJEE/resources/salesOrders").build();
	}
}