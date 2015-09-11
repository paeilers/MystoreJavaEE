package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the sales_order database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="sales_order")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="salesOrderUid")
@NamedQuery(name="SalesOrder.findAll", query="SELECT s FROM SalesOrder s")
public class SalesOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SALES_ORDER_UID")
	private int salesOrderUid;
	
	@Column(name="ORDER_NUMBER")
	private String orderNumber;

	@Column(name="BILL_TO_CITY")
	private String billToCity;

	@Column(name="BILL_TO_FIRST_NAME")
	private String billToFirstName;

	@Column(name="BILL_TO_LAST_NAME")
	private String billToLastName;

	@Column(name="BILL_TO_STATE")
	private String billToState;

	@Column(name="BILL_TO_STREET_NAME")
	private String billToStreetName;

	@Column(name="BILL_TO_STREET_NUMBER")
	private String billToStreetNumber;

	@Column(name="BILL_TO_UNIT_NUMBER")
	private String billToUnitNumber;

	@Column(name="BILL_TO_ZIP_CODE")
	private int billToZipCode;

	@Column(name="CREDIT_CARD_CSV")
	private int creditCardCsv;

	@Column(name="CREDIT_CARD_EXPIRY_MONTH")
	private String creditCardExpiryMonth;
	
	@Column(name="CREDIT_CARD_EXPIRY_YEAR")
	private String creditCardExpiryYear;
	
	@Column(name="CREDIT_CARD_NUMBER")
	private String creditCardNumber;

	@Column(name="CREDIT_CARD_TYPE")
	private String creditCardType;

	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name="NAME_ON_CREDIT_CARD")
	private String nameOnCreditCard;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SALES_ORDER_DATE")
	private Date salesOrderDate;

	@Column(name="SALES_ORDER_STATUS")
	private String salesOrderStatus;

	@Column(name="SHIP_TO_CITY")
	private String shipToCity;

	@Column(name="SHIP_TO_STATE")
	private String shipToState;

	@Column(name="SHIP_TO_STREET_NAME")
	private String shipToStreetName;

	@Column(name="SHIP_TO_STREET_NUMBER")
	private String shipToStreetNumber;

	@Column(name="SHIP_TO_UNIT_NUMBER")
	private String shipToUnitNumber;

	@Column(name="SHIP_TO_ZIP_CODE")
	private int shipToZipCode;
	
	@Column(name="PROMO_CODE")
	private String promoCode;
	
	//Calculated and persisted for data retrieval performance
	@Column(name="SUB_TOTAL")
	private BigDecimal subTotal = BigDecimal.ZERO;
	
	@Column(name="DISCOUNT")
	private BigDecimal discount = BigDecimal.ZERO;
	
	@Column(name="SALES_TAX")
	private BigDecimal salesTax = BigDecimal.ZERO;
	
	@Column(name="SHIPPING")
	private BigDecimal shipping = BigDecimal.ZERO;
	
	@Column(name="TOTAL")
	private BigDecimal total = BigDecimal.ZERO;
	
	@Column(name="USER_ACCOUNT_UID")
	private int userAccountUid;
	
	@Version
	@Column(name="LAST_UPDATED_TIME")
	private Timestamp lastUpdatedTime;

	//many-to-one association to UserAccount
	@ManyToOne(targetEntity=UserAccount.class, fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ACCOUNT_UID", insertable=false, updatable=false)
	@JsonManagedReference
	private UserAccount userAccount;

	@OneToMany(targetEntity=SalesOrderLine.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="salesOrder")
	@JsonManagedReference
	private List<SalesOrderLine> lineItems;

	public SalesOrder() {
	}
	
	public void setSubTotal(BigDecimal subTotal){
		this.subTotal = subTotal;
	}
	public BigDecimal getSubTotal() {
		this.calculateSubTotal();
		return this.subTotal;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getDiscount(){
		return this.discount;
	}
	public void setSalesTax(BigDecimal salesTax) {
		this.salesTax = salesTax;
	}
	public BigDecimal getSalesTax() {
		return this.salesTax;
	}
	
	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}
	public BigDecimal getShipping() {
		return this.shipping;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getTotal() {
		return this.total;
	}
	
//	@OneToMany(orphanRemoval=true)
//	@JoinColumn(name="SALES_ORDER_UID")
//	@XmlElement
	public List<SalesOrderLine> getLineItems() {
		return lineItems;
	}
	
	public void setLineItems (List<SalesOrderLine> lineItems) {
		this.lineItems = lineItems;
	}
	
	public void addOrderLineItem(SalesOrderLine lineItem) {
		if (!lineItems.contains(lineItem)) {
			lineItems.add(lineItem);
		}
	}
	
	public void removeOrderLineItem(SalesOrderLine lineItem) {
		this.lineItems.remove(lineItem);
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getPromoCode() {
		return this.promoCode;
	}
	
	// Calculate subTotal and total (calculation of shipping and sales tax require external services)
	public BigDecimal calculateSubTotal(){
		BigDecimal runningTotal = new BigDecimal(0);
		
		// Loop through the line items and calculate extended cost for each line, adding to runningTotal
		for (SalesOrderLine lineItem : this.getLineItems()) {
			runningTotal = runningTotal.add(lineItem.getExtendedPrice());
		}
		this.subTotal = runningTotal;
		return this.subTotal;
	}
	
	public BigDecimal calculateTotal() throws Exception {
		if ( (this.getSalesTax() == null) || (this.getShipping() == null) ) {
			throw new Exception("Missing Sales Tax and/or Shipping");
		} else {
			this.setTotal( (this.calculateSubTotal().subtract(this.getDiscount()).add(this.getSalesTax()).add(this.getShipping()) ));
		}
		return this.total;
	}

	@XmlID
	public int getSalesOrderUid() {
		return this.salesOrderUid;
	}

	public void setSalesOrderUid(int salesOrderUid) {
		this.salesOrderUid = salesOrderUid;
	}
	
	public String getOrderNumber() {
		return this.orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBillToCity() {
		return this.billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}

	public String getBillToFirstName() {
		return this.billToFirstName;
	}

	public void setBillToFirstName(String billToFirstName) {
		this.billToFirstName = billToFirstName;
	}

	public String getBillToLastName() {
		return this.billToLastName;
	}

	public void setBillToLastName(String billToLastName) {
		this.billToLastName = billToLastName;
	}

	public String getBillToState() {
		return this.billToState;
	}

	public void setBillToState(String billToState) {
		this.billToState = billToState;
	}

	public String getBillToStreetName() {
		return this.billToStreetName;
	}

	public void setBillToStreetName(String billToStreetName) {
		this.billToStreetName = billToStreetName;
	}

	public String getBillToStreetNumber() {
		return this.billToStreetNumber;
	}

	public void setBillToStreetNumber(String billToStreetNumber) {
		this.billToStreetNumber = billToStreetNumber;
	}

	public String getBillToUnitNumber() {
		return this.billToUnitNumber;
	}

	public void setBillToUnitNumber(String billToUnitNumber) {
		this.billToUnitNumber = billToUnitNumber;
	}

	public int getBillToZipCode() {
		return this.billToZipCode;
	}

	public void setBillToZipCode(int billToZipCode) {
		this.billToZipCode = billToZipCode;
	}

	public int getCreditCardCsv() {
		return this.creditCardCsv;
	}

	public void setCreditCardCsv(int creditCardCsv) {
		this.creditCardCsv = creditCardCsv;
	}

	public String getCreditCardExpiryMonth() {
		return this.creditCardExpiryMonth;
	}

	public void setCreditCardExpiryMonth(String creditCardExpiryMonth) {
		this.creditCardExpiryMonth = creditCardExpiryMonth;
	}

	public String getCreditCardExpiryYear() {
		return this.creditCardExpiryYear;
	}

	public void setCreditCardExpiryYear(String creditCardExpiryYear) {
		this.creditCardExpiryYear = creditCardExpiryYear;
	}

	public String getCreditCardNumber() {
		return this.creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardType() {
		return this.creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNameOnCreditCard() {
		return this.nameOnCreditCard;
	}

	public void setNameOnCreditCard(String nameOnCreditCard) {
		this.nameOnCreditCard = nameOnCreditCard;
	}

	public Date getSalesOrderDate() {
		return this.salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	public String getSalesOrderStatus() {
		return this.salesOrderStatus;
	}

	public void setSalesOrderStatus(String salesOrderStatus) {
		this.salesOrderStatus = salesOrderStatus;
	}

	public String getShipToCity() {
		return this.shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToState() {
		return this.shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToStreetName() {
		return this.shipToStreetName;
	}

	public void setShipToStreetName(String shipToStreetName) {
		this.shipToStreetName = shipToStreetName;
	}

	public String getShipToStreetNumber() {
		return this.shipToStreetNumber;
	}

	public void setShipToStreetNumber(String string) {
		this.shipToStreetNumber = string;
	}

	public String getShipToUnitNumber() {
		return this.shipToUnitNumber;
	}

	public void setShipToUnitNumber(String shipToUnitNumber) {
		this.shipToUnitNumber = shipToUnitNumber;
	}

	public int getShipToZipCode() {
		return this.shipToZipCode;
	}

	public void setShipToZipCode(int shipToZipCode) {
		this.shipToZipCode = shipToZipCode;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}