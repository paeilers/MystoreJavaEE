package com.mystoreJEE.salesOrderMgmt;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import business.salesOrderMgmt.controls.CatalogService;
import business.salesOrderMgmt.entity.Catalog;

public class CatalogServiceTest {
	
	protected static CatalogService catalogService;
	static EntityManager emgr;

	@BeforeSuite(timeOut=30000)
	public void setUp() {
		
		catalogService = new CatalogService();
		emgr = Persistence.createEntityManagerFactory("PrototypeDB").createEntityManager();
		
	}

	@BeforeClass
	public static void initSalesOrderService() {
		catalogService.setEntityManager(emgr);
	}

	@Test
	public void testRetrieveCurrentCatalog() {
		Catalog retrievedCatalog = catalogService.retrieveCurrentCatalog();
		AssertJUnit.assertNotNull(retrievedCatalog);
	}
	
}
