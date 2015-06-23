package business.salesOrderMgmt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the catalog database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Catalog.findAll", query="SELECT c FROM Catalog c")
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CATALOG_UID")
	private int catalogUid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_FROM")
	private Date validFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_THROUGH")
	private Date validThrough;

	//bi-directional many-to-one association to CatalogItem
	@OneToMany(targetEntity=CatalogItem.class, mappedBy="catalog", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<CatalogItem> catalogItems;

	public Catalog() {
	}

	public int getCatalogUid() {
		return this.catalogUid;
	}

	public void setCatalogUid(int catalogUid) {
		this.catalogUid = catalogUid;
	}

	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidThrough() {
		return this.validThrough;
	}

	public void setValidThrough(Date validThrough) {
		this.validThrough = validThrough;
	}


	public List<CatalogItem> getCatalogItems() {
		return this.catalogItems;
	}

	public void setCatalogItems(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}
	
	public CatalogItem addCatalogItem(CatalogItem catalogItem) {
		getCatalogItems().add(catalogItem);
		catalogItem.setCatalog(this);

		return catalogItem;
	}

	public CatalogItem removeCatalogItem(CatalogItem catalogItem) {
		getCatalogItems().remove(catalogItem);
		catalogItem.setCatalog(null);

		return catalogItem;
	}


}