package business.salesOrderMgmt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the product_category_item database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="product_category_item")
@NamedQuery(name="ProductCategoryItem.findAll", query="SELECT p FROM ProductCategoryItem p")
public class ProductCategoryItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_CATEGORY_ITEM_UID")
	private int productCategoryItemUid;
	
	@Column(name="PRODUCT_CATEGORY_UID")
	private int productCategoryUid;
	
	@Column(name="ITEM_UID")
	private int itemUid;

	//bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name="ITEM_UID", insertable=false, updatable=false)
	@JsonManagedReference
	private Item item;

	//bi-directional many-to-one association to ProductCategory
	@ManyToOne
	@JoinColumn(name="PRODUCT_CATEGORY_UID", insertable=false, updatable=false)
	@JsonManagedReference
	private ProductCategory productCategory;

	public ProductCategoryItem() {
	}
	
	public ProductCategoryItem(int productCategoryUid, int itemUid) {
		this.productCategoryUid = productCategoryUid;
		this.itemUid = itemUid; 
	}

	public int getProductCategoryItemUid() {
		return this.productCategoryItemUid;
	}

	public void setProductCategoryItemUid(int productCategoryItemUid) {
		this.productCategoryItemUid = productCategoryItemUid;
	}

	public int getProductCategoryUid() {
		return this.productCategoryUid;
	}

	public void setProductCategoryUid(int productCategoryUid) {
		this.productCategoryUid = productCategoryUid;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ProductCategory getProductCategory() {
		return this.productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

}