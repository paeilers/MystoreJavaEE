package business.salesOrderMgmt.controls;

//import model.SalesOrder;
/* This boundary service should expose course grained methods to the client */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import business.salesOrderMgmt.entity.CatalogItem;
import business.salesOrderMgmt.entity.SalesOrder;
import business.salesOrderMgmt.entity.SalesOrderLine;

/* This boundary service should expose course grained methods to the client */
@Stateless
@DependsOn({"TaxCalculator","ShippingCalculator"})
public class SalesOrderService implements Serializable {

	private static final long serialVersionUID = -3072083708013257602L;

	@PersistenceContext(name="PrototypeDB")
	EntityManager emgr;
		
	// Inject other EJB services here as required to support complex transactions
	@Inject
	TaxCalculator taxCalculator;
	
	@Inject
	ShippingCalculator shippingCalculator;
	
	@Inject
	DiscountCalculator discountCalculator;
	
	// Setter methods required required for all injected properties to support unit testing as the 
	// dependency injection doesn't occur during execution of unit tests
	public void setEntityManager(EntityManager emgr) {
		this.emgr = emgr;
	}
	
	public EntityManager getEntityManager() {
		return this.emgr;
	}
	
	// Setter methods required required for all injected properties to support unit testing as the 
	// dependency injection doesn't occur during execution of unit tests
	public void setTaxCalculator (TaxCalculator taxCalculator) {
		// This should be injected at runtime but the setter is required for unit testing of the service
		this.taxCalculator = taxCalculator;
	}
	
	// Setter methods required required for all injected properties to support unit testing as the 
	// dependency injection doesn't occur during execution of unit tests
	public void setShippingCalculator (ShippingCalculator shippingCalculator) {
		// This should be injected at runtime but the setter is required for unit testing of the service
		this.shippingCalculator = shippingCalculator;
	}
		
	public SalesOrder createSalesOrder(SalesOrder salesOrder) throws Exception {
	System.out.println("Creating new sales order...");
		// Start a transaction
		// Cannot use an EntityTransaction while using JTA but is required for RESOURCE_LOCAL persistence setting
		// EntityTransaction etx = emgr.getTransaction();
//		etx.begin();
		salesOrder.setOrderNumber(generateOrderNumber(salesOrder));
		salesOrder.setSalesOrderStatus("NEW");
		salesOrder.setSalesOrderDate(new Date());
		List<SalesOrderLine> lineItems = salesOrder.getLineItems();
		Integer salesOrderUid;
		emgr.persist(salesOrder);
		emgr.flush(); // Flush required here in order to persist the dependent lineItems with a non-zero foreign key
		salesOrderUid = salesOrder.getSalesOrderUid();
		// This seems to be necessary to persist the lineItems with the appropriate foreign key, else it ends up being zero
		for (int i = 0; i < salesOrder.getLineItems().size(); i++) {
			lineItems.get(i).setSalesOrderUid(salesOrderUid);
		}
		emgr.flush();
		//		etx.commit();
		return salesOrder;
	}
	
	public SalesOrderLine addLineItem(Integer salesOrderUid, SalesOrderLine lineItem) {
		System.out.println("Adding new line to sales order...");
		lineItem.setSalesOrderLineUid(salesOrderUid);
		emgr.persist(lineItem);
		emgr.flush();
//		emgr.getTransaction().commit();		
		return lineItem;
	}
	
	public SalesOrder addLineItem(SalesOrder salesOrder, CatalogItem catItem, int itemQuantity) {
	System.out.println("Adding new line to sales order...");
		SalesOrderLine lineItem = new SalesOrderLine();
		lineItem.setCatalogItem(catItem);
		lineItem.setItemQuantity(itemQuantity);
		lineItem.setItemPrice(catItem.getItemPrice());
		lineItem.calculateExtendedPrice();
		salesOrder.addOrderLineItem(lineItem);
		lineItem.setSalesOrderUid(salesOrder.getSalesOrderUid());
		// Update calculated fields
    	salesOrder.calculateSubTotal();
    	salesOrder.setDiscount(discountCalculator.getDiscount(salesOrder.getPromoCode()));
    	salesOrder.getSubTotal().subtract(salesOrder.getDiscount());
		// Start a transaction, persist the SalesOrderLine and update the SalesOrder

    	//Cannot use an EntityTransaction while using JTA
 //		emgr.getTransaction().begin();
		emgr.merge(salesOrder);
		emgr.flush();
//		emgr.getTransaction().commit();		
		return salesOrder;
	}
	
	public SalesOrder retrieveSalesOrder(Integer orderUid) {
		System.out.println("SalesOrderService is attempting to retrieve SalesOrder with Uid: " + orderUid);
		Query salesOrderQuery = emgr.createQuery("Select so from SalesOrder so join fetch so.lineItems where so.salesOrderUid = :orderUid");
		salesOrderQuery.setParameter("orderUid", orderUid);
		SalesOrder salesOrder = null;
		try {
			salesOrder = (SalesOrder) salesOrderQuery.getSingleResult();
			if (salesOrder != null) {
				
				System.out.println("subTotal is: " + salesOrder.getSubTotal());
				System.out.println("discount is: " + salesOrder.getDiscount());
				System.out.println("promoCode is: " + salesOrder.getPromoCode());
				
				BigDecimal totalAfterDiscount = salesOrder.getSubTotal().subtract(salesOrder.getDiscount());
				BigDecimal salesTax = totalAfterDiscount.multiply(taxCalculator.getTaxRate(salesOrder.getBillToZipCode()));
				salesOrder.setSalesTax(salesTax);
				salesOrder.setShipping(shippingCalculator.getShippingCost(salesOrder.getBillToZipCode(), salesOrder.getSubTotal()));
				salesOrder.calculateTotal();		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return salesOrder;
	}	


	public SalesOrder removeSalesOrderLine(SalesOrder salesOrder, SalesOrderLine salesOrderLine) {
	System.out.println("About to remove Sales Order Line with UID: " + salesOrderLine.getSalesOrderLineUid());
//		emgr.getTransaction().begin();
		SalesOrderLine retrieved = emgr.find(SalesOrderLine.class, salesOrderLine.getSalesOrderLineUid(), LockModeType.PESSIMISTIC_WRITE);
		emgr.remove(retrieved);
		salesOrder.removeOrderLineItem(salesOrderLine);
//		emgr.getTransaction().commit();	
		return salesOrder;
	}
	
	public void cancelSalesOrder(SalesOrder salesOrder) {
	System.out.println("About to cancel Sales Order with UID: " + salesOrder.getSalesOrderUid());
		// Update salesOrderStatus to "Cancelled" and send email to customer with confirmation of cancellation
//		emgr.getTransaction().begin();
		SalesOrder retrieved = emgr.find(SalesOrder.class, salesOrder.getSalesOrderUid(), LockModeType.PESSIMISTIC_WRITE);
		salesOrder.setSalesOrderStatus("Cancelled");
		emgr.merge(retrieved);
		// Send email via the email service (TBD)
//		emgr.getTransaction().commit();
	}
		
	private String generateOrderNumber(SalesOrder salesOrder) {
		// Simplified for prototyping purposes
		return String.valueOf(new Date().getTime());
	}
	
}
