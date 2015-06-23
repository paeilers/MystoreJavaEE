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


/**
 * The persistent class for the valid_values_list database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="valid_values_list")
@NamedQuery(name="ValidValuesList.findAll", query="SELECT v FROM ValidValuesList v")
public class ValidValuesList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="VALID_VALUES_LIST_UID")
	private int validValuesListUid;

	@Column(name="LIST_NAME")
	private String listName;

	//bi-directional many-to-one association to ValidValue
	@OneToMany(mappedBy="validValuesList")
	private List<ValidValue> validValues;

	public ValidValuesList() {
	}

	public int getValidValuesListUid() {
		return this.validValuesListUid;
	}

	public void setValidValuesListUid(int validValuesListUid) {
		this.validValuesListUid = validValuesListUid;
	}

	public String getListName() {
		return this.listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

}