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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ADDRESS_UID")
	private int addressUid;

	private String city;

	@Column(name="IS_DEFAULT_BILL_TO")
	private int isDefaultBillTo;

	@Column(name="IS_DEFAULT_SHIP_TO")
	private int isDefaultShipTo;

	private String state;

	@Column(name="STREET_NAME")
	private String streetName;

	@Column(name="STREET_NUMBER")
	private String streetNumber;

	@Column(name="UNIT_NUMBER")
	private String unitNumber;

	@Column(name="ZIP_CODE")
	private int zipCode;

	//bi-directional many-to-one association to UserAccount
	@ManyToOne
	@JoinColumn(name="USER_ACCOUNT_UID")
	private UserAccount userAccount;

	public Address() {
	}

	public int getAddressUid() {
		return this.addressUid;
	}

	public void setAddressUid(int addressUid) {
		this.addressUid = addressUid;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getIsDefaultBillTo() {
		return this.isDefaultBillTo;
	}

	public void setIsDefaultBillTo(int i) {
		this.isDefaultBillTo = i;
	}

	public int getIsDefaultShipTo() {
		return this.isDefaultShipTo;
	}

	public void setIsDefaultShipTo(int i) {
		this.isDefaultShipTo = i;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return this.streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getUnitNumber() {
		return this.unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public int getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}