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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the product_category database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="product_category")
@NamedQuery(name="ProductCategory.findAll", query="SELECT p FROM ProductCategory p")
public class ProductCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_CATEGORY_UID")
	private int productCategoryUid;

	@Column(name="CATEGORY_DESCRIPTION")
	private String categoryDescription;

	@Column(name="CATEGORY_NAME")
	private String categoryName;
	
	@Column(name="PARENT_PRODUCT_CATEGORY_UID", updatable=false)
	private int parentProductCategoryUid;

	@ManyToOne
	@JoinColumn(name="PARENT_PRODUCT_CATEGORY_UID", insertable=false, updatable=false)
	@JsonBackReference
	@JsonIgnore
	private ProductCategory parentProductCategory;
	
	/*
	@OneToMany(targetEntity=ProductCategory.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="subCategories")
	@JsonManagedReference
	private List<ProductCategory> subCategories;
	*/
	
	//bi-directional many-to-one association to ProductCategoryItem
	@OneToMany(targetEntity=ProductCategoryItem.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="productCategory")
	@JsonBackReference
	private List<ProductCategoryItem> productCategoryItems;

	public ProductCategory() {
	}

	public ProductCategory(Integer categoryParentUid, String categoryName, String categoryDescription) {
		this.parentProductCategoryUid = categoryParentUid;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}
	
	public int getProductCategoryUid() {
		return this.productCategoryUid;
	}

	public void setProductCategoryUid(int productCategoryUid) {
		this.productCategoryUid = productCategoryUid;
	}
	
	public int getParentProductCategoryUid() {
		return this.parentProductCategoryUid;
	}
	
	public void setParentProductCategoryUid(int parentProductCategoryUid) {
		this.parentProductCategoryUid = parentProductCategoryUid;
	}

	public String getCategoryDescription() {
		return this.categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ProductCategory getParentProductCategory() {
		return this.parentProductCategory;
	}

	public void setParentProductCategory(ProductCategory parentProductCategory) {
		this.parentProductCategory = parentProductCategory;
	}

	public List<ProductCategoryItem> getProductCategoryItems() {
		return this.productCategoryItems;
	}

	public void setProductCategoryItems(List<ProductCategoryItem> productCategoryItems) {
		this.productCategoryItems = productCategoryItems;
	}

	public ProductCategoryItem addProductCategoryItem(ProductCategoryItem productCategoryItem) {
		getProductCategoryItems().add(productCategoryItem);
		productCategoryItem.setProductCategory(this);

		return productCategoryItem;
	}

	public ProductCategoryItem removeProductCategoryItem(ProductCategoryItem productCategoryItem) {
		getProductCategoryItems().remove(productCategoryItem);
		productCategoryItem.setProductCategory(null);

		return productCategoryItem;
	}

}