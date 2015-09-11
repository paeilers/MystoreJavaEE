package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the sales_invoice database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="sales_invoice")
@NamedQuery(name="SalesInvoice.findAll", query="SELECT s FROM SalesInvoice s")
public class SalesInvoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SALES_INVOICE_UID")
	private int salesInvoiceUid;

	@Column(name="INVOICE_AMOUNT")
	private BigDecimal invoiceAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INVOICE_DATE")
	private Date invoiceDate;

	@Column(name="INVOICE_STATUS")
	private String invoiceStatus;
	
	@Version
	@Column(name="LAST_UPDATED_TIME")
	private Timestamp lastUpdatedTime;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="salesInvoice")
	private List<Shipment> shipments;

	public SalesInvoice() {
	}

	public int getSalesInvoiceUid() {
		return this.salesInvoiceUid;
	}

	public void setSalesInvoiceUid(int salesInvoiceUid) {
		this.salesInvoiceUid = salesInvoiceUid;
	}

	public BigDecimal getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setSalesInvoice(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setSalesInvoice(null);

		return shipment;
	}

}