package com.mystoreJEE.salesOrderMgmt;

/* Author: PEilers 
 * Note: Before executing this update the persistence.xml file such that 
 * the transaction type is changed from "JTA" to "LOCAL_RESOURCE" */

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertNotNull;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import business.salesOrderMgmt.controls.SalesOrderService;
import business.salesOrderMgmt.controls.ShippingCalculator;
import business.salesOrderMgmt.controls.TaxCalculator;
import business.salesOrderMgmt.entity.CatalogItem;
import business.salesOrderMgmt.entity.SalesOrder;
import business.salesOrderMgmt.entity.SalesOrderLine;

public class SalesOrderServiceTest {
		
	protected static SalesOrderService salesOrderService;
	protected static TaxCalculator taxCalculator;
	protected static ShippingCalculator shippingCalculator;
	static EntityManager emgr;
	protected SalesOrder salesOrder;
	
	@BeforeSuite(timeOut=30000)
	public void setUp() {
		
		salesOrderService = new SalesOrderService();
		taxCalculator = new TaxCalculator();
		shippingCalculator = new ShippingCalculator();
		emgr = Persistence.createEntityManagerFactory("PrototypeDB").createEntityManager();
		
	}

	@BeforeClass
	public static void initSalesOrderService() {
		salesOrderService.setTaxCalculator(taxCalculator);
		salesOrderService.setShippingCalculator(shippingCalculator);
		salesOrderService.setEntityManager(emgr);
	}

	
	@Test
	public void testCreateSalesOrder() {

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
		
		// Exercise the service
		try {
			salesOrderService.createSalesOrder(salesOrder);			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		System.out.println("SalesOrder Confirmation Order # " + salesOrder.getOrderNumber());
		System.out.println("SalesOrder UID: " + salesOrder.getSalesOrderUid());
		assertNotNull(salesOrder.getOrderNumber());
		assertNotNull(salesOrder.getSalesOrderUid());

	}

	@Test(dependsOnMethods={"testCreateSalesOrder"})
	public void testAddLineItem() {
		// Assumes pre-existence of a SalesOrder
		// The service method should take a SalesOrder object as input as well as a CatalogItem and Quantity
		Query findCatalogItem = emgr.createQuery("select ci from CatalogItem ci where ci.catalogItemUid = 1");
		CatalogItem catItem = (CatalogItem) findCatalogItem.getSingleResult();
		
		int initialNumberOfLines = salesOrder.getLineItems().size();
		int orderQuantity = 10;
		SalesOrder delta = salesOrderService.addLineItem(salesOrder, catItem, orderQuantity);
		// Verify results, the changed order should not be the same as the presented order
		AssertJUnit.assertNotSame(delta.getLineItems().size(), initialNumberOfLines);				
	}

	// Need to figure out how to add a dataProvider to this for passing in test data
	@Test(dependsOnMethods = {"testCreateSalesOrder"})
	public void testRetrieveSalesOrder() {
		System.out.println("Local salesOrder orderUid: " + this.salesOrder.getSalesOrderUid());
		SalesOrder retrievedOrder = salesOrderService.retrieveSalesOrder(this.salesOrder.getSalesOrderUid());
		AssertJUnit.assertSame(retrievedOrder, this.salesOrder);
	}
	
	@Test(dependsOnMethods={"testAddLineItem"})
	public void testRemoveLineItem() throws Exception {
		// Service method takes a SalesOrderLine object for the LineItem
		SalesOrderLine orderLine = this.salesOrder.getLineItems().get(0);
		if (orderLine != null) {
			salesOrderService.removeSalesOrderLine(this.salesOrder, orderLine);
			Query findOrderLine = emgr.createQuery("select sol from SalesOrderLine sol where sol.salesOrderLineUid = :lineUid");
			findOrderLine.setParameter("lineUid", orderLine.getSalesOrderLineUid());
			SalesOrderLine retrievedLine = null;
			try {
				retrievedLine = (SalesOrderLine) findOrderLine.getSingleResult();							
			} catch (javax.persistence.NoResultException exc) {
				System.out.println("As expected, no line item was retrieved from the database indicating successful removal");
			}
			assertNull(retrievedLine);			
		} else {
			Exception exc = new Exception("No order line found for the sales order being tested");
			throw exc;
		}
	}
	
	@Test(dependsOnMethods={"testCreateSalesOrder"})
	public void cancelSalesOrder() {
		// Service method takes a SalesOrder object to cancel--entity is maintained in the database but marked as cancelled
		salesOrderService.cancelSalesOrder(salesOrder);
		assertEquals(salesOrder.getSalesOrderStatus(), "Cancelled");
	}

}
