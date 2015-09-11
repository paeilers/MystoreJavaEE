package business.salesOrderMgmt.boundaries;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import business.salesOrderMgmt.controls.CatalogService;
import business.salesOrderMgmt.entity.Catalog;
import business.salesOrderMgmt.entity.ProductCategory;
import business.salesOrderMgmt.entity.ProductCategoryItem;

@Stateless
@Path("")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CatalogResource {

	@Inject
	CatalogService catalogService;
	
	@GET
	@Path("catalog")
	public Response getCatalog(@QueryParam("callback") String callback) {
		Response response = null;

		Catalog retrievedCatalog = catalogService.retrieveCurrentCatalog();
		if (retrievedCatalog == null) {
			System.out.println("No current Catalog could be found.");
			throw new WebApplicationException(404);
		}	
		response = Response.status(Status.OK).entity(retrievedCatalog).build();
		return response;	
	}

	@GET
	@Path("productCategories/{catalogUid}")
	public List<ProductCategory> getCatalogProductCategories(@PathParam("catalogUid") Integer catalogUid, @QueryParam("callback") String callback) {
		List<ProductCategory> productCategories = null;
		if (catalogUid != null) {
			productCategories = catalogService.retrieveCatalogProductCategories(catalogUid);			
		} else {
			productCategories = catalogService.retrieveAllProductCategories();
		}
		if (productCategories != null) {
			System.out.println("List of Product Categories retrieved");
		} else {
			throw new WebApplicationException(404);
		}
		return productCategories;
	}
	
	@GET
	@Path("productCategories")
	public List<ProductCategory> getAllProductCategories(@QueryParam("callback") String callback) {
		List<ProductCategory> productCategories = catalogService.retrieveAllProductCategories();
		if (productCategories != null) {
			System.out.println("List of Product Categories retrieved");
		} else {
			throw new WebApplicationException(404);
		}
		return productCategories;
	}
	
	@GET
	@Path("productCategoryItems/{categoryUid}")
	public List<ProductCategoryItem> getProductCategoryItems(@PathParam("categoryUid") Integer categoryUid, @QueryParam("callback") String callback) {
		List<ProductCategoryItem> productCategoryItems = catalogService.retrieveAllProductCategoryItems(categoryUid);
		if (productCategoryItems != null) {
			System.out.println("List of Product Category Items retrieved");
		} else {
			throw new WebApplicationException(404);
		}
		return productCategoryItems;
		
	}

	@GET
	@Path("productCategoryItems")
	public List<ProductCategoryItem> getAllProductCategoryItems(@QueryParam("callback") String callback) {
		List<ProductCategoryItem> productCategoryItems = catalogService.retrieveAllProductCategoryItems();
		if (productCategoryItems != null) {
			System.out.println("List of Product Category Items retrieved");
		} else {
			throw new WebApplicationException(404);
		}
		return productCategoryItems;
		
	}
	
}
