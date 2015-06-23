package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the credit_card_on_file database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="credit_card_on_file")
@NamedQuery(name="CreditCardOnFile.findAll", query="SELECT c FROM CreditCardOnFile c")
public class CreditCardOnFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CC_ON_FILE_UID")
	private int ccOnFileUid;

	@Column(name="CARD_NUMBER")
	private String cardNumber;

	@Column(name="CARD_TYPE")
	private String cardType;

	private int csv;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EXPIRATION_DATE")
	private Date expirationDate;

	@Column(name="NAME_ON_CARD")
	private String nameOnCard;

	//bi-directional many-to-one association to UserAccount
	@ManyToOne
	@JoinColumn(name="USER_ACCOUNT_UID")
	private UserAccount userAccount;

	public CreditCardOnFile() {
	}

	public int getCcOnFileUid() {
		return this.ccOnFileUid;
	}

	public void setCcOnFileUid(int ccOnFileUid) {
		this.ccOnFileUid = ccOnFileUid;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getCsv() {
		return this.csv;
	}

	public void setCsv(int csv) {
		this.csv = csv;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getNameOnCard() {
		return this.nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}