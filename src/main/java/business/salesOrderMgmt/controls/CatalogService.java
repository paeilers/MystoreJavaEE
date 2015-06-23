package business.salesOrderMgmt.controls;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import business.salesOrderMgmt.entity.Catalog;
import business.salesOrderMgmt.entity.CatalogItem;
import business.salesOrderMgmt.entity.Item;
import business.salesOrderMgmt.entity.ProductCategory;
import business.salesOrderMgmt.entity.ProductCategoryItem;

@Stateless
public class CatalogService implements Serializable {

	@PersistenceContext(name="PrototypeDB")
	EntityManager emgr;
		
	// Setter methods required required for all injected properties to support unit testing as the 
	// dependency injection doesn't occur during execution of unit tests
	public void setEntityManager(EntityManager emgr) {
		this.emgr = emgr;
	}
	
	public EntityManager getEntityManager() {
		return this.emgr;
	}
	
	public Catalog createCatalog(Date validFrom, Date validThrough) {
		Catalog catalog = new Catalog();
		catalog.setValidFrom(validFrom);
		catalog.setValidThrough(validThrough);
		emgr.persist(catalog);
		return catalog;
	}
	
	public Catalog retrieveCatalog(Integer catalogUid) {
		Query catalogQuery = emgr.createQuery("Select c from Catalog c where c.catalogUid = :catalogUid");
		catalogQuery.setParameter("catalogUid", catalogUid);
		Catalog catalog = null;
		catalog = (Catalog) catalogQuery.getSingleResult();
		return catalog;
	}		
	
	public Catalog retrieveCurrentCatalog() {
		Query catalogQuery = emgr.createQuery("Select c from Catalog c where c.validFrom <= CURRENT_DATE and (c.validThrough IS NULL or c.validThrough >= CURRENT_DATE)");
		Catalog catalog = (Catalog) catalogQuery.getSingleResult();
		return catalog;
	}		
	
	public ProductCategory createProductCategory(ProductCategory categoryParent, String categoryName, String categoryDescription) {
		ProductCategory productCategory = new ProductCategory(categoryParent.getParentProductCategoryUid(), categoryName, categoryDescription);
		emgr.persist(productCategory);
		return productCategory;
	}
	
	public ProductCategory retrieveProductCategory(Integer productCategoryUid) {
		System.out.println("CatalogService is attempting to retrieve ProductCategory with Uid: " + productCategoryUid);
		Query productCatalogQuery = emgr.createQuery("Select pc from ProductCategory pc where pc.productCategoryUid = :productCategoryUid");
		ProductCategory productCategory = (ProductCategory) productCatalogQuery.getSingleResult();
		return productCategory;
	}

	public List<ProductCategory> retrieveCatalogProductCategories(Integer catalogUid) {
		System.out.println("CatalogService is attempting to retrieve ProductCategories for a given catalog");
		Query query = emgr.createQuery("Select pc from ProductCategory pc, ProductCategoryItem pci, Item i, CatalogItem ci "
										+ "where pc.productCategoryUid = pci.productCategoryUid "
										+ "and pci.itemUid = i.itemUid "
										+ "and i.itemUid = ci.itemUid "
										+ "and ci.catalogUid = :catalogUid");
		query.setParameter("catalogUid", catalogUid);
		List<ProductCategory> productCategories = (List<ProductCategory>) query.getResultList();
		return productCategories;
	}
	
	public List<ProductCategory> retrieveAllProductCategories() {
		System.out.println("CatalogService is attempting to retrieve All ProductCategories");
		Query query = emgr.createQuery("Select pc from ProductCategory pc");
		List<ProductCategory> productCategories = (List<ProductCategory>) query.getResultList();
		return productCategories;
	}

	public Item createItem(ProductCategory productCategory, String productName, String productDescription) {
		Item item = new Item(productCategory.getProductCategoryUid(), productName, productDescription);
		emgr.persist(item);
		return item;
	}
	
	public Item retrieveItem(int itemUid) {
		System.out.println("CatalogService is attempting to retrieve Item with Uid: " + itemUid);
		Query itemQuery = emgr.createQuery("Select i from Item i where i.itemUid = :itemyUid");
		itemQuery.setParameter("itemUid", itemUid);
		Item item = (Item) itemQuery.getSingleResult();
		return item;
		
	}
	
	public CatalogItem createCatalogItem(Item item, Catalog catalog) {
		CatalogItem catalogItem = new CatalogItem(item, catalog);
		emgr.persist(catalogItem);
		return catalogItem;
	}
	
	public CatalogItem retrieveCatalogItem(Integer catalogItemUid) {
		System.out.println("CatalogService is attempting to retrieve CatalogItem with Uid: " + catalogItemUid);
		Query catalogItemQuery = emgr.createQuery("Select ci from CatalogItem c where c.catalogItemUid = :catalogItemUid");
		catalogItemQuery.setParameter("catalogItemUid", catalogItemUid);
		CatalogItem catalogItem = (CatalogItem) catalogItemQuery.getSingleResult();
		return catalogItem;
	}	
	
	public List<CatalogItem> retrieveAllCatalogItems(Catalog catalog) {
		Query catalogItemQuery = emgr.createQuery("Select ci from CatalogItem c where c.catalogItemUid = :catalogUid");
		catalogItemQuery.setParameter("catalogUid", catalog.getCatalogUid());
		System.out.println("CatalogService is attempting to retrieve all CatalogItems for a given Catalog with Uid: " + catalog.getCatalogUid());
		List<CatalogItem> catalogItems = (List<CatalogItem>) catalogItemQuery.getResultList(); 
		return catalogItems;
	}		
	
	public ProductCategoryItem createProductCategoryItem(ProductCategory productCategory, Item item) {
		ProductCategoryItem productCategoryItem = new ProductCategoryItem(productCategory.getProductCategoryUid(), item.getItemUid());
		emgr.persist(productCategoryItem);
		return productCategoryItem;
	}
	
	public ProductCategoryItem retrieveProductCategoryItem(Integer productCategoryItemUid) {
		System.out.println("CatalogService is attempting to retrieve ProductCategoryItem with Uid: " + productCategoryItemUid);
		Query query = emgr.createQuery("Select pci from ProductCategoryItem pci where pci.productCategoryItemUid = :productCategoryItemUid");
		query.setParameter("productCategoryItemUid", productCategoryItemUid);
		ProductCategoryItem productCategoryItem = (ProductCategoryItem) query.getSingleResult();
		return productCategoryItem;
		
	}
	
	public List<ProductCategoryItem> retrieveAllProductCategoryItems(Integer productCategoryUid) {
		Query query = emgr.createQuery("Select pci from ProductCategoryItem pci where pci.productCategoryUid = :productCategoryUid");
		query.setParameter("productCategoryUid", productCategoryUid);
		System.out.println("CatalogService is attempting to retrieve all ProductCategoryItems for a given ProductCategory with Uid: " + productCategoryUid);
		List<ProductCategoryItem> productCategoryItems = (List<ProductCategoryItem>) query.getResultList(); 
		return productCategoryItems;
	}	

	public List<ProductCategoryItem> retrieveAllProductCategoryItems() {
		Query query = emgr.createQuery("Select pci from ProductCategoryItem pci");
		System.out.println("CatalogService is attempting to retrieve all ProductCategoryItems for all Product Categories");
		List<ProductCategoryItem> productCategoryItems = (List<ProductCategoryItem>) query.getResultList(); 
		return productCategoryItems;
	}	

	// Need to add methods for updating as well as deleting/removing for all of the related Catalog objects
}
