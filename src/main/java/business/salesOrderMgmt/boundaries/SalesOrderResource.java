package business.salesOrderMgmt.boundaries;

/* Author: PEilers */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import business.salesOrderMgmt.controls.DiscountCalculator;
//import model.SalesOrder;
import business.salesOrderMgmt.controls.SalesOrderService;
import business.salesOrderMgmt.controls.ShippingCalculator;
import business.salesOrderMgmt.controls.TaxCalculator;
import business.salesOrderMgmt.entity.SalesOrder;

import com.google.gson.Gson;

/* This boundary service should expose course grained methods to the client */

@Stateless
@Path("salesOrders")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SalesOrderResource {
	
	@Inject
	SalesOrderService salesOrderService;
	
	@Inject
	TaxCalculator taxCalculator;
	
	@Inject
	ShippingCalculator shippingCalculator;
	
	@Inject
	DiscountCalculator discountCalculator;
	
	@GET
	@Path("review")
	public Response getSalesOrder(@QueryParam("callback") String callback, @QueryParam("promoCode") String promoCode, @QueryParam("zipCode") Integer zipCode, @QueryParam("subTotal") BigDecimal subTotal) {
		Response response = null;
		BigDecimal discount = discountCalculator.getDiscount(promoCode);
		BigDecimal taxAmount = taxCalculator.getTaxRate(zipCode).multiply((subTotal.subtract(discount)));
		BigDecimal shippingAmount = shippingCalculator.getShippingCost(zipCode, subTotal);
		
		Map<String, BigDecimal> keyValues = new HashMap();
		keyValues.put("discount", discount);
		keyValues.put("salesTax", taxAmount);
		keyValues.put("shipping", shippingAmount);
		keyValues.put("total", subTotal.subtract(discount).add(taxAmount).add(shippingAmount));
		
		response = Response.status(Status.OK).entity(keyValues).build();
//		response.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8090");
//		response.getHeaders().add("Content-Type", "application/json");
//		response.getHeaders().add("X-Served-By", "MyStoreJEE");			

		return response;
	}

	
	@GET
	@Path("{orderUid}")
	public Response getSalesOrder(@PathParam("orderUid") Integer orderUid, @QueryParam("callback") String callback) {
		System.out.println("Entered resource call to getSalesOrder()...");
		Response response = null;
		SalesOrder retrievedSalesOrder = salesOrderService.retrieveSalesOrder(orderUid);
		if (retrievedSalesOrder != null) {
			response = Response.status(Status.OK).entity(retrievedSalesOrder).build();
		} else {
			System.out.println("No matching SalesOrder could be found.");
			throw new WebApplicationException(404);
		}				
		return response;	
	}


	@POST
	@Path("new")
	public Response submitSalesOrder(String jsonSalesOrder) {
		// jsonSalesOrder is already in JSON format but without passing it in as a string the REST resource sends back a 400 status
		Gson gsonSalesOrder = new Gson();
		SalesOrder salesOrder = gsonSalesOrder.fromJson(jsonSalesOrder, SalesOrder.class);
		SalesOrder persistedSalesOrder = null;
		Response response = null;
		
		try {
			persistedSalesOrder = salesOrderService.createSalesOrder(salesOrder);
			response = Response.status(Status.OK).entity(persistedSalesOrder).build();
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
//		response.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8090");
//		response.getHeaders().add("Content-Type", "application/json");
//		response.getHeaders().add("X-Served-By", "MyStoreJEE");
		
		return response;
	}

}
