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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the valid_value database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="valid_value")
@NamedQuery(name="ValidState.findAll", query="SELECT v FROM ValidValue v, ValidValuesList l WHERE l.listName = 'States' AND l = v.validValuesList")
public class ValidState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="VALID_VALUE_UID")
	private int validValueUid;

	@Column(name="LIST_VALUE")
	private String listValue;

	@Column(name="VALUE_DESCRIPTION")
	private String valueDescription;

	@ManyToOne
	@JoinColumn(name="VALID_VALUES_LIST_UID")
	private ValidValuesList validValuesList;

	public ValidState() {
	}

	public int getValidValueUid() {
		return this.validValueUid;
	}

	public void setValidValueUid(int validValueUid) {
		this.validValueUid = validValueUid;
	}

	public String getListValue() {
		return this.listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

	public ValidValuesList getValidValuesList() {
		return this.validValuesList;
	}

	public void setValidValuesList(ValidValuesList validValuesList) {
		this.validValuesList = validValuesList;
	}

	public String getValueDescription() {
		return this.valueDescription;
	}

	public void setValueDescription(String valueDescription) {
		this.valueDescription = valueDescription;
	}

}