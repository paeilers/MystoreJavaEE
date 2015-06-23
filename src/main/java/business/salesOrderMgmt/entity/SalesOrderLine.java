package business.salesOrderMgmt.entity;

/* Author: PEilers */

import java.io.Serializable;
import java.math.BigDecimal;

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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the sales_order_line database table.
 * 
 */
@Entity
@XmlRootElement 
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="sales_order_line")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="salesOrderLineUid")
@NamedQuery(name="SalesOrderLine.findAll", query="SELECT s FROM SalesOrderLine s")
public class SalesOrderLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SALES_ORDER_LINE_UID")
	private int salesOrderLineUid;
	
	@Column(name="SALES_ORDER_UID")
	private int salesOrderUid;
	
	@Column(name="CATALOG_ITEM_UID")
	private int catalogItemUid;

	@Column(name="EXTENDED_PRICE")
	private BigDecimal extendedPrice;

	@Column(name="ITEM_QUANTITY")
	private int itemQuantity;
	
	@Column(name="ITEM_PRICE")
	private BigDecimal itemPrice;

	@ManyToOne
	@JoinColumn(name="SHIPMENT_UID")
	private Shipment shipment;
	
	//bi-directional many-to-one association to SalesOrder
//	@JoinColumn(name="SALES_ORDER_UID", insertable=false, updatable=false)
	@ManyToOne
	@JoinColumn(name="SALES_ORDER_UID", insertable=false, updatable=false)
	@JsonBackReference
	@JsonIgnore
	private SalesOrder salesOrder;
	
	//bi-directional many-to-one association to CatalogItem
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CATALOG_ITEM_UID", insertable=false, updatable=false)
	@JsonManagedReference
	private CatalogItem catalogItem;

	public SalesOrderLine() {
	}
	@XmlInverseReference(mappedBy="lineItems")	
	public SalesOrder getSalesOrder() {
		return this.salesOrder;
	}
	
	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public void setCatalogItem(CatalogItem item) {
		this.catalogItem = item;
		System.out.println("The catalog item for the line is: " + this.catalogItem);
	}
	
	public void calculateExtendedPrice() {
		this.extendedPrice = this.catalogItem.getItemPrice().multiply(new BigDecimal(this.itemQuantity));
	}
	
	public int getSalesOrderLineUid() {
		return this.salesOrderLineUid;
	}

	public void setSalesOrderLineUid(int salesOrderLineUid) {
		this.salesOrderLineUid = salesOrderLineUid;
	}

	public int getSalesOrderUid() {
		return salesOrderUid;
	}

	public void setSalesOrderUid(int salesOrderUid) {
		this.salesOrderUid = salesOrderUid;
	}

	public CatalogItem getCatalogItem() {
		return this.catalogItem;
	}
	
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	
	public BigDecimal getExtendedPrice() {
		if (this.extendedPrice == null) { 
			calculateExtendedPrice(); 
		} 
		return this.extendedPrice;
	}

	public int getItemQuantity() {
		return this.itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}
	
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Shipment getShipmentUid() {
		return this.shipment;
	}

	public void setShipmentUid(Shipment shipment) {
		this.shipment = shipment;
	}

}