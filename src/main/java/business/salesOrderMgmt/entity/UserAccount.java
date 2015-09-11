package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the user_account database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="user_account")
@NamedQuery(name="UserAccount.findAll", query="SELECT u FROM UserAccount u")
public class UserAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ACCOUNT_UID")
	private int userAccountUid;

	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="IS_ACTIVE")
	private int isActive;

	@Column(name="LAST_NAME")
	private String lastName;

	private String password;

	@Column(name="USER_NAME")
	private String userName;

/*
	//bi-directional many-to-one association to Address
	@XmlTransient
	@OneToMany(mappedBy="userAccount")
	private List<Address> addresses;

	//bi-directional many-to-one association to CreditCardOnFile
	@XmlTransient
	@OneToMany(mappedBy="userAccount")
	private List<CreditCardOnFile> creditCardOnFiles;
*/
	//bi-directional many-to-one association to SalesOrder
	@XmlTransient
	@OneToMany(targetEntity=SalesOrder.class, mappedBy="userAccount")
	@JsonBackReference
	private List<SalesOrder> salesOrders;

	public UserAccount() {
	}

	public int getUserAccountUid() {
		return this.userAccountUid;
	}

	public void setUserAccountUid(int userAccountUid) {
		this.userAccountUid = userAccountUid;
	}
	
	public int getIsActive1() {
		return this.isActive;
	}
	
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

/*
	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setUserAccount(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setUserAccount(null);

		return address;
	}

	public List<CreditCardOnFile> getCreditCardOnFiles() {
		return this.creditCardOnFiles;
	}

	public void setCreditCardOnFiles(List<CreditCardOnFile> creditCardOnFiles) {
		this.creditCardOnFiles = creditCardOnFiles;
	}
	
	public CreditCardOnFile addCreditCardOnFile(CreditCardOnFile creditCardOnFile) {
		getCreditCardOnFiles().add(creditCardOnFile);
		creditCardOnFile.setUserAccount(this);

		return creditCardOnFile;
	}

	public CreditCardOnFile removeCreditCardOnFile(CreditCardOnFile creditCardOnFile) {
		getCreditCardOnFiles().remove(creditCardOnFile);
		creditCardOnFile.setUserAccount(null);

		return creditCardOnFile;
	}
*/
	public List<SalesOrder> getSalesOrders() {
		return this.salesOrders;
	}

	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}
	
	public SalesOrder addSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().add(salesOrder);
		salesOrder.setUserAccount(this);

		return salesOrder;
	}

	public SalesOrder removeSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().remove(salesOrder);
		salesOrder.setUserAccount(null);

		return salesOrder;
	}

}