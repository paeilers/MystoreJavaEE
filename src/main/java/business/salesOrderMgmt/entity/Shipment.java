package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the shipment database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Shipment.findAll", query="SELECT s FROM Shipment s")
public class Shipment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SHIPMENT_UID")
	private int shipmentUid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DELIVERED_DATE")
	private Date deliveredDate;

	@Column(name="SHIPMENT_STATUS")
	private String shipmentStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SHIPPED_DATE")
	private Date shippedDate;

	private String shipper;

	@Column(name="SHIPPER_TRACKING_NUMBER")
	private String shipperTrackingNumber;
	
	@Version
	@Column(name="LAST_UPDATED_TIME")
	private Timestamp lastUpdatedTime;

	//bi-directional many-to-one association to SalesInvoice
	@ManyToOne
	@JoinColumn(name="SALES_INVOICE_UID")
	private SalesInvoice salesInvoice;

	public Shipment() {
	}

	public int getShipmentUid() {
		return this.shipmentUid;
	}

	public void setShipmentUid(int shipmentUid) {
		this.shipmentUid = shipmentUid;
	}

	public Date getDeliveredDate() {
		return this.deliveredDate;
	}

	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getShipmentStatus() {
		return this.shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public Date getShippedDate() {
		return this.shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public String getShipper() {
		return this.shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getShipperTrackingNumber() {
		return this.shipperTrackingNumber;
	}

	public void setShipperTrackingNumber(String shipperTrackingNumber) {
		this.shipperTrackingNumber = shipperTrackingNumber;
	}

	public SalesInvoice getSalesInvoice() {
		return this.salesInvoice;
	}

	public void setSalesInvoice(SalesInvoice salesInvoice) {
		this.salesInvoice = salesInvoice;
	}

}