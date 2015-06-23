package business.salesOrderMgmt.controls;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import business.salesOrderMgmt.entity.UserAccount;

@Stateless
public class UserAccountService implements Serializable {

	@PersistenceContext(name="PrototypeDB")
	EntityManager emgr;
		
	// Setter methods required required for all injected properties to support unit testing as the 
	// dependency injection doesn't occur during execution of unit tests
	public void setEntityManager(EntityManager emgr) {
		this.emgr = emgr;
	}
	
	public EntityManager getEntityManager() {
		return this.emgr;
	}
	
	public UserAccount retrieveUserAccount(Integer userAccountUid) {
		System.out.println("UserAccountService is attempting to retrieve UserAccount with Uid: " + userAccountUid);
		Query query = emgr.createQuery("Select u from UserAccount u where u.userAccountUid = :userAccountUid");
		query.setParameter("userAccountUid", userAccountUid);
		UserAccount userAccount = null;
		userAccount = (UserAccount) query.getSingleResult();
		return userAccount;
	}		
	
}
