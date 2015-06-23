package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ITEM_UID")
	private int itemUid;

	@Column(name="PRODUCT_CATEGORY_UID")
	private int productCategoryUid;

	@Column(name="PRODUCT_DESCRIPTION")
	private String productDescription;

	@Column(name="PRODUCT_NAME")
	private String productName;
	
	//bi-directional many-to-one association to CatalogItem
	@OneToMany(targetEntity=CatalogItem.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="item")
	@JsonBackReference
	private List<CatalogItem> catalogItems;

	//bi-directional many-to-one association to ProductCategoryItem
	@OneToMany(targetEntity=ProductCategoryItem.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="item")
	@JsonBackReference
	private List<ProductCategoryItem> productCategoryItems;

	public Item() {
	}

	public Item(Integer productCategoryUid, String productName, String productDescription) {
		this.productCategoryUid = productCategoryUid;
		this.productName = productName;
		this.productDescription = productDescription;
	}
	public int getItemUid() {
		return this.itemUid;
	}

	public void setItemUid(int itemUid) {
		this.itemUid = itemUid;
	}

	public int getProductCategoryUid() {
		return this.productCategoryUid;
	}

	public void setProductCategoryUid(int productCategoryUid) {
		this.productCategoryUid = productCategoryUid;
	}

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

/*
	public List<CatalogItem> getCatalogItems() {
		return this.catalogItems;
	}

	public void setCatalogItems(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}

	public CatalogItem addCatalogItem(CatalogItem catalogItem) {
		getCatalogItems().add(catalogItem);
		catalogItem.setItem(this);

		return catalogItem;
	}

	public CatalogItem removeCatalogItem(CatalogItem catalogItem) {
		getCatalogItems().remove(catalogItem);
		catalogItem.setItem(null);

		return catalogItem;
	}

	public List<ProductCategoryItem> getProductCategoryItems() {
		return this.productCategoryItems;
	}

	public void setProductCategoryItems(List<ProductCategoryItem> productCategoryItems) {
		this.productCategoryItems = productCategoryItems;
	}

	public ProductCategoryItem addProductCategoryItem(ProductCategoryItem productCategoryItem) {
		getProductCategoryItems().add(productCategoryItem);
		productCategoryItem.setItem(this);

		return productCategoryItem;
	}

	public ProductCategoryItem removeProductCategoryItem(ProductCategoryItem productCategoryItem) {
		getProductCategoryItems().remove(productCategoryItem);
		productCategoryItem.setItem(null);

		return productCategoryItem;
	}
*/
	
}