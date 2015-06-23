package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the catalog_item database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="catalog_item")
@NamedQuery(name="CatalogItem.findAll", query="SELECT c FROM CatalogItem c")
public class CatalogItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CATALOG_ITEM_UID")
	private int catalogItemUid;
	
	@Column(name="ITEM_UID")
	private int itemUid;

	@Column(name="ITEM_PRICE")
	private BigDecimal itemPrice;

	//bi-directional many-to-one association to Catalog
	@ManyToOne
	@JoinColumn(name="CATALOG_UID", insertable=false, updatable=false)
	@JsonBackReference
	@JsonIgnore
	private Catalog catalog;

	//bi-directional many-to-one association to Item
	@ManyToOne(targetEntity=Item.class, fetch=FetchType.EAGER)
	@JoinColumn(name="ITEM_UID", insertable=false, updatable=false)
	@JsonManagedReference
	private Item item;
	
	@OneToMany(targetEntity=SalesOrderLine.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="catalogItem")
	@JsonBackReference
	@JsonIgnore
	private List<SalesOrderLine> salesOrderLines;
	
	public CatalogItem() {
	}

	public CatalogItem(Item item, Catalog catalog) {
		this.setItem(item);
		this.setCatalog(catalog);
	}

	public int getCatalogItemUid() {
		return this.catalogItemUid;
	}
	
	public void setCatalogItemUid(int catItemUid) {
		this.catalogItemUid = catItemUid;
	}

	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}